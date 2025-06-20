package com.artcode.thirtyfifty.coupons;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CouponsRepository extends MongoRepository<Coupons, String>{

	Optional<Coupons> findByIdAndIsDeleted(String id, boolean isDeleted);
	
	 List<Coupons> findByExpiredFalseAndEndDateBefore(LocalDate date);

}
