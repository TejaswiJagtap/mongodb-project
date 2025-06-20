package com.artcode.thirtyfifty.session;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends MongoRepository<Session, String> {

	public List<Session> findByIsActive(String value);

	public List<Session> findByIsDeleted(boolean isDeleted);

	public List<Session> findByIsActiveAndIsDeleted(String value, boolean isDeleted);

}
