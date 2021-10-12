package project.board.web.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class FindUserForm {

    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String userEmail; //사용자 이메일을 이용하여 아이디 찾기
}
