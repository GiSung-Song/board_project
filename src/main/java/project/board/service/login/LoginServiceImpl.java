package project.board.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.board.domain.user.User;
import project.board.domain.user.UserRepository;
import project.board.web.dto.UserDto;

import java.util.Optional;
import java.util.OptionalInt;

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

    /*
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
     */

    @Override
    public UserDto doLogin(UserDto userdto) {
        Optional<User> user = userRepository.findAll().stream()
                .filter(m -> m.getUserId().equals(userdto.getUserId()))
                .filter(m -> m.getUserPw().equals(userdto.getUserPw()))
                .findFirst();

        if (user.isPresent()) {
            User userPresent = user.get();

            UserDto userDto = UserDto.builder()
                    .userName(userPresent.getUserName())
                    .userPw(userPresent.getUserPw())
                    .userEmail(userPresent.getUserEmail())
                    .userId(userPresent.getUserId())
                    .build();

            return userDto;
        }

        return null;
    }
}
