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
            news = null;
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
            this.closeStatement(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM news_tag where newsId = ?");
            preparedStatement.setInt(1, news.getId());
            preparedStatement.executeUpdate();
            this.closeStatement(preparedStatement);
            if(news.getTags() != null) {
                for (Tag tag : news.getTags()) {
                    preparedStatement = connection.prepareStatement("INSERT IGNORE INTO news_tag(tagId, newsId) values (?, ?)");
                    preparedStatement.setInt(1, tag.getId());
                    preparedStatement.setInt(2, news.getId());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            news = null;
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
                news.setViews(resultSet.getInt("Views"));
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
                News toAdd = new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at"));
                toAdd.setViews(resultSet.getInt("Views"));
                news.add(toAdd);
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
                News toAdd = new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at"));
                toAdd.setViews(resultSet.getInt("Views"));
                news.add(toAdd);
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
    public List<News> allPaginatedForCategory(Integer id, int start, int size) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news where categoryId = ? order by Created_at desc limit ?, ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, start*size);
            preparedStatement.setInt(3, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                News toAdd = new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at"));
                toAdd.setViews(resultSet.getInt("Views"));
                news.add(toAdd);
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
                News toAdd = new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at"));
                toAdd.setViews(resultSet.getInt("Views"));
                news.add(toAdd);
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

    @Override
    public List<News> allPaginated(int start, int size) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news order by Created_at desc limit ?, ?");
            preparedStatement.setInt(1, start*size);
            preparedStatement.setInt(2, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                News toAdd = new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at"));
                toAdd.setViews(resultSet.getInt("Views"));
                news.add(toAdd);
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
    public List<News> allPaginatedForTag(int id, int start, int size) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news join news_tag nt on news.newsId = nt.newsId where nt.tagId = ? order by Created_at desc limit ?, ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, start*size);
            preparedStatement.setInt(3, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                News toAdd = new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at"));
                toAdd.setViews(resultSet.getInt("Views"));
                news.add(toAdd);
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
    public int count() {
        int count = 0;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select count(*) from news");
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

    @Override
    public int countForCategory(int id) {
        int count = 0;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select count(*) from news where categoryId = ?");
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

    @Override
    public int countForTag(int id) {
        int count = 0;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select count(*) from news join news_tag nt on news.newsId = nt.newsId where nt.tagId = ?");
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

    @Override
    public List<News> mostViewed(int start, int size) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news where Created_at between DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW() order by Views desc, Created_at desc limit ?, ?");
            if(size > 10){
                size = 10;
            }
            preparedStatement.setInt(1, start*size);
            preparedStatement.setInt(2, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                News toAdd = new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at"));
                toAdd.setViews(resultSet.getInt("Views"));
                news.add(toAdd);
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
    public List<News> allNewest(int start, int size) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news order by Created_at desc limit ?, ?");
            if(size > 10){
                size = 10;
            }
            preparedStatement.setInt(1, start*size);
            preparedStatement.setInt(2, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                News toAdd = new News(resultSet.getInt("newsId"), resultSet.getInt("categoryId"), resultSet.getInt("userId"), resultSet.getString("Text"),
                        resultSet.getString("Title"), resultSet.getTimestamp("Created_at"));
                toAdd.setViews(resultSet.getInt("Views"));
                news.add(toAdd);
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
    public boolean incrementViews(int id) {
        boolean incremented = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE news set Views = Views + 1 where newsId = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            incremented = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return incremented;
    }

}
