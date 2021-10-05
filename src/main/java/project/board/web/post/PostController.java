package project.board.web.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/posts")
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

    @GetMapping("/{post_index}")
    public String postsList(@PathVariable Long post_index, Model model, HttpServletRequest request) {
        PostDto postDto = postService.findById(post_index);

        model.addAttribute("post", postDto);

        HttpSession session = request.getSession(false);
        UserDto loginUser = (UserDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        //로그인 한 사용자만이 수정할 수 있도록 model 에 값 하나 추가
        if(postDto.getUserId().equals(loginUser.getUserId())) {
            model.addAttribute("loginUser", "login");
        }

        return "post/post";
    }

    @GetMapping("/addPost")
    public String add(@ModelAttribute("post") PostDto postDto) {
        log.info("게시글 등록 이동");
        return "post/addPost";
    }

    @PostMapping("/addPost")
    public String addPost(@Validated PostSaveForm post, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {

        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "post/addPost";
        }

        HttpSession session = request.getSession(false);

        UserDto loginUser = (UserDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        PostDto postDto = PostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(loginUser.getUserName())
                .userId(loginUser.getUserId())
                .build();

        log.info("writer={}",postDto.getWriter());

        model.addAttribute("posts", postDto);

        Long post_index = postService.save(postDto);
        redirectAttributes.addAttribute("post_index", post_index);
        redirectAttributes.addAttribute("status", true);

        return "redirect:/posts/{post_index}";
    }

    @GetMapping("/{post_index}/edit")
    public String editForm(@PathVariable Long post_index, Model model, HttpServletRequest request) {
        PostDto postDto = postService.findById(post_index);
        model.addAttribute("post", postDto);

        HttpSession session = request.getSession(false);
        UserDto userDto = (UserDto) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("user", userDto);

        return "post/editPost";
    }

    @PostMapping("/{post_index}/edit")
    public String edit(@PathVariable Long post_index, HttpServletRequest request,
                       @Validated @ModelAttribute("post") PostEditForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "post/editPost";
        }

        postService.update(post_index, form.getTitle(), form.getContent());

        return "redirect:/posts/{post_index}";
    }



}
