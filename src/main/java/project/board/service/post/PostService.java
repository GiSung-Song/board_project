package project.board.service.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.board.web.dto.PostDto;
import project.board.web.dto.UserDto;

import java.util.List;

public interface PostService {
    Long save(PostDto post);
    List<PostDto> findAll();
    PostDto findById(Long id);
    PostDto update(Long index, String title, String content);
    void delete(Long post_index);
    Page<PostDto> getList(Pageable pageable);
    Page<PostDto> getSearchList(String title, Pageable pageable);
}
