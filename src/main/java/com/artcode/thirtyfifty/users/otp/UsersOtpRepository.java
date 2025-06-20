
package com.artcode.thirtyfifty.users.otp;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersOtpRepository extends MongoRepository<UsersOtp, String> ,PagingAndSortingRepository<UsersOtp, String> {

	public UsersOtp findByEmail(String email);

}
