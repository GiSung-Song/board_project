package project.board.web.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.board.service.post.PostService;
import project.board.web.SessionConst;
import project.board.web.dto.PostDto;
import project.board.web.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public String postsList(Model model) {
        log.info("게시글 목록 이동");
        List<PostDto> posts = postService.findAll();
        model.addAttribute("posts", posts);

        return "post/posts";
    }

    @GetMapping("/addPost")
    public String add(@ModelAttribute("post") PostDto postDto) {
        log.info("게시글 등록 이동");
        return "post/addPost";
    }

    @PostMapping("/addPost")
    public String addPost(@Validated @ModelAttribute("post") PostSaveForm post, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "post/addPost";
        }

        HttpSession session = request.getSession(false);

        UserDto loginUser = (UserDto) session.getAttribute(SessionConst.LOGIN_MEMBER);
        log.info("loginUserName={}", loginUser.getUserName());

        PostDto postDto = PostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(loginUser.getUserName())
                .userId(loginUser.getUserId())
                .build();

        postService.save(postDto);

        return "redirect:/post";
    }



}
