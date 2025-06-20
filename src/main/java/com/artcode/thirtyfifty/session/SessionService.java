/**
 * 
 */
package com.artcode.thirtyfifty.session;

;

/**
 * @author atulg
 *
 */
public interface SessionService {

	String save(SessionRequest request) throws Exception;

	String update(String id, SessionRequest request);

	String getById(String id);

	String getById(String id, String collegeId);

	String getAll(String status);

//	String getAll(Long collegeId, String status);

//	String saveAdmin(SessionsRequest request) throws Exception;
//
//	String getAllAdmin(String isActve);

	String updateUserSession(String userId, String sessionId) throws Exception;

	String updateStatus(String id, String isActive);

//	String deleteById(Long id);

	String delete(String id);
	// public String updateUserSession(Long userId, Long sessionId);

}
