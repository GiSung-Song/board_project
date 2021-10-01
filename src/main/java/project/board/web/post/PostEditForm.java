package project.board.web.post;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PostEditForm {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
