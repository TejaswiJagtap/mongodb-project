package com.artcode.thirtyfifty.master.category;

public interface CategoryService {

	public String saveUpdate(CategoryDto request) throws Exception;

	public String getByAll(int pageNo, int pageSize,String sortBy, String sortOrder, String searchTerm,
			int IsPagination, String levelId) throws Exception;

	public String delete(String id) throws Exception;

	public String getById(String id) throws Exception;
}
