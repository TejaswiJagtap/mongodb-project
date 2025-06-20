package com.artcode.thirtyfifty.coupons;

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
@RequestMapping(RequestMappingConstant.COUPONS)
@Slf4j
public class CouponsController {

	@Autowired
	private CouponsService service;

	@PostMapping
	public ResponseEntity<String> save(@RequestBody CouponsDto request) throws Exception {
		log.info("save coupons request : {}", request.toString());
		return new ResponseEntity<String>(service.save(request), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody CouponsDto request, @PathVariable("id") String id)
			throws Exception {
		log.info("save coupons request : {}", request.toString());
		return new ResponseEntity<String>(service.update(request, id), HttpStatus.OK);
	}

	@GetMapping("/get-all")
	public ResponseEntity<String> getByAll(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
			@RequestParam(name = "searchTerm", required = false) String searchTerm,
			@RequestParam(name = "isPagination", defaultValue = "1") int isPagination) throws Exception {
		log.info("Get All  coupons");
		return new ResponseEntity<String>(
				service.getByAll(pageNo, pageSize, sortBy, sortOrder, searchTerm, isPagination), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> getById(@PathVariable("id") String id) throws Exception {
		log.info("get coupons  details by id : {}", id);
		return new ResponseEntity<String>(service.getById(id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id) throws Exception {
		log.info("Delete coupons by id : {}", id);
		return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
	}

	@PutMapping("/re-active/{id}")
	public ResponseEntity<String> updateStatus(@RequestBody CouponsDto request ,@PathVariable("id") String id) throws Exception {
		log.info("change status of  coupons by request : {}", request);
		return new ResponseEntity<String>(service.updateStatus(request,id), HttpStatus.OK);
	}

}
