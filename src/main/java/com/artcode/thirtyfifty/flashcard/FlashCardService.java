package com.artcode.thirtyfifty.flashcard;

public interface FlashCardService {

	public String saveUpdate(FlashCardDto request) throws Exception;

	public String getByAll(int pageNo, int pageSize, String sortBy, String sortOrder, String searchTerm,
			int IsPagination, String courseId) throws Exception;

	public String delete(String id) throws Exception;

	public String getById(String id) throws Exception;
}
