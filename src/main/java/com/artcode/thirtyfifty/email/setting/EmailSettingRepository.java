package com.artcode.thirtyfifty.email.setting;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSettingRepository extends MongoRepository<EmailSetting, String>{

	Optional<EmailSetting> findTopByOrderByIdAsc();

}
