package project.board.service.post;

import project.board.web.dto.PostDto;

import java.util.List;

public interface PostService {
    Long save(PostDto post);
    List<PostDto> findAll();
    PostDto findById(Long id);
    PostDto update(Long index, String title, String content);
}
