package com.artcode.thirtyfifty.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	boolean existsByEmailAndIsDeleted(String email, boolean isDeleted);

	User findByEmailAndIsDeleted(String email, boolean isDeleted);

	Optional<User> findById(Long userId);

	User findByIdAndIsDeleted(Long id, boolean b);

	List<User> findByRoleAndIsDeleted(String value, boolean isDeleted);

	List<User> findByIsDeleted(boolean isDeleted);

}
