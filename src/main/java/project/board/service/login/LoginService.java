package project.board.service.login;

import project.board.domain.user.User;
import project.board.web.dto.UserDto;


public interface LoginService {
    User login(String userId, String userPw);
    UserDto userLogin(String userId, String userPw);
}
