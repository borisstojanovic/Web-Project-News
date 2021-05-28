package rs.raf.Web_Project.repositories.comment;

import rs.raf.Web_Project.entities.Comment;

import java.util.List;

public interface ICommentRepository {

    List<Comment> findAll(int postId);

    Comment add(Comment comment);

}
