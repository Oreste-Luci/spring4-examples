package cl.luci.example.springboot.repositories;

import cl.luci.example.springboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Oreste Luci
 */
public interface UserRepository extends JpaRepository<User,Long> {

    // Spring automatically provides this method by prefixing findBy with column name
    User findByEmail(String email);

    // Spring automatically provides this method by prefixing findBy with column name
    User findByForgotPasswordCode(String forgotPasswordCode);
}
