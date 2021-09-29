package project.board.service.user;

import project.board.domain.user.User;
import project.board.domain.user.UserRepository;
import project.board.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public void signUp(UserDto userDto) {
        userRepository.save(User.builder()
        .userId(userDto.getUserId())
        .userPw(userDto.getUserPw())
        .userName(userDto.getUserName())
        .userEmail(userDto.getUserEmail())
        .build());
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userRepository.findAll().stream()
                .filter(m -> m.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public Optional<User> findByUserEmail(String userEmail) {
        return userRepository.findAll().stream()
                .filter(m -> m.getUserEmail().equals(userEmail))
                .findFirst();
    }

}
