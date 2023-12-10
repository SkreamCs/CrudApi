package abdu.tumg.repository.repositoryImpl;

import abdu.tumg.model.Label;
import abdu.tumg.model.Post;
import abdu.tumg.model.PostStatus;
import abdu.tumg.model.Writer;
import abdu.tumg.repository.WriterRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Watchable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static abdu.tumg.App.*;

public class JdbcWriterRepositoryImpl implements WriterRepository {
    private final Gson GSON = new Gson();
    @Override
    public Writer getByID(Integer integer) throws SQLException {
        return getAll().stream()
                .filter(n -> n.getId() == integer)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Writer writer) throws SQLException {
        Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
        String sqlQuery = "UPDATE writer_table SET firstName = ?, lastName = ?, writer_post = ?, PostStatus = ?  WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, writer.getFirstName());
        preparedStatement.setString(2, writer.getLastName());
        preparedStatement.setString(3, GSON.toJson(writer.getPostWriter()));
        preparedStatement.setString(4, String.valueOf(PostStatus.UNDER_REVIEW));
        preparedStatement.setInt(5, writer.getId());
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
    }

    @Override
    public void delete(Integer integer) throws SQLException {
        Writer writer = getByID(integer);
        writer.setPostStatus(PostStatus.DELETED);
        Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
        String sqlQuery = "UPDATE writer_table SET PostStatus = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, String.valueOf(writer.getPostStatus()));
        preparedStatement.setInt(2, writer.getId());
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
    }

    @Override
    public void save(Writer writer) throws SQLException {
        Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
        String sqlQuery = "SELECT * FROM writer_table";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery(sqlQuery);
        int max = 0;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            if (id > max) max = id;
        }
        sqlQuery = "INSERT INTO writer_table(id, firstName, lastName, writer_post, PostStatus) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, max + 1);
        preparedStatement.setString(2, writer.getFirstName());
        preparedStatement.setString(3, writer.getLastName());
        preparedStatement.setString(4, GSON.toJson(writer.getPostWriter()));
        preparedStatement.setString(5, writer.getPostStatus().toString());
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
    }

    @Override
    public List<Writer> getAll() throws SQLException {
        Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT * FROM writer_table";
        ResultSet set = statement.executeQuery(sqlQuery);
        List<Writer> writers = new ArrayList<>();
        while (set.next()) {
            int id = set.getInt("id");
            String firstName = set.getString("firstName");
            String lastName = set.getString("lastName");
            List<Post> posts = GSON.fromJson(set.getString("writer_post"), new TypeToken<List<Post>>(){}.getType());
            String postString = set.getString("PostStatus");
            PostStatus postStatus = PostStatus.valueOf(postString);

            Writer writer = new Writer(firstName, lastName, postStatus, posts);
            writer.setId(id);
            writers.add(writer);
        }
        return writers;
    }
}
