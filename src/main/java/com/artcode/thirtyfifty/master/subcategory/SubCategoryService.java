package com.artcode.thirtyfifty.master.subcategory;

public interface SubCategoryService {

	public String saveUpdate(SubCategoryDto request) throws Exception;

	public String getByAll(int pageNo, int pageSize,String sortBy, String sortOrder, String searchTerm,
			int IsPagination,String categoryId) throws Exception;

	public String delete(String id) throws Exception;

	public String getById(String id) throws Exception;
}
