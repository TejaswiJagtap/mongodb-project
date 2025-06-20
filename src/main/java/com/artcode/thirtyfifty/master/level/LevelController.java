package com.artcode.thirtyfifty.master.level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.artcode.thirtyfifty.constants.RequestMappingConstant;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RequestMappingConstant.LEVEL)
@Slf4j
public class LevelController {

	@Autowired
	private LevelService service;

	@PostMapping(consumes = { "multipart/form-data" }, produces = { "application/json" })
	public ResponseEntity<String> saveUpdate(@RequestParam("request") String request,
			@RequestParam(name = "image", required = false) MultipartFile image) throws Exception {
		log.info("save course request : {}", request.toString());
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		LevelDto entity = mapper.readValue(request, LevelDto.class);
		return new ResponseEntity<String>(service.saveUpdate(entity, image), HttpStatus.CREATED);

	}
//	@PostMapping
//	public ResponseEntity<String> saveUpdate(@RequestBody LevelDto levelDto) throws Exception {
//		log.info("save level request : {}");
//
//		return new ResponseEntity<String>(service.saveUpdate(levelDto), HttpStatus.CREATED);
//
//	}

	@GetMapping("/get-all")
	public ResponseEntity<String> getByAll(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
			@RequestParam(name = "searchTerm", required = false) String searchTerm,
			@RequestParam(name = "IsPagination", defaultValue = "1") int IsPagination) throws Exception {
		log.info("Get All  level");
		return new ResponseEntity<String>(
				service.getByAll(pageNo, pageSize, sortBy, sortOrder, searchTerm, IsPagination), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> getById(@PathVariable("id") String id) throws Exception {
		log.info("get course  details by id : {}", id);
		return new ResponseEntity<String>(service.getById(id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id) throws Exception {
		log.info("Delete course by id : {}", id);
		return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
	}
}
