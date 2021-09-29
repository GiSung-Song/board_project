package project.board.service.post;

import project.board.web.dto.PostDto;

import java.util.List;

public interface PostService {
    void save(PostDto post);
    List<PostDto> findAll();
}
