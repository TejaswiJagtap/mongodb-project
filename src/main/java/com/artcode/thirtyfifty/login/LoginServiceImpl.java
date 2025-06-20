package com.artcode.thirtyfifty.login;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.artcode.thirtyfifty.constants.ErrorCode;
import com.artcode.thirtyfifty.email.setting.EmailSetting;
import com.artcode.thirtyfifty.email.setting.EmailSettingRepository;
import com.artcode.thirtyfifty.exception.CustomMessageApp;
import com.artcode.thirtyfifty.refresh.token.RefreshToken;
import com.artcode.thirtyfifty.refresh.token.RefreshTokenService;
import com.artcode.thirtyfifty.spring.security.JwtService;
import com.artcode.thirtyfifty.spring.security.PasswordUtil;
import com.artcode.thirtyfifty.spring.security.UserDto;
import com.artcode.thirtyfifty.user.User;
import com.artcode.thirtyfifty.user.UserRepository;
import com.artcode.thirtyfifty.user.UserRequest;
import com.artcode.thirtyfifty.users.otp.UsersOtp;
import com.artcode.thirtyfifty.users.otp.UsersOtpRepository;
import com.artcode.thirtyfifty.utils.FunctionUtils;
import com.artcode.thirtyfifty.utils.JavaMailUtil;
import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.transaction.Transactional;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JsonUtils utils;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private EmailSettingRepository emailSettingRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsersOtpRepository usersOtpRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private final KafkaProducerService kafkaProducerService;

	public LoginServiceImpl(KafkaProducerService kafkaProducerService) {
		this.kafkaProducerService = kafkaProducerService;
	}

	@Value("${jwt.header}")
	private String tokenHeader;

	String smsMessage = "Login otp: ";

	String loginOtpSuccessMsg = "Otp sent ";

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String signup(UserRequest request) throws IOException {
		if (userRepository.existsByEmailAndIsDeleted(request.getEmail(), false)) {
			return utils.objectMapperError("User already registered");
		}
		User user = new User();
		user.setEmail(request.getEmail());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setMobileNo(request.getMobileNo());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("USER");

		userRepository.save(user);
		return utils.objectMapperSuccess("Successfully saved");
	}

	@Override
	public String login(LoginRequest request) throws Exception {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmailAndIsDeleted(request.getEmail(), false);
		final String token = jwtService.generateToken(authentication, user.getRole());
		RefreshToken refreshToken1 = refreshTokenService.refreshToken(user.getId());
		UserDto userDto = null;
		userDto = new UserDto();
		userDto.setRefreshToken(refreshToken1.getRefreshToken());
		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setMobileNo(user.getMobileNo());
		userDto.setRole(user.getRole());
		userDto.setToken(token);
//		userDto.setActiveSessionId(user.getSession() != null ? user.getSession().getId() : null);

//		user.setIpAddress(request.getIpAddress());
//		user.setBrowser(request.getBrowser());
//		user.setAccessType(request.getAccessType());
//		user.setOs(request.getOs());
//		user.setVersion(request.getVersion());
		userRepository.save(user);

		return utils.objectMapperSuccess(userDto, "Logged In successfully");
	}

	@Override
	public String forgotPassword(String email) throws Exception {
		User user = this.userRepository.findByEmailAndIsDeleted(email, false);
		if (user == null) {
			return utils.objectMapperError("email is not valid");
		}

		UsersOtp usersOtp = usersOtpRepository.findByEmail(email);
		if (usersOtp == null) {
			usersOtp = new UsersOtp();
		}
		usersOtp.setEmail(email);
		String otp = FunctionUtils.generateOtp(4);
		usersOtp.setOtp(otp);
		usersOtpRepository.save(usersOtp);

		ForgotPasswordEvent forgotPasswordEvent = new ForgotPasswordEvent(email, otp);
		kafkaProducerService.sendForgotPasswordEvent("forgot-password-topic", forgotPasswordEvent);
		System.out.println("otp " + usersOtp.getOtp());
		return utils.objectMapperSuccess("Otp Send Successfully");
	}

	@Override
	public String verifyOtp(String otp, String email) throws Exception {
		User user = userRepository.findByEmailAndIsDeleted(email, false);
		if (!user.getOtp().equals(otp)) {
			throw new CustomMessageApp(ErrorCode.C103.getMessage());
		}
		// return UserMapper.setDto(user);
		return utils.objectMapperSuccess("Otp Verified Successfully");
	}

	@Override
	public String refreshtoken(LoginRequest loginRequest) {
		return tokenHeader;
	}

	@Override
	public String setNewPassword(String email, String password) {
		User user = userRepository.findByEmailAndIsDeleted(email, false);
		if (user == null) {
			return utils.objectMapperError("Enter correct email");
		}
		user.setPassword(PasswordUtil.getPasswordHash(password));
		this.userRepository.save(user);
		return utils.objectMapperSuccess("your password changed");
	}

	@Override
	public String changePassword(ChangePasswordRequest request) {
		if (request.getNewPassword().equals(request.getOldPassword())) {

			return utils.objectMapperError("New password can not be old password");
		}

		User user = this.userRepository.findByIdAndIsDeleted(request.getId(), false);

		if (PasswordUtil.matchPassword(request.getOldPassword(), user.getPassword())) {
			user.setPassword(PasswordUtil.getPasswordHash(request.getNewPassword()));

			this.userRepository.save(user);
			return utils.objectMapperSuccess("Your password successfully changed");

		}

		return utils.objectMapperError("Enter correct old password");
	}

	public String resendOtp(String email) throws Exception {
		User user = userRepository.findByEmailAndIsDeleted(email, false);
		if (user == null) {
			throw new CustomMessageApp(ErrorCode.C100.getMessage());
		}
		String otp = FunctionUtils.generateOtp(4);
		user.setOtp(otp);
		user = userRepository.save(user);
		// Send email
		EmailSetting emailSetting = emailSettingRepository.findTopByOrderByIdAsc()
				.orElseThrow(() -> new Exception("Email settings not found"));

		JavaMailUtil.sendMail("Forgot Password OTP", email, "Your OTP is: " + otp, null, emailSetting.getEmail(),
				emailSetting.getPassword());
//		sendSms.sendMessage(smsMessage + otpNo, user.getMobileNo());
		return utils.objectMapperSuccess(loginOtpSuccessMsg);
	}

}
