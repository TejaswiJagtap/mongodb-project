package com.artcode.thirtyfifty.flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.artcode.thirtyfifty.master.courses.Courses;
import com.artcode.thirtyfifty.master.courses.CoursesRepository;
import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.transaction.Transactional;

@Service
public class FlashCardServiceImpl implements FlashCardService {

	@Autowired
	private FlashCardRepository repository;

	@Autowired
	private JsonUtils utils;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private CoursesRepository coursesRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String saveUpdate(FlashCardDto request) throws Exception {
		FlashCard flashCard = repository.findById(request.getId()).orElseGet(FlashCard::new);
		Courses courses = coursesRepository.findByIdAndIsDeleted(request.getCoursesId(), false)
				.orElseThrow(() -> new Exception("course not present"));

		flashCard.setQuestion(request.getQuestion());
		flashCard.setAnswer(request.getAnswer());
		flashCard.setCourses(courses);
		flashCard.setDeleted(false);

		repository.save(flashCard);
		return utils.objectMapperSuccess("Save/Update Successfully");
	}

	@Override
	public String getByAll(int pageNo, int pageSize, String sortBy, String sortOrder, String searchTerm,
			int IsPagination, String courseId) throws Exception {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Direction.fromString(sortOrder), sortBy);
		// Build the query
		Query query = new Query();

		// Exclude deleted entries
		query.addCriteria(Criteria.where("isDeleted").is(false));

		if (searchTerm != null && !searchTerm.isEmpty()) {
			String likeSearchTerm = ".*" + searchTerm + ".*";

			query.addCriteria(new Criteria().orOperator(Criteria.where("question").regex(likeSearchTerm, "i"),
					Criteria.where("answer").regex(likeSearchTerm, "i"),
					Criteria.where("courses.name").regex(likeSearchTerm, "i")));
		}

		if (courseId != null && !courseId.isEmpty()) {
			query.addCriteria(Criteria.where("courses.id").is(courseId));
		}
		// Pagination
		query.with(pageable);

		List<FlashCard> flashCards = mongoTemplate.find(query, FlashCard.class);
		long totalElements = mongoTemplate.count(query.skip(-1).limit(-1), FlashCard.class);

		List<FlashCardDto> responses = new ArrayList<>();

		responses = flashCards
				.stream().map(flashCard -> new FlashCardDto(flashCard.getId(), flashCard.getQuestion(),
						flashCard.getAnswer(), flashCard.getCourses().getId(), flashCard.getCourses().getName()))
				.collect(Collectors.toList());

		PageImpl<FlashCardDto> pageImpl = new PageImpl<>(responses, pageable, totalElements);
		return utils.objectMapperSuccess(pageImpl, "flashcard list");
	}

	@Override
	public String delete(String id) throws Exception {
		FlashCard flashCard = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new Exception(utils.objectMapperError("record not found")));
		flashCard.setDeleted(true);

		repository.save(flashCard);
		return utils.objectMapperSuccess("deleted Successfully");
	}

	@Override
	public String getById(String id) throws Exception {
		FlashCard flashCard = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new Exception(utils.objectMapperError("record not found")));

		FlashCardDto dto = new FlashCardDto(flashCard.getId(), flashCard.getQuestion(), flashCard.getAnswer(),
				flashCard.getCourses().getId(), flashCard.getCourses().getName());

		return utils.objectMapperSuccess(dto, "flash card details");
	}

}
