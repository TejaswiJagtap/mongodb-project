package com.artcode.thirtyfifty.mock_exam;

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
import org.springframework.web.bind.annotation.RestController;

import com.artcode.thirtyfifty.constants.RequestMappingConstant;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RequestMappingConstant.MOCK_EXAM)
@Slf4j
public class MockExamController {

	@Autowired
	private MockExamService service;

	@PostMapping("/add")
	public ResponseEntity<String> save(@RequestBody MockExamDto request) throws Exception {
		log.info("save mock exam request : {}", request.toString());
		return new ResponseEntity<String>(service.save(request), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody MockExamDto request ,@PathVariable("id") String id) throws Exception {
		log.info("update mock exam request : {}", request.toString());
		return new ResponseEntity<String>(service.update(request,id), HttpStatus.CREATED);
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<String> getByAll(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
			@RequestParam(name = "searchTerm", required = false) String searchTerm,
			@RequestParam(name = "isPagination", defaultValue = "1") int isPagination,
			@RequestParam(name = "courseId",required = false) String courseId)
			throws Exception {
		log.info("Get All mock exam ");
		return new ResponseEntity<String>(service.getByAll(pageNo, pageSize, sortBy, sortOrder, searchTerm,
				isPagination, courseId), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> getById(@PathVariable("id") String id) throws Exception {
		log.info("get mock exam  details by id : {}", id);
		return new ResponseEntity<String>(service.getById(id), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") String id) throws Exception {
		log.info("delete mock exam   details by id : {}", id);
		return new ResponseEntity<String>(service.delete(id), HttpStatus.OK);
	}
}
