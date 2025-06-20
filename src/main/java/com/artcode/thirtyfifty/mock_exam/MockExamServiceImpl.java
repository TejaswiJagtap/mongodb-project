package com.artcode.thirtyfifty.mock_exam;

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
import org.springframework.stereotype.Service;

import com.artcode.thirtyfifty.master.courses.Courses;
import com.artcode.thirtyfifty.master.courses.CoursesRepository;
import com.artcode.thirtyfifty.mock_exam.question.MockExamQuestion;
import com.artcode.thirtyfifty.mock_exam.question.MockExamQuestionDto;
import com.artcode.thirtyfifty.mock_exam.question.options.QuestionOption;
import com.artcode.thirtyfifty.mock_exam.question.options.QuestionOptionDto;
import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.transaction.Transactional;

@Service
public class MockExamServiceImpl implements MockExamService {

	@Autowired
	private MockExamRepository repository;

	@Autowired
	private JsonUtils utils;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private CoursesRepository coursesRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String save(MockExamDto request) throws Exception {
		MockExam mockExam = new MockExam();
		Courses courses = coursesRepository.findByIdAndIsDeleted(request.getCoursesId(), false)
				.orElseThrow(() -> new Exception("course not present"));

		mockExam.setName(request.getName());
		mockExam.setDescription(request.getDescription());
		mockExam.setDuration(request.getDuration());
		mockExam.setTotalQuestions(request.getTotalQuestions());
		mockExam.setCourses(courses);
		mockExam.setDeleted(false);

		List<MockExamQuestion> mockExamQuestions = new ArrayList<>();
		List<MockExamQuestionDto> mockExamQuestionList = request.getMockExamQuestionDtos();
		for (MockExamQuestionDto mockExamQuestionDto : mockExamQuestionList) {
			MockExamQuestion mockExamQuestion = new MockExamQuestion();

			mockExamQuestion.setQuestion(mockExamQuestionDto.getQuestion());
			mockExamQuestion.setCorrectAnswer(mockExamQuestionDto.getCorrectAnswer());

			List<QuestionOption> questionOptions = new ArrayList<>();
			List<QuestionOptionDto> questionOptionDtos = mockExamQuestionDto.getQuestionOptionDtos();
			for (QuestionOptionDto optionDto : questionOptionDtos) {
				QuestionOption questionOption = new QuestionOption();
				questionOption.setOptionText(optionDto.getOptionText());
				questionOption.setCorrect(optionDto.isCorrect());
				questionOptions.add(questionOption);
			}
			mockExamQuestion.setQuestionOptions(questionOptions);
			mockExamQuestions.add(mockExamQuestion);
		}
		mockExam.setMockExamQuestions(mockExamQuestions);

		repository.save(mockExam);
		return utils.objectMapperSuccess("Saved Successfully");
	}

	@Override
	public String update(MockExamDto request, String id) throws Exception {
		MockExam mockExam = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new Exception(utils.objectMapperError("record not found")));
		Courses courses = coursesRepository.findByIdAndIsDeleted(request.getCoursesId(), false)
				.orElseThrow(() -> new Exception("course not present"));

		mockExam.setName(request.getName());
		mockExam.setDescription(request.getDescription());
		mockExam.setDuration(request.getDuration());
		mockExam.setTotalQuestions(request.getTotalQuestions());
		mockExam.setCourses(courses);

		List<MockExamQuestion> mockExamQuestions = new ArrayList<>();
		List<MockExamQuestionDto> mockExamQuestionList = request.getMockExamQuestionDtos();
		for (MockExamQuestionDto mockExamQuestionDto : mockExamQuestionList) {
			MockExamQuestion mockExamQuestion = new MockExamQuestion();

			mockExamQuestion.setQuestion(mockExamQuestionDto.getQuestion());
			mockExamQuestion.setCorrectAnswer(mockExamQuestionDto.getCorrectAnswer());

			List<QuestionOption> questionOptions = new ArrayList<>();
			List<QuestionOptionDto> questionOptionDtos = mockExamQuestionDto.getQuestionOptionDtos();
			for (QuestionOptionDto optionDto : questionOptionDtos) {
				QuestionOption questionOption = new QuestionOption();
				questionOption.setOptionText(optionDto.getOptionText());
				questionOption.setCorrect(optionDto.isCorrect());

				questionOptions.add(questionOption);
			}
			mockExamQuestion.setQuestionOptions(questionOptions);
			mockExamQuestions.add(mockExamQuestion);
		}
		mockExam.setMockExamQuestions(mockExamQuestions);

		repository.save(mockExam);
		return utils.objectMapperSuccess("Updated Successfully");
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

			query.addCriteria(new Criteria().orOperator(Criteria.where("name").regex(likeSearchTerm, "i"),
					Criteria.where("description").regex(likeSearchTerm, "i"),
					Criteria.where("duration").regex(likeSearchTerm, "i"),
					Criteria.where("totalQuestions").regex(likeSearchTerm, "i"),
					Criteria.where("courses.name").regex(likeSearchTerm, "i"),
					Criteria.where("mockExamQuestions").regex(likeSearchTerm, "i")));
		}

		if (courseId != null && !courseId.isEmpty()) {
			query.addCriteria(Criteria.where("courses.id").is(courseId));
		}
		// Pagination
		query.with(pageable);

		List<MockExam> mockExams = mongoTemplate.find(query, MockExam.class);
		long totalElements = mongoTemplate.count(query.skip(-1).limit(-1), MockExam.class);

		List<MockExamResponse> responses = new ArrayList<>();
		for (MockExam mockExam : mockExams) {
			MockExamResponse response = new MockExamResponse();
			response.setName(mockExam.getName());
			response.setDescription(mockExam.getDescription());
			response.setDuration(mockExam.getDuration());
			response.setTotalQuestions(mockExam.getTotalQuestions());
			response.setCoursesId(mockExam.getCourses().getId());
			response.setCoursesName(mockExam.getCourses().getName());
			response.setId(mockExam.getId());

			List<MockExamQuestionDto> mockExamQuestionDtos = mockExam.getMockExamQuestions().stream()
					.map(mockExamQuestion -> {
						MockExamQuestionDto dto = new MockExamQuestionDto();
						dto.setCorrectAnswer(mockExamQuestion.getCorrectAnswer());
						dto.setQuestion(mockExamQuestion.getQuestion());

						List<QuestionOptionDto> questionOptionDtos = mockExamQuestion.getQuestionOptions().stream()
								.map(questionOption -> {
									QuestionOptionDto questionOptionDto = new QuestionOptionDto();
									questionOptionDto.setCorrect(questionOption.isCorrect());
									questionOptionDto.setOptionText(questionOption.getOptionText());
									return questionOptionDto;
								}).toList();
						dto.setQuestionOptionDtos(questionOptionDtos);
						return dto;
					}).toList();

			response.setMockExamQuestionDtos(mockExamQuestionDtos);
			responses.add(response);
		}
		PageImpl<MockExamResponse> pageImpl = new PageImpl<>(responses, pageable, totalElements);
		return utils.objectMapperSuccess(pageImpl, "mock exam list");
	}

	@Override
	public String delete(String id) throws Exception {
		MockExam mockExam = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new Exception(utils.objectMapperError("mockExam not found")));
		mockExam.setDeleted(true);

		repository.save(mockExam);
		return utils.objectMapperSuccess("Deleted Sucessfully");
	}

	@Override
	public String getById(String id) throws Exception {
		MockExam mockExam = repository.findByIdAndIsDeleted(id, false)
				.orElseThrow(() -> new Exception(utils.objectMapperError("record not found")));

		MockExamResponse response = new MockExamResponse();
		response.setName(mockExam.getName());
		response.setDescription(mockExam.getDescription());
		response.setDuration(mockExam.getDuration());
		response.setTotalQuestions(mockExam.getTotalQuestions());
		response.setCoursesId(mockExam.getCourses().getId());
		response.setCoursesName(mockExam.getCourses().getName());
		response.setId(mockExam.getId());

		List<MockExamQuestionDto> mockExamQuestionDtos = mockExam.getMockExamQuestions().stream()
				.map(mockExamQuestion -> {
					MockExamQuestionDto dto = new MockExamQuestionDto();
					dto.setCorrectAnswer(mockExamQuestion.getCorrectAnswer());
					dto.setQuestion(mockExamQuestion.getQuestion());

					List<QuestionOptionDto> questionOptionDtos = mockExamQuestion.getQuestionOptions().stream()
							.map(questionOption -> {
								QuestionOptionDto questionOptionDto = new QuestionOptionDto();
								questionOptionDto.setCorrect(questionOption.isCorrect());
								questionOptionDto.setOptionText(questionOption.getOptionText());
								return questionOptionDto;
							}).toList();
					dto.setQuestionOptionDtos(questionOptionDtos);
					return dto;
				}).toList();

		response.setMockExamQuestionDtos(mockExamQuestionDtos);
		return utils.objectMapperSuccess(response, "mock exam details");
	}
}
