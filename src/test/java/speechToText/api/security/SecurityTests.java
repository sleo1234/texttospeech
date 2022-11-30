package speechToText.api.security;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import speechToText.api.user.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SecurityTests {
	@Autowired UserRepository userRepo;
	@Test
	public void testCreateUser() {
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String password = passwordEncoder.encode("12345678");
	        
	        User user = new User("safta.leonard@yahoo.com",password);
	        
	        User savedUser = userRepo.save(user);
	        
	        assertThat(savedUser.getId()).isGreaterThan(0);
	         
	}

}
