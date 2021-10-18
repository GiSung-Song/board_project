package project.board.web.user;

import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.board.service.post.PostService;
import project.board.service.user.UserService;
import project.board.web.SessionConst;
import project.board.web.dto.PostDto;
import project.board.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final PostService postService;

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
    public String findIdForm(@ModelAttribute("user") UserDto user) {
        return "user/findIdForm";
    }

    @PostMapping("/findId")
    public String findId(@Valid @ModelAttribute("user") FindIdForm user, BindingResult bindingResult, Model model) {

        if(!userService.findByLoginEmail(user.getUserEmail())) { //등록된 이메일이 없다면
            bindingResult.addError(new FieldError("error", "userEmail", "등록된 이메일이 없습니다."));
        }

        if(bindingResult.hasErrors()) {
            return "user/findIdForm";
        }

        UserDto userDto = userService.findByUserEmail(user.getUserEmail());
        model.addAttribute("userId", userDto.getUserId());

        return "user/userId";
    }

    @GetMapping("/findPw")
    public String findPwForm(@ModelAttribute("user") UserDto user) {
        return "user/findPassForm";
    }

    @PostMapping("/findPw")
    public String findPw(@Valid @ModelAttribute("user") FindPwForm user, BindingResult bindingResult, Model model) {

        if(!userService.findByLoginEmail(user.getUserEmail())) { //등록된 이메일이 없다면
            bindingResult.addError(new FieldError("error", "userEmail", "등록된 이메일이 없습니다."));
        }

        if(!userService.findByLoginId(user.getUserId())) { //등록된 아이디가 없다면
            bindingResult.addError(new FieldError("error", "userId", "등록된 아이디가 없습니다."));
        }

        if(bindingResult.hasErrors()) {
            return "user/findPassForm";
        }

        UserDto userDto = UserDto.builder()
                .userEmail(user.getUserEmail())
                .userId(user.getUserId()).build();

        UserDto toUser = userService.findPassWord(userDto);
        model.addAttribute("userPw", toUser.getUserPw());

        return "user/userPw";
    }

    @GetMapping("/delete")
    public String deleteUser(@ModelAttribute("user") UserDto userDto) {
        return "user/deleteUserForm";
    }

    @PostMapping("/delete")
    public String deleteUser(@Valid @ModelAttribute("user") DeleteUserForm user, BindingResult bindingResult, HttpServletRequest request) {

        if(!userService.findByLoginEmail(user.getUserEmail())) { //등록된 이메일이 없다면
            bindingResult.addError(new FieldError("error", "userEmail", "등록된 이메일이 없습니다."));
        }

        if(!userService.findByLoginId(user.getUserId())) { //등록된 아이디가 없다면
            bindingResult.addError(new FieldError("error", "userId", "등록된 아이디가 없습니다."));
        }

        HttpSession session = request.getSession(false);
        UserDto sessionUser = (UserDto) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(!sessionUser.getUserPw().equals(user.getUserPw())) {
            bindingResult.addError(new FieldError("error", "userPw", "비밀번호가 틀립니다."));
        }

        if(bindingResult.hasErrors()) {
            return "user/deleteUserForm";
        }

        UserDto userDto = UserDto.builder()
                .userEmail(user.getUserEmail())
                .userId(user.getUserId())
                .userPw(user.getUserPw()).build();

        UserDto toUser = userService.findUser(userDto);
        userService.deleteUser(toUser);
        session.invalidate();

        return "redirect:/";
    }

}
