package project.board.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.board.domain.user.User;
import project.board.domain.user.UserRepository;
import project.board.web.dto.UserDto;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final UserRepository userRepository;

    @Override
    public User login(String userId, String userPw) {
        return userRepository.findAll().stream()
                .filter(m -> m.getUserId().equals(userId)).findFirst()
                .filter(m -> m.getUserPw().equals(userPw))
                .orElse(null);
    }

    @Override
    public UserDto userLogin(String userId, String userPw) {
        User user = userRepository.findAll().stream()
                .filter(m -> m.getUserId().equals(userId)).findFirst()
                .filter(m -> m.getUserPw().equals(userPw))
                .orElse(null);

        UserDto userDto = UserDto.builder()
                .userName(user.getUserName())
                .userPw(user.getUserPw())
                .userEmail(user.getUserEmail())
                .userId(user.getUserId())
                .build();

        return userDto;
    }


}
