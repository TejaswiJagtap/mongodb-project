package com.artcode.thirtyfifty.master.level;

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
import org.springframework.web.multipart.MultipartFile;

import com.artcode.thirtyfifty.utils.JsonUtils;
import com.artcode.thirtyfifty.utils.UploadFile;

import jakarta.transaction.Transactional;

@Service
public class LevelServiceImpl implements LevelService {

	@Autowired
	private JsonUtils utils;

	@Autowired
	private LevelRepository repository;

//	@Autowired
//	private CategoryRepository categoryRepository;

	@Autowired
	private UploadFile uploadFile;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String saveUpdate(LevelDto dto, MultipartFile image) throws Exception {
		Level level = repository.findById(dto.getId()).orElseGet(Level::new);

		level.setName(dto.getName());
		level.setShortName(dto.getShortName());
		level.setDeleted(false);

		// Upload image if available
		if (image != null) {
			String imageUrl = uploadFile.uploadFile(image, "image");
			level.setImage(imageUrl);
		}

		repository.save(level);
		return utils.objectMapperSuccess("Saved successfully");
	}

	@Override
	public String getByAll(int pageNo, int pageSize, String sortBy, String sortOrder, String searchTerm,
			int IsPagination) throws Exception {
		Pageable pageable = PageRequest.of(pageNo, pageSize,
				Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortOrder))));

		// Build the query
		Query query = new Query();
		// Exclude deleted entries
		query.addCriteria(Criteria.where("isDeleted").is(false));

		if (searchTerm != null && !searchTerm.isEmpty()) {
			String regex = ".*" + searchTerm + ".*";
			query.addCriteria(new Criteria().orOperator(Criteria.where("name").regex(regex, "i"),
					Criteria.where("shortName").regex(regex, "i")));
		}
		// Pagination
		query.with(pageable);

		List<Level> levels = mongoTemplate.find(query, Level.class);
		long totalElements = mongoTemplate.count(query.skip(-1).limit(-1), Level.class);

		List<LevelDto> responses = new ArrayList<>();

		responses = levels.stream()
				.map(level -> new LevelDto(level.getId(), level.getName(), level.getImage(), level.getShortName()))
				.collect(Collectors.toList());

		PageImpl<LevelDto> pageImpl2 = new PageImpl<>(responses, pageable, totalElements);
		return utils.objectMapperSuccess(pageImpl2, "level list");
	}

	@Override
	public String delete(String id) throws Exception {
		Level level = Optional.ofNullable(repository.findByIdAndIsDeleted(id, false))
				.orElseThrow(() -> new Exception(utils.objectMapperError("level not present")));
//		Category category = categoryRepository.findByLevelId(level.getId());
//		if (category != null) {
//			return utils.objectMapperError("can't delete category is present");
//		} else {
		level.setDeleted(true);
		repository.save(level);
		return utils.objectMapperSuccess("Deleted successfully");
//		}
	}

	@Override
	public String getById(String id) throws Exception {
		Level level = Optional.ofNullable(repository.findByIdAndIsDeleted(id, false))
				.orElseThrow(() -> new Exception(utils.objectMapperError("level not present")));
		LevelDto response = new LevelDto(level.getId(), level.getName(), level.getImage(), level.getShortName());

		return utils.objectMapperSuccess(response, "level details");
	}
}
