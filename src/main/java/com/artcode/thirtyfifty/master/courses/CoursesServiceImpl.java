package com.artcode.thirtyfifty.master.courses;

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

import com.artcode.thirtyfifty.master.subcategory.SubCategory;
import com.artcode.thirtyfifty.master.subcategory.SubCategoryRepository;
import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.transaction.Transactional;

@Service
public class CoursesServiceImpl implements CoursesService {

	@Autowired
	private JsonUtils utils;

	@Autowired
	private CoursesRepository repository;

	@Autowired
	private SubCategoryRepository subCategoryRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String saveUpdate(CoursesDto dto) throws Exception {
		Courses courses = repository.findById(dto.getId()).orElseGet(Courses::new);
		SubCategory subCategory = subCategoryRepository.findById(dto.getSubCategoryId())
				.orElseThrow(() -> new Exception("sub-category not present"));
		courses.setName(dto.getName());
		courses.setDescription(dto.getDescription());
		courses.setCost(dto.getCost());
		courses.setSubCategory(subCategory);
		courses.setDeleted(false);

		repository.save(courses);
		return utils.objectMapperSuccess("Saved successfully");
	}

	@Override
	public String getByAll(int pageNo, int pageSize, String sortBy, String sortOrder, String searchTerm,
			int IsPagination, String subCategoryId) throws Exception {
		Pageable pageable = PageRequest.of(pageNo, pageSize,
				Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortOrder))));

		// Build the query
		Query query = new Query();

		// Exclude deleted entries
		query.addCriteria(Criteria.where("isDeleted").is(false));

		if (searchTerm != null && !searchTerm.isEmpty()) {
			String likeSearchTerm = ".*" + searchTerm + ".*";

			query.addCriteria(new Criteria().orOperator(Criteria.where("name").regex(likeSearchTerm, "i"),
					Criteria.where("description").regex(likeSearchTerm, "i"),
					Criteria.where("cost").regex(likeSearchTerm, "i"),
					Criteria.where("subCategory.name").regex(likeSearchTerm, "i")));
		}

		if (subCategoryId != null && !subCategoryId.isEmpty()) {
			query.addCriteria(Criteria.where("subCategory.id").is(subCategoryId));
		}
		// Pagination
		query.with(pageable);

		List<Courses> coursesList = mongoTemplate.find(query, Courses.class);
		long totalElements = mongoTemplate.count(query.skip(-1).limit(-1), Courses.class);

		List<CoursesDto> responses = new ArrayList<>();

		responses = coursesList.stream()
				.map(courses -> new CoursesDto(courses.getId(), courses.getName(), courses.getDescription(),
						courses.getCost(), courses.getSubCategory().getId(), courses.getSubCategory().getName()))
				.collect(Collectors.toList());

		PageImpl<CoursesDto> pageImpl = new PageImpl<>(responses, pageable, totalElements);
		return utils.objectMapperSuccess(pageImpl, "category list");
	}

	@Override
	public String delete(String id) throws Exception {
		Courses courses = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new Exception(utils.objectMapperError("courses not present")));
		courses.setDeleted(true);
		repository.save(courses);

		return utils.objectMapperSuccess("Deleted successfully");
	}

	@Override
	public String getById(String id) throws Exception {
		Courses courses = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new Exception(utils.objectMapperError("courses not present")));
		CoursesDto response = new CoursesDto(courses.getId(), courses.getName(), courses.getDescription(),
				courses.getCost(), courses.getSubCategory().getId(), courses.getSubCategory().getName());

		return utils.objectMapperSuccess(response, "courses details");
	}
}
