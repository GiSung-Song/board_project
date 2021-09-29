package project.board.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User Jpa
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
