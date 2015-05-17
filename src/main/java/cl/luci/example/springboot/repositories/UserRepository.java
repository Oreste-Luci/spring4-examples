package cl.luci.example.springboot.repositories;

import cl.luci.example.springboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Oreste Luci
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
