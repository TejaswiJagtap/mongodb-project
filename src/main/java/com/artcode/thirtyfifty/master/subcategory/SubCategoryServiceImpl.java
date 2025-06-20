package com.artcode.thirtyfifty.master.subcategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.artcode.thirtyfifty.master.category.Category;
import com.artcode.thirtyfifty.master.category.CategoryRepository;
import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.transaction.Transactional;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

	@Autowired
	private JsonUtils utils;

	@Autowired
	private SubCategoryRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String saveUpdate(SubCategoryDto dto) throws Exception {
		SubCategory subCategory = repository.findById(dto.getId()).orElseGet(SubCategory::new);
		Category category = categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(() -> new Exception("not found"));
		subCategory.setName(dto.getName());
		subCategory.setCategory(category);
		subCategory.setDeleted(false);

		repository.save(subCategory);
		return utils.objectMapperSuccess("Saved successfully");
	}

	@Override
	public String getByAll(int pageNo, int pageSize, String sortBy, String sortOrder, String searchTerm,
			int IsPagination, String categoryId) throws Exception {
		Pageable pageable = PageRequest.of(pageNo, pageSize,
				Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortOrder))));

		// Build the query
		Query query = new Query();

		// Exclude deleted entries
		query.addCriteria(Criteria.where("isDeleted").is(false));

		if (searchTerm != null && !searchTerm.isEmpty()) {
			String likeSearchTerm = ".*" + searchTerm + ".*";

			query.addCriteria(new Criteria().orOperator(Criteria.where("name").regex(likeSearchTerm, "i"),
					Criteria.where("category.name").regex(likeSearchTerm, "i")));
		}

		if (categoryId != null && !categoryId.isEmpty()) {
			query.addCriteria(Criteria.where("category.id").is(categoryId));
		}
		// Pagination
		query.with(pageable);

		List<SubCategory> subCategories = mongoTemplate.find(query, SubCategory.class);
		long totalElements = mongoTemplate.count(query.skip(-1).limit(-1), SubCategory.class);

		List<SubCategoryDto> responses = new ArrayList<>();

		responses = subCategories.stream()
				.map(subCategory -> new SubCategoryDto(subCategory.getId(), subCategory.getName(),
						subCategory.getCategory().getId(), subCategory.getCategory().getName()))
				.collect(Collectors.toList());

		PageImpl<SubCategoryDto> pageImpl = new PageImpl<>(responses, pageable, totalElements);
		return utils.objectMapperSuccess(pageImpl, "category list");
	}

	@Override
	public String delete(String id) throws Exception {
		SubCategory subCategory = repository.findById(id)
				.orElseThrow(() -> new Exception(utils.objectMapperError("subCategory not present")));
		subCategory.setDeleted(true);
		repository.save(subCategory);
		return utils.objectMapperSuccess("Deleted successfully");
	}

	@Override
	public String getById(String id) throws Exception {
		SubCategory subCategory = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new Exception(utils.objectMapperError("subCategory not present")));

		SubCategoryDto response = new SubCategoryDto(subCategory.getId(), subCategory.getName(),
				subCategory.getCategory().getId(), subCategory.getCategory().getName());
		return utils.objectMapperSuccess(response, "subCategory details");
	}
}
