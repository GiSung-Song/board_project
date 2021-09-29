package project.board.web.user;

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

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
@Slf4j
public class AddUserController {

    private final UserService userService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("user") UserDto user) {
        log.info("user/add 접속");
        return "user/addUserForm";
    }

    @PostMapping("/add")
    public String signUp(@Valid @ModelAttribute("user") AddUserForm user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user/addUserForm";
        }

        log.info("userId={}, userPw={}, userName={}", user.getUserId(), user.getUserPw(), user.getUserName());

        UserDto userDto = UserDto.builder()
                .userId(user.getUserId())
                .userPw(user.getUserPw())
                .userName(user.getUserName())
                .build();

        userService.signUp(userDto);
        return "redirect:/";

    }
}
