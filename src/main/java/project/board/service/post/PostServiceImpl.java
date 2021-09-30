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
    public void save(PostDto postDto) {
        Optional<User> optionalUser = userService.findByUserId(postDto.getUserId());
        User user = optionalUser.get();

        log.info("postDto={},{},{}", postDto.getContent(), postDto.getTitle(), postDto.getWriter());
        log.info("userName={}", user.getUserName());

        postRepository.save(Post.builder()
        .postContent(postDto.getContent())
        .postTitle(postDto.getTitle())
        .postWriter(user.getUserName())
        .user(user)
        .build());
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
                    .build();

            postDtoList.add(responseDto);
        }

        return postDtoList;
    }


}
