/**
 * 
 */
package com.artcode.thirtyfifty.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcode.thirtyfifty.exception.CustomException;
import com.artcode.thirtyfifty.user.User;
import com.artcode.thirtyfifty.user.UserRepository;
import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.transaction.Transactional;

/**
 * @author atulg
 *
 */
@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	private SessionRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JsonUtils utils;
	
	private static final String NOT_FOUND = "Record not found";

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String save(SessionRequest request) throws Exception {
		Session sessions = new Session();
		sessions.setEndDate(request.getEndDate());
		sessions.setIsActive(request.getIsActive());
		sessions.setName(request.getName());
		sessions.setStartDate(request.getStartDate());
		repository.save(sessions);
		return utils.objectMapperSuccess("Successfully saved");
	}

	@Override
	public String update(String id, SessionRequest request) {
		Optional<Session> optional = repository.findById(id);
		if (optional.isPresent()) {
			Session sessions = optional.get();
			sessions.setEndDate(request.getEndDate());
			sessions.setIsActive(request.getIsActive());
			sessions.setName(request.getName());
			sessions.setStartDate(request.getStartDate());
			sessions.setCreatedBy(sessions.getId());
			sessions.setCreatedByUserName(sessions.getCreatedByUserName());
			repository.save(sessions);
			return utils.objectMapperSuccess("Successfully saved");
		}
		throw new CustomException(NOT_FOUND);
	}

	@Override
	public String getById(String id) {
		Optional<Session> optional = repository.findById(id);
		if (optional.isPresent()) {
			SessionResponse response = new SessionResponse();
			response.setId(optional.get().getId());
			response.setEndDate(optional.get().getEndDate());
			response.setIsActive(optional.get().getIsActive());
			response.setName(optional.get().getName());
//			response.setCollege(optional.get().getCollege());
			response.setStartDate(optional.get().getStartDate());
			return utils.objectMapperSuccess(response, "Session details");
		}
		throw new CustomException(NOT_FOUND);
	}

	@Override
	public String getAll(String status) {
		List<Session> data = new ArrayList<>();
		List<SessionResponse> sessionsResponses = new ArrayList<>();
		if (status.equals("ALL")) {
			data = repository.findByIsDeleted(false);
		} else {
			data = repository.findByIsActiveAndIsDeleted(status, false);

		}
		for (Session session : data) {
			SessionResponse response = new SessionResponse();
			response.setId(session.getId());
			response.setEndDate(session.getEndDate());
			response.setIsActive(session.getIsActive());
			response.setName(session.getName());
			response.setStartDate(session.getStartDate());
			sessionsResponses.add(response);
		}

		return utils.objectMapperListToJson(sessionsResponses, "Session list");
	}

	@Override
	public String updateUserSession(String userId, String sessionId) throws Exception {
		Optional<User> optional = userRepository.findById(userId);
		if (optional.isPresent()) {
			User users = optional.get();
			users.setSession(repository.findById(sessionId).orElseThrow(() -> new Exception("session not found")));
			userRepository.save(users);
			return utils.objectMapperSuccess("Successfully updated");
		}
		throw new CustomException(NOT_FOUND);
	}

	@Override
	public String updateStatus(String id, String isActive) {
		Optional<Session> optional = repository.findById(id);
		if (optional.isPresent()) {
			Session sessions = optional.get();
			sessions.setIsActive(isActive);
			repository.save(sessions);
			return utils.objectMapperSuccess(sessions.getName(), "Session status updated");
		}
		throw new CustomException(NOT_FOUND);
	}

	@Override
	public String delete(String id) {
		Optional<Session> optional = repository.findById(id);
		if (optional.isPresent()) {
			Session sessions = optional.get();
			sessions.setIsDeleted(true);
			repository.save(sessions);
			return utils.objectMapperSuccess("Session deleted successfully");
		}
		throw new CustomException(NOT_FOUND);

	}

	@Override
	public String getById(String id, String collegeId) {
		Optional<Session> optional = repository.findById(id);
		if (optional.isPresent()) {
			Session session = optional.get();
			SessionResponse response = new SessionResponse();
			response.setId(session.getId());
			response.setEndDate(session.getEndDate());
			response.setIsActive(session.getIsActive());
			response.setName(session.getName());
			response.setStartDate(session.getStartDate());

			return utils.objectMapperSuccess(response, "get on Session details  with collegeId");
		}
		throw new CustomException(NOT_FOUND);

	}

}