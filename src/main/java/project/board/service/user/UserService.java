package project.board.service.user;

import project.board.domain.user.User;
import project.board.web.dto.UserDto;

import java.util.Optional;

public interface UserService {

    void signUp(UserDto userDto);
    Optional<User> findByUserId(String userId);
    //Optional<User> findByUserEmail(String userEmail);
    boolean findByLoginId(String userId);
    boolean findByLoginEmail(String userEmail);
    UserDto findByUserEmail(String userEmail);
}
