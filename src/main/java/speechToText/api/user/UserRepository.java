package speechToText.api.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import speechToText.api.security.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByEmail(String email);
	
}
