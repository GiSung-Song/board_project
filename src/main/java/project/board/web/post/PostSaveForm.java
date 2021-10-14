package project.board.web.post;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostSaveForm {

    @NotEmpty(message = "게시글 제목을 입력해주세요.")
    @Size(min = 1, max = 20, message = "1 ~ 20글자로 입력해주세요.")
    private String title; //게시글 제목

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content; //게시글 내용

}
