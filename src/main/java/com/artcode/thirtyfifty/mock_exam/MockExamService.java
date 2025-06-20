package com.artcode.thirtyfifty.mock_exam;

public interface MockExamService {

	public String save(MockExamDto request) throws Exception;
	
	public String update(MockExamDto request ,String id) throws Exception;

	public String getByAll(int pageNo, int pageSize, String sortBy, String sortOrder, String searchTerm,
			int IsPagination, String courseId) throws Exception;

	public String delete(String id) throws Exception;

	public String getById(String id) throws Exception;
}
