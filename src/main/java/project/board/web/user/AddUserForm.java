package project.board.web.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AddUserForm {

    @NotEmpty(message = "아이디를 입력해주세요.")
    @Size(min = 2, max = 10, message = "아이디는 2자 이상 10자 이하로 입력해주세요.")
    private String userId; //사용자 로그인 아이디

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 4, max = 10, message = "비밀번호는 4자 이상 10자 이하로 입력해주세요.")
    private String userPw; //사용자 로그인 비밀번호

    @NotEmpty(message = "이름을 입력해주세요.")
    private String userName; //사용자 이름

    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String userEmail; //사용자 이메일
}
