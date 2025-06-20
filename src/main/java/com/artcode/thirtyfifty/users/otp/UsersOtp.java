
package com.artcode.thirtyfifty.users.otp;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author atulg
 *
 */
@Document(collection = "user-otp")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersOtp {

	@Id
	private String id;

	private String email;

	private String otp;

	public UsersOtp(String id) {
		super();
		this.id = id;
	}
}
