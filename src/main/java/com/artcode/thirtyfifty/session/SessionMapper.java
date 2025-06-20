package com.artcode.thirtyfifty.session;

import org.springframework.stereotype.Component;

@Component
public class SessionMapper {

	public SessionResponse convertSessionToResponse(Session session) {

		SessionResponse response = new SessionResponse();
		response.setId(session.getId());
		//response.setAdminSessionId(session.getAdminSessionId());
		response.setStartDate(session.getStartDate());
		response.setEndDate(session.getEndDate());
		response.setName(session.getName());
		response.setIsActive(session.getIsActive());
		response.setStatus(session.getStatus());
		return response;
	}

	public Session convertResponseToSession(SessionResponse response) {
		Session session = new Session();
		session.setId(response.getId());
		//session.setAdminSessionId(response.getAdminSessionId());
		session.setStartDate(response.getStartDate());
		session.setEndDate(response.getEndDate());
		session.setName(response.getName());
		session.setIsActive(response.getIsActive());
		session.setStatus(response.getStatus());
		return session;
	}

	public SessionRequest convertSessionToRequest(Session session) {
		SessionRequest request = new SessionRequest();
		//request.setAdminSessionId(session.getAdminSessionId());
		request.setStartDate(session.getStartDate());
		request.setEndDate(session.getEndDate());
		request.setName(session.getName());
		request.setIsActive(session.getIsActive());
		//request.setStatus(session.getStatus());
		return request;
	}

	public Session convertRequestToSession(SessionRequest request) {
		Session session = new Session();
		//session.setAdminSessionId(request.getAdminSessionId());
		session.setStartDate(request.getStartDate());
		session.setEndDate(request.getEndDate());
		session.setName(request.getName());
		session.setIsActive(request.getIsActive());
		//session.setStatus(request.getStatus());
		return session;
	}

}
