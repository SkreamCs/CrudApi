package abdu.tumg.repository.repositoryImpl;

import abdu.tumg.model.Label;
import abdu.tumg.model.PostStatus;
import abdu.tumg.repository.LabelRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static abdu.tumg.App.*;

public class JdbcLabelRepositoryImpl implements LabelRepository {


    public Label getByID(Integer integer) throws SQLException {
        return getAll().stream()
                .filter(n -> n.getId() == integer)
                .findFirst()
                .orElse(null);
    }
    public void update(Label label) throws SQLException {
        Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
        String sqlQuery = "UPDATE label_table SET name = ?, PostStatus = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, label.getName());
        preparedStatement.setString(2, String.valueOf(PostStatus.UNDER_REVIEW));
        preparedStatement.setInt(3, label.getId());
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
    }

    public void delete(Integer integer) throws SQLException {
        Label label = getByID(integer);
        label.setPostStatus(PostStatus.DELETED);
        Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
        String sqlQuery = "UPDATE label_table SET PostStatus = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, String.valueOf(label.getPostStatus()));
        preparedStatement.setInt(2, label.getId());
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
    }

    public void save(Label label) throws SQLException {
        Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
        String sqlQuery = "SELECT * FROM label_table";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery(sqlQuery);
        int max = 0;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            if (id > max) max = id;
        }
        sqlQuery = "INSERT INTO label_table(id, name, PostStatus) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, max + 1);
        preparedStatement.setString(2, label.getName());
        preparedStatement.setString(3, label.getPostStatus().toString());
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
    }

    public List<Label> getAll() throws SQLException {
        Connection connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT * FROM label_table";
        ResultSet set = statement.executeQuery(sqlQuery);
        List<Label> labels = new ArrayList<>();
        while (set.next()) {
            int id = set.getInt("id");
            String name = set.getString("name");
            String postString = set.getString("PostStatus");
            PostStatus postStatus = PostStatus.valueOf(postString);
            Label label = new Label(name, postStatus);
            label.setId(id);
            labels.add(label);
        }
        return labels;
    }
}

