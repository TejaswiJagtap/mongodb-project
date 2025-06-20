package com.artcode.thirtyfifty.master.courses;

public interface CoursesService {

	public String saveUpdate(CoursesDto request) throws Exception;

	public String getByAll(int pageNo, int pageSize, String sortBy, String sortOrder, String searchTerm,
			int IsPagination ,String subCategoryId) throws Exception;

	public String delete(String id) throws Exception;

	public String getById(String id) throws Exception;
}
