package com.artcode.thirtyfifty.master.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.artcode.thirtyfifty.constants.RequestMappingConstant;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RequestMappingConstant.CATEGORY)
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService service;

	@PostMapping
	public ResponseEntity<String> saveUpdate(@RequestBody CategoryDto request) throws Exception {
		log.info("save category request : {}", request.toString());
		return new ResponseEntity<String>(service.saveUpdate(request), HttpStatus.CREATED);
	}

	@GetMapping("/get-all")
	public ResponseEntity<String> getByAll(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
			@RequestParam(name = "searchTerm", required = false) String searchTerm,
			@RequestParam(name = "IsPagination", defaultValue = "1") int IsPagination,
			@RequestParam(name = "levelId", required = false) String levelId) throws Exception {
		log.info("Get All  category");
		return new ResponseEntity<String>(
				service.getByAll(pageNo, pageSize, sortBy, sortOrder, searchTerm, IsPagination, levelId),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> getById(@PathVariable("id") String id) throws Exception {
		log.info("get category  details by id : {}", id);
		return new ResponseEntity<String>(service.getById(id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id) throws Exception {
		log.info("Delete category by id : {}", id);
		return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
	}
}
