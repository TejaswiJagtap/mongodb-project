package com.artcode.thirtyfifty.refresh.token;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.entity.AbstractEntity;
import com.artcode.thirtyfifty.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document("refresh-token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RefreshToken extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private User user;

	private String refreshToken;

	@JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@Column(name="expiry_date_time")
	private LocalDateTime expiryDateTime;
}
