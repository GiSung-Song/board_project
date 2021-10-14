package project.board.web.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class FindPwForm {

    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String userEmail; //사용자 이메일을 이용하여 아이디 찾기

    @NotEmpty(message = "아이디를 입력해주세요")
    private String userId; //사용자 아이디와 이메일로 비밀번호 찾기

}
