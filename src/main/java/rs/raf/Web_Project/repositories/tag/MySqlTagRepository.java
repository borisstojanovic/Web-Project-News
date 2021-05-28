package rs.raf.Web_Project.repositories.tag;

import rs.raf.Web_Project.entities.News;
import rs.raf.Web_Project.entities.Tag;
import rs.raf.Web_Project.entities.User;
import rs.raf.Web_Project.entities.enums.Type;
import rs.raf.Web_Project.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlTagRepository extends MySqlAbstractRepository implements ITagRepository {
    @Override
    public Tag add(Tag tag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement("INSERT INTO tag (Keyword) VALUES(?)", generatedColumns);
            preparedStatement.setString(1, tag.getKeyword());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                tag.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return tag;
    }

    @Override
    public Tag update(Tag tag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE tag set Keyword = ? where tagId = ?");
            preparedStatement.setString(1, tag.getKeyword());
            preparedStatement.setInt(2, tag.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return tag;
    }

    @Override
    public void remove(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM tag WHERE tagId = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public List<Tag> allForNews(Integer newsId) {
        List<Tag> tags = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select * from tag join news_tag nt on tag.tagId = nt.tagId where newsId = ?;");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tags.add(new Tag(resultSet.getInt("tagId"), resultSet.getString("Keyword")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return tags;
    }

    @Override
    public List<Tag> all() {
        List<Tag> tags = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tag");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int tagId = resultSet.getInt("tagId");
                String keyword = resultSet.getString("Keyword");

                tags.add(new Tag(tagId, keyword));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return tags;
    }
}
