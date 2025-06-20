package com.artcode.thirtyfifty.flashcard;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardRepository extends MongoRepository<FlashCard, String> {

	Optional<FlashCard> findByIdAndIsDeleted(String id, boolean isDeleted);

}
