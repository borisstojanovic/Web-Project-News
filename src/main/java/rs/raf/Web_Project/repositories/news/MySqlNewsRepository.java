package rs.raf.Web_Project.repositories.news;

import rs.raf.Web_Project.entities.News;
import rs.raf.Web_Project.entities.Tag;
import rs.raf.Web_Project.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements INewsRepository {
    @Override
    public News add(News news) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement("INSERT INTO news (Text, Title, Created_at, Views, categoryId, userId) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, news.getText());
            preparedStatement.setString(2, news.getTitle());
            preparedStatement.setTimestamp(3, new Timestamp(news.getCreatedAt().getTime()));
            preparedStatement.setInt(4, news.getViews());
            preparedStatement.setInt(5, news.getCategoryId());
            preparedStatement.setInt(6, news.getUserId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                news.setId(resultSet.getInt(1));
            }

            if(news.getTags() != null) {
                for (Tag tag : news.getTags()) {
                    preparedStatement = connection.prepareStatement("INSERT INTO news_tag(tagId, newsId) values (?, ?)");
                    preparedStatement.setInt(1, tag.getId());
                    preparedStatement.setInt(2, news.getId());
                    preparedStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public News update(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE news set Text = ?, Title = ?, Created_at = ?, Views = ?, categoryId = ?, userId = ? where newsId = ?");
            preparedStatement.setString(1, news.getText());
            preparedStatement.setString(2, news.getTitle());
            preparedStatement.setTimestamp(3, new Timestamp(news.getCreatedAt().getTime()));
            preparedStatement.setInt(4, news.getViews());
            preparedStatement.setInt(5, news.getCategoryId());
            preparedStatement.setInt(6, news.getUserId());
            preparedStatement.setInt(7, news.getId());
            preparedStatement.executeUpdate();

            if(news.getTags() != null) {
                for (Tag tag : news.getTags()) {
                    preparedStatement = connection.prepareStatement("INSERT INTO news_tag(tagId, newsId) values (?, ?)");
                    preparedStatement.setInt(1, tag.getId());
                    preparedStatement.setInt(2, news.getId());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public void remove(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM news WHERE newsId = ?");
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
    public News find(Integer id) {
        News news = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE newsId = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                news = new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> all() {
        List<News> news = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                news.add(new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> allForCategory(Integer categoryId) {
        List<News> news = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE categoryId = ?");
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                news.add(new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> allForTag(Integer tagId) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select * from news join news_tag nt on news.newsId = nt.newsId where tagId = ?;");
            preparedStatement.setInt(1, tagId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                news.add(new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at")));
            }
            /*
            //todo dodavanje liste tagova
            for (News news1 : news) {
                preparedStatement = connection.prepareStatement("select * from tag join news_tag nt on tag.tagId = nt.tagId where newsId = ?;");
                preparedStatement.setInt(1, news1.getId());
                resultSet = preparedStatement.executeQuery();
                List<Tag> tagList = new ArrayList<>();
                while(resultSet.next()){
                    tagList.add(new Tag(resultSet.getInt("tagId"), resultSet.getString("Keyword")));
                }
                news1.setTags(tagList);
            }

             */

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }
}
