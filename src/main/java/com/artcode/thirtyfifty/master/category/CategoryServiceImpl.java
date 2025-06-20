package com.artcode.thirtyfifty.master.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import com.artcode.thirtyfifty.master.level.Level;
import com.artcode.thirtyfifty.master.level.LevelRepository;
import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private JsonUtils utils;

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private LevelRepository levelRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String saveUpdate(CategoryDto dto) throws Exception {
		Category category = repository.findById(dto.getId()).orElseGet(Category::new);
		Level level = levelRepository.findById(dto.getLevelId()).orElseThrow(() -> new Exception("not found"));
		category.setName(dto.getName());
		category.setLevel(level);
		category.setDeleted(false);

		repository.save(category);
		return utils.objectMapperSuccess("Saved successfully");
	}

	@Override
	public String getByAll(int pageNo, int pageSize, String sortBy, String sortOrder, String searchTerm,
			int IsPagination, String levelId) throws Exception {
		Pageable pageable = PageRequest.of(pageNo, pageSize,
				Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortOrder))));

		// Build the query
		Query query = new Query();
		// Exclude deleted entries
		query.addCriteria(Criteria.where("isDeleted").is(false));

		query.fields().include("id").include("name").include("level.id").include("level.name");

		if (searchTerm != null && !searchTerm.isEmpty()) {
			String regex = ".*" + searchTerm + ".*";
			query.addCriteria(new Criteria().orOperator(Criteria.where("name").regex(regex, "i"),
					Criteria.where("level.name").regex(regex, "i")));
		}

		if (levelId != null && !levelId.isEmpty()) {
			query.addCriteria(Criteria.where("level.id").is(levelId));
		}

		// Pagination
		query.with(pageable);

		List<Category> categories = mongoTemplate.find(query, Category.class);
		long totalElements = mongoTemplate.count(query.skip(-1).limit(-1), Category.class);

		List<CategoryDto> responses = new ArrayList<>();

		responses = categories.stream().map(category -> new CategoryDto(category.getId(), category.getName(),
				category.getLevel().getId(), category.getLevel().getName())).collect(Collectors.toList());

		PageImpl<CategoryDto> pageImpl = new PageImpl<>(responses, pageable, totalElements);
		return utils.objectMapperSuccess(pageImpl, "category list");
	}

	@Override
	public String delete(String id) throws Exception {
		Category category = repository.findById(id)
				.orElseThrow(() -> new Exception(utils.objectMapperError("category not present")));
		category.setDeleted(true);
		repository.save(category);
		return utils.objectMapperSuccess("Deleted successfully");
	}

	@Override
	public String getById(String id) throws Exception {
		Category category = Optional.ofNullable(repository.findByIdAndIsDeleted(id, false))
				.orElseThrow(() -> new Exception(utils.objectMapperError("category not present")));
		CategoryDto response = new CategoryDto(category.getId(), category.getName(), category.getLevel().getId(),
				category.getLevel().getName());

		return utils.objectMapperSuccess(response, "category details");
	}
}
