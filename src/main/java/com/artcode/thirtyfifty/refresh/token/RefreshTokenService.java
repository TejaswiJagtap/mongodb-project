package com.artcode.thirtyfifty.refresh.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcode.thirtyfifty.user.UserRepository;

@Service
public class RefreshTokenService {
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

//	private long refreshTokenDurationMs = 172800000;// 5hr

	@Autowired
	private UserRepository userRepository;



//	@Override
	public RefreshToken refreshToken(String userId) throws Exception{

		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUser(userRepository.findById(userId).orElseThrow(() -> new Exception("user not found")));
		refreshToken.setExpiryDateTime(LocalDateTime.now().plusHours(72));
		  refreshToken.setRefreshToken(generateRandomToken());
		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;

	}

	private String generateRandomToken() {
		return LocalDateTime.now().toString() + "-" + Math.random();
}

	public RefreshToken verifyExpiration(RefreshToken token) throws Exception {
		if (token.getExpiryDateTime().compareTo(LocalDateTime.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new Exception("Refresh token was expired. Please make a new signin request");
			// return utils.objectMapperError(token.getRefreshToken(), "Refresh token was
			// expired. Please make a new signin request");
		}

		return token;
	}

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByRefreshToken(token);
	}

}
