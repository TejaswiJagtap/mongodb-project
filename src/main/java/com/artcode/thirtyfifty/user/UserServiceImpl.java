package com.artcode.thirtyfifty.user;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.artcode.thirtyfifty.exception.CustomException;
import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private JsonUtils utils;

	@Autowired
	private UserMapper mapper;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static final String NOT_FOUND ="Record not found";

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String update(String id, UserRequest request) throws IOException {
		Optional<User> optional = repository.findById(id);
		if (optional.isPresent()) {
			User user = optional.get();
			user.setEmail(request.getEmail());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setMobileNo(request.getMobileNo());
			user.setPassword(passwordEncoder.encode(request.getPassword()));

			repository.save(user);
			return utils.objectMapperSuccess("Successfully updated");
		}
		return utils.objectMapperError(NOT_FOUND);
	}

	@Override
	public String getById(String id) {
		Optional<User> optional = repository.findById(id);
		if (optional.isPresent()) {
			User user = optional.get();
			UserResponse response = mapper.setUserResponse(user);
//			List<ModulesDto> modulesDtos = userPermissionsService.getUsersPermission(user.getId());
//			response.setModulesDtos(modulesDtos);
			return utils.objectMapperSuccess(response, "User details");
		}
		return utils.objectMapperError(NOT_FOUND);
	}

	@Override
	public String getAll() {
		List<User> users = repository.findByIsDeleted(false);
		return utils.objectMapperListToJson(users, "users list");
	}

	@Override
	public Page<UserResponse> getAll(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo, pageSize);

		// Create a query object
		Query query = new Query();
		// Exclude deleted entries
		query.addCriteria(Criteria.where("isDeleted").is(false));
		// Apply filters if any-----------------

		// Get total count of documents
		long total = mongoTemplate.count(query, User.class);

		// Set pagination
		query.with(pageable);

		// Execute the query to get the results
		List<User> users = mongoTemplate.find(query, User.class);

		List<UserResponse> userResponses = users.stream().map(this::convertToUserResponse).collect(Collectors.toList());

		// Return the paginated results
		return new PageImpl<>(userResponses, pageable, total);

	}

	private UserResponse convertToUserResponse(User user) {
		UserResponse response = mapper.setUserResponse(user);
		return response;
	}

//	private Specification<User> userSpecification(String sortBy, String sortOrder, String searchTerm, Long roleId) {
//		Specification<User> specification = new Specification<User>() {
//
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//
//				List<Predicate> predicates = new ArrayList<>();
//
//				predicates.add(criteriaBuilder.equal(root.get(User_.IS_DELETED), false));
//
//				if (!ObjectUtils.isEmpty(searchTerm)) {
//
//					Predicate lastName = criteriaBuilder.like(root.get(User_.LAST_NAME), "%" + searchTerm + "%");
//
//					Predicate firstName = criteriaBuilder.like(root.get(User_.FIRST_NAME), "%" + searchTerm + "%");
//
//					Predicate or = criteriaBuilder.or(lastName, firstName);
//					predicates.add(or);
//
//				}
//
//				if (roleId != null) {
//					Predicate role = criteriaBuilder.equal(root.get(User_.ROLE), new Role(roleId));
//					predicates.add(role);
//				}
//
//				query.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})))
//						.orderBy(criteriaBuilder.desc(root.get(sortBy)));
//				return query.getRestriction();
//
//			}
//
//		};
//		return specification;
//	}

	@Override
	public String delete(String id) {
		Optional<User> optional = repository.findById(id);
		if (optional.isPresent()) {
			User users = optional.get();
			users.setDeleted(true);
			users = repository.save(users);

			repository.save(users);
			return utils.objectMapperSuccess("Successfully deleted");
		}
		throw new CustomException(NOT_FOUND);
	}

	@Override
	public String getAll(int pageNo, int pageSize, String sortBy, Sort sortOrder, int IsPagination, String searchTerm,
			Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}
}
