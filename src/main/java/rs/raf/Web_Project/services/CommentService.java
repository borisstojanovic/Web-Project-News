package rs.raf.Web_Project.services;

import rs.raf.Web_Project.entities.Comment;
import rs.raf.Web_Project.repositories.comment.ICommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    public CommentService() {

    }

    @Inject
    private ICommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return this.commentRepository.add(comment);
    }

    public List<Comment> allCommentsForPost(Integer id) {
        return this.commentRepository.findAll(id);
    }

    public int count(Integer id) {
        return this.commentRepository.count(id);
    }

    public List<Comment> allPaginated(int start, int size, Integer id) {
        return this.commentRepository.allPaginated(start, size, id);
    }
}
