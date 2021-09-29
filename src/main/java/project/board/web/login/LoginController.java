package project.board.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.board.domain.user.User;
import project.board.service.login.LoginService;
import project.board.service.user.UserService;
import project.board.web.SessionConst;
import project.board.web.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    private String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        log.info("/login 접속");
        return "login/loginForm";
    }

    @PostMapping("/login")
    private String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                         @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {

        if(bindingResult.hasErrors())
            return "login/loginForm";

        UserDto loginUser = loginService.userLogin(form.getLoginId(), form.getLoginPw());

        if(loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //세션이 있으면 세션 반환, 없으면 세션 생성
        HttpSession session = request.getSession();

        //세션에 로그인 정보 저장
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null)
            session.invalidate();
        return "redirect:/";
    }
}
