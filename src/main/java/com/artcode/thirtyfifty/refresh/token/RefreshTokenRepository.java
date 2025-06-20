package com.artcode.thirtyfifty.refresh.token;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

	Optional<RefreshToken> findByUserId(String userId);

	Optional<RefreshToken> findByRefreshToken(String token);
	

}
