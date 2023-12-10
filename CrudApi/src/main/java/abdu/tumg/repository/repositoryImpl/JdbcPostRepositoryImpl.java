package abdu.tumg.repository.repositoryImpl;

import abdu.tumg.model.Label;
import abdu.tumg.model.Post;
import abdu.tumg.model.PostStatus;
import abdu.tumg.repository.PostRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static abdu.tumg.App.*;

public class JdbcPostRepositoryImpl implements PostRepository {
    private final Gson GSON = new Gson();
    @Override
    public Post getByID(Integer integer) throws SQLException {
        return getAll().stream()
                .filter(n -> n.getId() == integer)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Post post) throws SQLException {
        Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
        String sqlQuery = "UPDATE post_table SET content = ?, created = ?, updated = ?, post_label = ?, PostStatus = ?  WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, post.getContent());
        preparedStatement.setTimestamp(2, Timestamp.valueOf(post.getCreated()));
        preparedStatement.setTimestamp(3, Timestamp.valueOf(post.getUpdated()));
        preparedStatement.setString(4, GSON.toJson(post.getPostLabel()));
        preparedStatement.setString(5, String.valueOf(PostStatus.UNDER_REVIEW));
        preparedStatement.setInt(6, post.getId());
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
    }

    @Override
    public void delete(Integer integer) throws SQLException {
        Post post = getByID(integer);
        post.setPostStatus(PostStatus.DELETED);
        Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
        String sqlQuery = "UPDATE post_table SET PostStatus = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, String.valueOf(post.getPostStatus()));
        preparedStatement.setInt(2, post.getId());
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
    }

    @Override
    public void save(Post post) throws SQLException {
        Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
        String sqlQuery = "SELECT * FROM post_table";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery(sqlQuery);
        int max = 0;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            if (id > max) max = id;
        }
        sqlQuery = "INSERT INTO post_table(id, content, created, updated, post_label, PostStatus) VALUES (?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, max + 1);
        preparedStatement.setString(2, post.getContent());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(post.getUpdated()));
        preparedStatement.setString(5, GSON.toJson(post.getPostLabel()));
        preparedStatement.setString(6, post.getPostStatus().toString());
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
    }

    @Override
    public List<Post> getAll() throws SQLException {
            Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM post_table";
            ResultSet set = statement.executeQuery(sqlQuery);
            List<Post> posts = new ArrayList<>();
            while (set.next()) {
                int id = set.getInt("id");
                String content = set.getString("content");
                Timestamp timestamp = set.getTimestamp("created");
                Timestamp timestampUpdated = set.getTimestamp("updated");
                List<Label> labels = GSON.fromJson(set.getString("post_label"), new TypeToken<List<Label>>(){}.getType());
                String postString = set.getString("PostStatus");
                PostStatus postStatus = PostStatus.valueOf(postString);

                Post post = new Post(content, timestamp.toString(), timestampUpdated.toString(), labels, postStatus);
                post.setId(id);
                posts.add(post);
            }
            return posts;
        }
}
