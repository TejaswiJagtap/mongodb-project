package com.artcode.thirtyfifty.refresh.token;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.artcode.thirtyfifty.exception.CustomException;
import com.artcode.thirtyfifty.spring.security.JwtService;

@RestController
@RequestMapping()
public class RefreshTokenController {
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

//	@PostMapping("/refreshtoken")
//	public ResponseEntity<?> refreshtoken(@RequestParam String token) {
//
//		log.info("refresh token");
//		Optional<RefreshToken> optional = this.refreshTokenRepository.findByRefreshToken(token);
//		if (optional.isPresent()) {
//			RefreshToken refreshToken = optional.get();
//			Long masterUserId = refreshToken.getUser().getId();
//
//			return refreshTokenService.findByToken(token).map(t -> {
//				try {
//					return refreshTokenService.verifyExpiration(t);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				return t;
//			}).map(RefreshToken::getUser).map(user -> {
//
//				String Usertoken =jwtTokenProvider.generateToken(token);
//				return ResponseEntity.ok(new TokenRefreshResponse(Usertoken, token));
//			}).orElseThrow(() -> new CustomException("Refresh token is not in database!"));
//		}
//		return null;
//
//	}
	
	 @PostMapping("/refreshtoken")
	 public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestParam String token) {
		    Optional<RefreshToken> optional = refreshTokenRepository.findByRefreshToken(token);
		    if (optional.isPresent()) {
		        RefreshToken refreshToken = optional.get();
		        refreshToken.getUser().getId();

		        // Retrieve the authentication object from the SecurityContextHolder
		        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        
		        // Generate a new access token using the authentication object
		        String newAccessToken = jwtService.generateToken(authentication,refreshToken.getUser().getRole());

		        // Return the new access token along with the original refresh token
		        return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, token));
		    } else {
		        throw new CustomException("Refresh token is not in the database!");
		    }
		}
	
	

}
