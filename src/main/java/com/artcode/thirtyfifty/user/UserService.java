package com.artcode.thirtyfifty.user;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface UserService {

	public String update(String id, UserRequest request) throws IOException;

	public String getById(String id);

	public Page<UserResponse> getAll(int pageNo, int pageSize);

	public String getAll(int pageNo, int pageSize, String sortBy, Sort sortOrder, int IsPagination, String searchTerm,
			Long roleId);

	public String delete(String id);

	public String getAll();
}
