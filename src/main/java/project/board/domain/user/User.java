package project.board.domain.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * User Entity
 */

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_IDX")
    private Long userIdx;

    @Column(nullable = false, unique = true)
    private String userId; //사용자 로그인 아이디

    @Column(nullable = false)
    private String userPw; //사용자 로그인 비밀번호

    @Column(nullable = false)
    private String userName; //사용자 이름

    @Column(nullable = false, unique = true)
    private String userEmail; //사용자 이메일

    @Column(name = "regDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Builder
    public User(String userId, String userPw, String userName, String userEmail) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userEmail = userEmail;
        this.date = LocalDate.now();
    }

    public void setNull() {
        this.userId = "";
        this.userEmail = "";
        this.userName = "";
        this.userPw = "";
    }
}
