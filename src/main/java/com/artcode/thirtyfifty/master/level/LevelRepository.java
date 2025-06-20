package com.artcode.thirtyfifty.master.level;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends MongoRepository<Level, String>,PagingAndSortingRepository<Level, String>{

	Level findByIdAndIsDeleted(String id, boolean iseDeleted);

}
