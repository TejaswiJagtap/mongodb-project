package com.artcode.thirtyfifty.coupons;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.artcode.thirtyfifty.coupons.level.CouponLevel;
import com.artcode.thirtyfifty.coupons.level.CouponLevelDto;
import com.artcode.thirtyfifty.coupons.level.CouponLevelRepository;
import com.artcode.thirtyfifty.coupons.level.CouponLevelResponse;
import com.artcode.thirtyfifty.coupons.level.category.CouponCategory;
import com.artcode.thirtyfifty.coupons.level.category.CouponCategoryDto;
import com.artcode.thirtyfifty.coupons.level.category.CouponCategoryRepository;
import com.artcode.thirtyfifty.coupons.level.category.CouponCategoryResponse;
import com.artcode.thirtyfifty.coupons.level.category.subcategory.CouponSubCategory;
import com.artcode.thirtyfifty.coupons.level.category.subcategory.CouponSubCategoryDto;
import com.artcode.thirtyfifty.coupons.level.category.subcategory.CouponSubCategoryRepository;
import com.artcode.thirtyfifty.coupons.level.category.subcategory.CouponSubCategoryResponse;
import com.artcode.thirtyfifty.exception.CustomException;
import com.artcode.thirtyfifty.master.category.Category;
import com.artcode.thirtyfifty.master.level.Level;
import com.artcode.thirtyfifty.master.subcategory.SubCategory;
import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CouponsServiceImpl implements CouponsService {

	@Autowired
	private JsonUtils utils;

	@Autowired
	private CouponsRepository repository;

	@Autowired
	private CouponLevelRepository couponLevelRepository;

	@Autowired
	private CouponCategoryRepository couponCategoryRepository;

	@Autowired
	private CouponSubCategoryRepository subCategoryRepository;

	@Autowired
	private CouponMapper mapper;

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final String COUPON_NOT_PRESENT = "coupons not present";

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String save(CouponsDto dto) throws CustomException {
		Coupons coupons = mapper.dtoToCoupon(dto);
		repository.save(coupons);

		List<CouponLevelDto> couponLevelDtos = dto.getCouponLevelDtos();
		for (CouponLevelDto couponLevelDto : couponLevelDtos) {
			CouponLevel couponLevel = new CouponLevel();

			couponLevel.setLevel(new Level(couponLevelDto.getLevelId()));
			couponLevel.setCoupons(coupons);
			couponLevelRepository.save(couponLevel);

			List<CouponCategoryDto> couponCategoryDtos = couponLevelDto.getCouponCategoryDtos();
			for (CouponCategoryDto categoryDto : couponCategoryDtos) {
				CouponCategory couponCategory = new CouponCategory();

				couponCategory.setCouponLevel(couponLevel);
				couponCategory.setCategory(new Category(categoryDto.getCategoryId()));

				couponCategoryRepository.save(couponCategory);

				List<CouponSubCategoryDto> couponSubCategoryDtos = categoryDto.getCouponSubCategoryDtos();
				for (CouponSubCategoryDto subCategoryDto : couponSubCategoryDtos) {
					CouponSubCategory couponSubCategory = new CouponSubCategory();

					couponSubCategory.setCouponCategory(couponCategory);
					couponSubCategory.setSubCategory(new SubCategory(subCategoryDto.getSubCategoryId()));

					subCategoryRepository.save(couponSubCategory);
				}
			}
		}
		return utils.objectMapperSuccess("Saved Successfully");
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String update(CouponsDto dto, String id) throws CustomException {
		Coupons coupons = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new CustomException(utils.objectMapperError(COUPON_NOT_PRESENT)));

		coupons = mapper.updateCoupon(dto, coupons);
		repository.save(coupons);

		deleteExistingEntities(coupons.getId());

		List<CouponLevelDto> couponLevelDtos = dto.getCouponLevelDtos();
		for (CouponLevelDto couponLevelDto : couponLevelDtos) {
			CouponLevel couponLevel = new CouponLevel();

			couponLevel.setLevel(new Level(couponLevelDto.getLevelId()));
			couponLevel.setCoupons(coupons);
			couponLevelRepository.save(couponLevel);

			List<CouponCategoryDto> couponCategoryDtos = couponLevelDto.getCouponCategoryDtos();
			for (CouponCategoryDto categoryDto : couponCategoryDtos) {
				CouponCategory couponCategory = new CouponCategory();

				couponCategory.setCouponLevel(couponLevel);
				couponCategory.setCategory(new Category(categoryDto.getCategoryId()));
				couponCategoryRepository.save(couponCategory);

				List<CouponSubCategoryDto> couponSubCategoryDtos = categoryDto.getCouponSubCategoryDtos();
				for (CouponSubCategoryDto subCategoryDto : couponSubCategoryDtos) {
					CouponSubCategory couponSubCategory = new CouponSubCategory();

					couponSubCategory.setCouponCategory(couponCategory);
					couponSubCategory.setSubCategory(new SubCategory(subCategoryDto.getSubCategoryId()));
					subCategoryRepository.save(couponSubCategory);
				}
			}
		}
		return utils.objectMapperSuccess("Updated Successfully");
	}

	@Transactional
	private void deleteExistingEntities(String couponId) {
		// Ensure proper deletion order
		List<CouponLevel> couponLevels = couponLevelRepository.findByCouponsId(couponId);
		for (CouponLevel couponLevel : couponLevels) {
			List<CouponCategory> couponCategories = couponCategoryRepository.findByCouponLevelId(couponLevel.getId());
			for (CouponCategory couponCategory : couponCategories) {
				subCategoryRepository.deleteByCouponCategoryId(couponCategory.getId());
			}
			couponCategoryRepository.deleteByCouponLevelId(couponLevel.getId());
			System.out.println("coupon category" + couponCategories);
		}
		couponLevelRepository.deleteByCouponsId(couponId);
		log.info("coupon levels" + couponLevels);

	}

	@Override
	public String getByAll(int pageNo, int pageSize, String sortBy, String sortOrder, String searchTerm,
			int isPagination) throws CustomException {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Direction.fromString(sortOrder), sortBy);

		// Build the query
		Query query = new Query();

		// Exclude deleted entries
		query.addCriteria(Criteria.where("isDeleted").is(false));

		if (searchTerm != null && !searchTerm.isEmpty()) {
			String likeSearchTerm = ".*" + searchTerm + ".*";

			query.addCriteria(new Criteria().orOperator(Criteria.where("name").regex(likeSearchTerm, "i"),
					Criteria.where("description").regex(likeSearchTerm, "i"),
					Criteria.where("duration").regex(likeSearchTerm, "i"),
					Criteria.where("totalQuestions").regex(likeSearchTerm, "i"),
					Criteria.where("courses.name").regex(likeSearchTerm, "i"),
					Criteria.where("mockExamQuestions").regex(likeSearchTerm, "i")));
		}
		// Pagination
		query.with(pageable);

		List<Coupons> couponsList = mongoTemplate.find(query, Coupons.class);
		long totalElements = mongoTemplate.count(query.skip(-1).limit(-1), Coupons.class);

		List<CouponResponse> responses = new ArrayList<>();
		for (Coupons coupons : couponsList) {
			CouponResponse response = mapper.couponToResponse(coupons);

			List<CouponLevelResponse> couponLevelResponses = couponLevelRepository.findByCouponsId(coupons.getId())
					.stream().map(couponLevel -> {
						CouponLevelResponse levelResponse = new CouponLevelResponse();

						levelResponse.setCouponId(couponLevel.getCoupons().getId());
						levelResponse.setCouponName(couponLevel.getCoupons().getSchemeName());
						levelResponse.setLevelId(couponLevel.getLevel().getId());
						levelResponse.setLevelName(couponLevel.getLevel().getName());
						levelResponse.setId(couponLevel.getId());

						List<CouponCategoryResponse> categoryResponses = couponCategoryRepository
								.findByCouponLevelId(couponLevel.getId()).stream().map(couponCategory -> {
									CouponCategoryResponse categoryResponse = new CouponCategoryResponse();

									categoryResponse.setCouponLevelId(couponCategory.getCouponLevel().getId());
									categoryResponse
											.setCouponLevelName(couponCategory.getCouponLevel().getLevel().getName());
									categoryResponse.setCategoryId(couponCategory.getCategory().getId());
									categoryResponse.setCategoryName(couponCategory.getCategory().getName());
									categoryResponse.setId(couponCategory.getId());

									List<CouponSubCategoryResponse> subCategoryResponses = subCategoryRepository
											.findByCouponCategoryId(couponCategory.getId()).stream()
											.map(couponSubCategory -> {
												CouponSubCategoryResponse subCategoryResponse = new CouponSubCategoryResponse();

												subCategoryResponse.setCouponCategoryId(
														couponSubCategory.getCouponCategory().getId());
												subCategoryResponse.setCouponCategory(
														couponSubCategory.getCouponCategory().getCategory().getName());
												subCategoryResponse
														.setSubCategoryId(couponSubCategory.getSubCategory().getId());
												subCategoryResponse
														.setSubCategory(couponSubCategory.getSubCategory().getName());
												subCategoryResponse.setId(couponSubCategory.getId());

												return subCategoryResponse;
											}).toList();

									categoryResponse.setCouponSubCategoryResponses(subCategoryResponses);
									return categoryResponse;
								}).toList();

						levelResponse.setCouponCategoryResponses(categoryResponses);
						return levelResponse;
					}).toList();

			response.setCouponLevelResponses(couponLevelResponses);
			responses.add(response);
		}
		PageImpl<CouponResponse> pageImpl = new PageImpl<>(responses, pageable, totalElements);
		return utils.objectMapperSuccess(pageImpl, "Coupons list");
	}

	@Override
	public String delete(String id) throws CustomException {
		Coupons coupons = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new CustomException(utils.objectMapperError(COUPON_NOT_PRESENT)));
		coupons.setDeleted(true);

		repository.save(coupons);
		return utils.objectMapperSuccess("Deleted successfully");
	}

	@Override
	public String getById(String id) throws CustomException {
		Coupons coupons = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new CustomException(utils.objectMapperError(COUPON_NOT_PRESENT)));

		CouponResponse response = mapper.couponToResponse(coupons);

		List<CouponLevelResponse> couponLevelResponses = couponLevelRepository.findByCouponsId(coupons.getId()).stream()
				.map(couponLevel -> {
					CouponLevelResponse levelResponse = new CouponLevelResponse();

					levelResponse.setCouponId(couponLevel.getCoupons().getId());
					levelResponse.setCouponName(couponLevel.getCoupons().getSchemeName());
					levelResponse.setLevelId(couponLevel.getLevel().getId());
					levelResponse.setLevelName(couponLevel.getLevel().getName());
					levelResponse.setId(couponLevel.getId());

					List<CouponCategoryResponse> categoryResponses = couponCategoryRepository
							.findByCouponLevelId(couponLevel.getId()).stream().map(couponCategory -> {
								CouponCategoryResponse categoryResponse = new CouponCategoryResponse();

								categoryResponse.setCouponLevelId(couponCategory.getCouponLevel().getId());
								categoryResponse
										.setCouponLevelName(couponCategory.getCouponLevel().getLevel().getName());
								categoryResponse.setCategoryId(couponCategory.getCategory().getId());
								categoryResponse.setCategoryName(couponCategory.getCategory().getName());
								categoryResponse.setId(couponCategory.getId());

								List<CouponSubCategoryResponse> subCategoryResponses = subCategoryRepository
										.findByCouponCategoryId(couponCategory.getId()).stream()
										.map(couponSubCategory -> {
											CouponSubCategoryResponse subCategoryResponse = new CouponSubCategoryResponse();

											subCategoryResponse
													.setCouponCategoryId(couponSubCategory.getCouponCategory().getId());
											subCategoryResponse.setCouponCategory(
													couponSubCategory.getCouponCategory().getCategory().getName());
											subCategoryResponse
													.setSubCategoryId(couponSubCategory.getSubCategory().getId());
											subCategoryResponse
													.setSubCategory(couponSubCategory.getSubCategory().getName());
											subCategoryResponse.setId(couponSubCategory.getId());

											return subCategoryResponse;
										}).toList();

								categoryResponse.setCouponSubCategoryResponses(subCategoryResponses);
								return categoryResponse;
							}).toList();

					levelResponse.setCouponCategoryResponses(categoryResponses);
					return levelResponse;

				}).toList();

		response.setCouponLevelResponses(couponLevelResponses);

		return utils.objectMapperSuccess(response, "coupons details");
	}

	@Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
	@Override
	public void updateExpiredCoupons() {
		LocalDate today = LocalDate.now();
		List<Coupons> couponsToExpire = repository.findByExpiredFalseAndEndDateBefore(today);
		for (Coupons coupon : couponsToExpire) {
			coupon.setExpired(true);
			coupon.setStatus(CouponStatus.DEACTIVE);
			repository.save(coupon);
		}
	}

	@Override
	public String updateStatus(CouponsDto dto, String id) throws CustomException {
		Coupons coupons = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new CustomException(utils.objectMapperError(COUPON_NOT_PRESENT)));

		coupons.setStartDate(dto.getStartDate());
		coupons.setEndDate(dto.getEndDate());
		coupons.setStatus(dto.getStatus());

		repository.save(coupons);
		return utils.objectMapperSuccess("status updated successfully");
	}
}
