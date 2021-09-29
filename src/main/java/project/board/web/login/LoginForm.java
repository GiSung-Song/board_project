package project.board.web.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String loginPw;
}
