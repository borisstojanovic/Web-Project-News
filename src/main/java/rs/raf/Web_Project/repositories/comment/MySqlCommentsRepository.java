package rs.raf.Web_Project.repositories.comment;

import rs.raf.Web_Project.entities.Comment;
import rs.raf.Web_Project.entities.News;
import rs.raf.Web_Project.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlCommentsRepository extends MySqlAbstractRepository implements ICommentRepository {
    @Override
    public List<Comment> findAll(int newsId) {
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM comment WHERE newsId = ? order by Created_at desc");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comments.add(new Comment(resultSet.getInt("commentId"), resultSet.getInt("newsId"), resultSet.getString("Author"),
                        resultSet.getString("Text"), resultSet.getTimestamp("Created_at")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comments;
    }

    @Override
    public List<Comment> allPaginated(int start, int size, int newsId) {
        List<Comment> comments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM comment where newsId = ? order by Created_at desc limit ?, ?");
            preparedStatement.setInt(1, newsId);
            preparedStatement.setInt(2, start * size);
            preparedStatement.setInt(3, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comments.add(new Comment(resultSet.getInt("commentId"), resultSet.getInt("newsId"), resultSet.getString("Author"),
                        resultSet.getString("Text"), resultSet.getTimestamp("Created_at")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comments;
    }

    @Override
    public Comment add(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO comment (Text, Author, Created_at, newsId) VALUES(?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getText());
            preparedStatement.setString(2, comment.getAuthor());
            preparedStatement.setTimestamp(3, new Timestamp(comment.getCreatedAt().getTime()));
            preparedStatement.setInt(4, comment.getNewsId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }

    @Override
    public int count(Integer id) {
        int count = 0;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select count(*) from comment where newsId = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return count;
    }
}
