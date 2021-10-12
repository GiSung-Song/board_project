package project.board.web.user;

import org.springframework.validation.FieldError;
import project.board.domain.user.User;
import project.board.service.user.UserService;
import project.board.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("user") UserDto user) {
        log.info("user/add 접속");
        return "user/addUserForm";
    }

    @PostMapping("/add")
    public String signUp(@Valid @ModelAttribute("user") AddUserForm user, BindingResult bindingResult) {

        if(userService.findByLoginId(user.getUserId())) { //아이디 중복 체크
            bindingResult.addError(new FieldError("error", "userId", user.getUserId(),
                    false, null, null, "중복된 아이디입니다."));
        }

        if(userService.findByLoginEmail(user.getUserEmail())) { //이메일 중복 체크
            bindingResult.addError(new FieldError("error", "userEmail", user.getUserEmail(),
                    false, null, null, "중복된 이메일입니다."));
        }

        if(bindingResult.hasErrors()) {
            return "user/addUserForm";
        }

        log.info("userId={}, userPw={}, userName={}", user.getUserId(), user.getUserPw(), user.getUserName());

        UserDto userDto = UserDto.builder()
                .userId(user.getUserId())
                .userPw(user.getUserPw())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .build();

        userService.signUp(userDto);
        return "redirect:/";

    }

    @GetMapping("/findId")
    public String findForm(@ModelAttribute("user") UserDto user) {
        return "user/findUserForm";
    }

}
