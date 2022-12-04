package speechToText.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import speechToText.api.user.UserRepository;

@Service
public class UserService {
	@Autowired UserRepository userRepo;
	
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	@Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
	
	public User saveUser (User user) {
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		return userRepo.save(user);
	}

}
