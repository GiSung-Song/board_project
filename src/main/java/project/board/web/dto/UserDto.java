package project.board.web.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId; //사용자 로그인 아이디
    private String userPw; //사용자 로그인 비밀번호
    private String userName; //사용자 이름
    private String userEmail; //사용자 이메일
}
