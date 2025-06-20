/**
 * 
 */
package com.artcode.thirtyfifty.session;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * @author atulg
 *
 */

@RequestMapping("/sessions")
public class SessionController {

	private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

	@Autowired
	private SessionService service;

	@PostMapping
	public ResponseEntity<String> save(@RequestBody SessionRequest request) throws Exception {
		logger.info(" Save sessions details request : {}", request);

		return new ResponseEntity<>(service.save(request), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody SessionRequest request) {
		logger.info("Update sessions detail id : {}, request : {} ", id, request);
		return new ResponseEntity<>(service.update(id, request), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> getById(@PathVariable String id) {
		logger.info("Fetching sessions details with id {}", id);
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}

//	@GetMapping("/With-college-ID/{id}")
//	public ResponseEntity<String> getByIdWithSchoolId(@PathVariable Long id, @RequestParam("collegeId") Long collegeId) {
//		logger.info("Fetching sessions details with id {}", id);
//		return new ResponseEntity<>(service.getById(id, collegeId), HttpStatus.OK);
//	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<String> deleteById(@PathVariable Long id) {
//		logger.info("Fetching sessions details with id {}", id);
//		return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
//	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable String id) {
		logger.info("Fetching sessions details with id {}", id);
		return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<String> getAll(@RequestParam("status") String status) {
		logger.info("Get sessions list by status : {} ",status);
		return new ResponseEntity<>(service.getAll(status), HttpStatus.OK);
	}

//	@GetMapping("/get-session-by-collegeId")
//	public ResponseEntity<String> getAll(@RequestParam(name = "isActive", defaultValue = "YES") String isActve,
//			@RequestParam("collegeId") Long collegeId) {
//		logger.info("Get sessions list isActive : {} and collegeId :{}", isActve, collegeId);
//		return new ResponseEntity<>(service.getAll(collegeId, isActve), HttpStatus.OK);
//	}

	@GetMapping("/update-active-status")
	public ResponseEntity<String> updateActiveStatus(@RequestParam("id") String id,
			@RequestParam(name = "isActive") String isActive) {
		logger.info("Update  sessions  by id : {} and  isActive status: {}", id, isActive);
		return new ResponseEntity<>(service.updateStatus(id, isActive), HttpStatus.OK);
	}

	@GetMapping("/user-update-session")
	public ResponseEntity<String> updateUserActiveStatus(@RequestParam("id") String id, @RequestParam String userId) throws Exception {
		logger.info("Update user   sessions  by id : {} and  status: {}", id);
		return new ResponseEntity<>(service.updateUserSession(userId, id), HttpStatus.OK); 
	}

}
