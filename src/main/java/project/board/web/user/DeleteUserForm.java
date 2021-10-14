package project.board.web.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class DeleteUserForm {

    @NotEmpty(message = "이메일을 입력하세요.")
    @Email
    private String userEmail;

    @NotEmpty(message = "아이디를 입력하세요.")
    private String userId;

    @NotEmpty(message = "비밀번호를 입력하세요")
    private String userPw;
}
