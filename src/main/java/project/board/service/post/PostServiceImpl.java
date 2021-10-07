package project.board.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.post.Post;
import project.board.domain.post.PostRepository;
import project.board.domain.user.User;
import project.board.service.user.UserService;
import project.board.web.dto.PostDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserService userService;


    @Override
    public Long save(PostDto postDto) {
        User user = userService.findByUserId(postDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        return postRepository.save(Post.builder()
        .postContent(postDto.getContent())
        .postTitle(postDto.getTitle())
        .postWriter(user.getUserName())
        .user(user)
        .build()).getPostIdx();
    }

    @Override
    public List<PostDto> findAll() {
        List<Post> postList = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();

        for(Post post : postList) {
            PostDto responseDto = toPostDto(post);
            postDtoList.add(responseDto);
        }
        return postDtoList;
    }

    @Override
    public PostDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        PostDto postDto = toPostDto(post);

        return postDto;
    }

    @Override
    public PostDto update(Long index, String title, String content) {
        Post post = postRepository.findById(index).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        post.update(title, content);

        return toPostDto(post);

    }

    @Override
    public void delete(Long post_index) {
        postRepository.deleteById(post_index);
    }

    @Override
    public Page<PostDto> getList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page,10);

        Page<Post> postPage = postRepository.findAll(pageable);
        Page<PostDto> postDtoPage = postPage.map(m ->
                new PostDto(m.getPostIdx(), m.getPostTitle(), m.getPostContent(), m.getPostWriter(), m.getUser().getUserId(), m.getDate()));

        return postDtoPage;
    }

    @Override
    public Page<PostDto> getSearchList(String title, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page,10);

        Page<Post> postPage = postRepository.findByPostTitleContaining(title, pageable);
        Page<PostDto> postDtoPage = postPage.map(m ->
                new PostDto(m.getPostIdx(), m.getPostTitle(), m.getPostContent(), m.getPostWriter(), m.getUser().getUserId(), m.getDate()));

        log.info("postDtoPage.getNumber={}", postDtoPage.getNumber());

        return postDtoPage;
    }

    private PostDto toPostDto(Post post) {
        return PostDto.builder()
                .userId(post.getUser().getUserId())
                .writer(post.getPostWriter())
                .title(post.getPostTitle())
                .content(post.getPostContent())
                .id(post.getPostIdx())
                .date(post.getDate())
                .build();
    }

}
