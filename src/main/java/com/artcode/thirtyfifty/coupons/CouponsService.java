package com.artcode.thirtyfifty.coupons;

import com.artcode.thirtyfifty.exception.CustomException;

public interface CouponsService {

	public String save(CouponsDto request) throws CustomException;

	public String update(CouponsDto request, String id) throws CustomException;

	public String getByAll(int pageNo, int pageSize, String sortBy, String sortOrder, String searchTerm,
			int isPagination) throws CustomException;

	public String delete(String id) throws CustomException;

	public String getById(String id) throws CustomException;

	public String updateStatus(CouponsDto request, String id) throws CustomException;

	public void updateExpiredCoupons();
}
