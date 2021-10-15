package project.board.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 게시글 Jpa
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByPostTitleContaining(String title, Pageable pageable);
}
