package project.board.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.post.Post;
import project.board.domain.post.PostRepository;
import project.board.domain.user.User;
import project.board.service.user.UserService;
import project.board.web.dto.PostDto;

import java.util.ArrayList;
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
        Optional<User> optionalUser = userService.findByUserId(postDto.getUserId());
        User user = optionalUser.get();

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
            PostDto responseDto = PostDto.builder()
                    .id(post.getPostIdx())
                    .title(post.getPostTitle())
                    .content(post.getPostContent())
                    .writer(post.getPostWriter())
                    .date(post.getDate())
                    .build();

            postDtoList.add(responseDto);
        }

        return postDtoList;
    }

    @Override
    public PostDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        PostDto postDto = PostDto.builder()
                .title(post.getPostTitle())
                .writer(post.getPostWriter())
                .id(post.getPostIdx())
                .content(post.getPostContent())
                .userId(post.getUser().getUserId())
                .date(post.getDate())
                .build();

        return postDto;
    }

    @Override
    public PostDto update(Long index, String title, String content) {
        Post post = postRepository.findById(index).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        post.update(title, content);

        return PostDto.builder()
                .userId(post.getUser().getUserId())
                .writer(post.getPostWriter())
                .title(post.getPostTitle())
                .content(post.getPostContent())
                .id(post.getPostIdx())
                .date(post.getDate())
                .build();

    }

    @Override
    public void delete(Long post_index) {
        postRepository.deleteById(post_index);
    }


}
