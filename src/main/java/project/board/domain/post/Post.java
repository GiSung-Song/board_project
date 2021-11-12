package project.board.domain.post;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import project.board.domain.user.User;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 게시글 Entity
 */


@Entity
@NoArgsConstructor
@Getter
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_IDX")
    private long postIdx;

    @Column(nullable = false)
    private String postTitle; //게시글 제목

    @Column(nullable = false)
    private String postContent; //게시글 내용

    @ManyToOne(fetch = FetchType.LAZY) //한 유저가 여러 글을 쓸 수 있으므로, 다대일
    @JoinColumn(name = "USER_IDX")
    private User user;

    @Column(nullable = false)
    private String postWriter; //작성자

    @Column(name = "postDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date; //게시글 등록 날짜

    @Builder
    public Post(String postTitle, String postContent, User user, String postWriter) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postWriter = postWriter;
        this.date = LocalDate.now();
        this.user = user;
    }

    public void update(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.date = LocalDate.now();
    }

    public void setNull() {
        this.user = null;
        this.postWriter = "탈퇴한 사용자";
    }
}
