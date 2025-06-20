package com.artcode.thirtyfifty.master.level;

import org.springframework.web.multipart.MultipartFile;

public interface LevelService {

	public String saveUpdate(LevelDto request ,MultipartFile image) throws Exception;

	public String getByAll(int pageNo, int pageSize, String sortBy, String sortOrder, String searchTerm,
			int IsPagination) throws Exception;

	public String delete(String id) throws Exception;

	public String getById(String id) throws Exception;
}
