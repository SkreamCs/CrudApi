package abdu.tumg.repository.repositoryImpl;

import abdu.tumg.model.Label;
import abdu.tumg.model.Post;
import abdu.tumg.model.PostStatus;
import abdu.tumg.model.Status;
import abdu.tumg.repository.LabelRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static abdu.tumg.utils.JdbcUtils.*;

public class JdbcLabelRepositoryImpl implements LabelRepository {


    public Label getByID(Integer integer)  {
        String SQL = "SELECT * FROM labels WHERE id = ?";
        try(PreparedStatement preparedStatement = getPreparedStatement(SQL)) {
            preparedStatement.setInt(1, integer);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                return mapResultSetToLabel(set);
            }
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
        return null;
    }
    public void update(Label label) {
        String sqlQuery = "UPDATE labels SET name = ?, status = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = getPreparedStatement(sqlQuery);) {
            preparedStatement.setString(1, label.getName());
            preparedStatement.setString(2, String.valueOf(Status.UNDER_REVIEW));
            preparedStatement.setInt(3, label.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getErrorCode();
        }finally {
            closeConnect();
        }
    }

    public void delete(Integer integer)  {
        Label label = getByID(integer);
        label.setStatus(Status.DELETED);
        String sqlQuery = "UPDATE labels SET status = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)) {
            preparedStatement.setString(1, label.getStatus().toString());
            preparedStatement.setInt(2, label.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
    }

    public void save(Label label)  {
        String sqlQuery = "INSERT INTO labels(name, status) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = getPreparedStatementWithKeys(sqlQuery)){
            preparedStatement.setString(1, label.getName());
            preparedStatement.setString(2, label.getStatus().toString());
            preparedStatement.executeUpdate();
            ResultSet setId = preparedStatement.getGeneratedKeys();
            int id = 0;
            if (setId.next()) {
                id = setId.getInt(1);
            }
            label.setId(id);
            closeConnect();
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }
     Label mapResultSetToLabel(ResultSet resultSet) {
        try {
                Label label = new Label(resultSet.getString("name"), Status.valueOf(resultSet.getString("status")));
                label.setId(resultSet.getInt("id"));
                return label;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return null;
    }

    public List<Label> getAll()  {
        List<Label> labels = new ArrayList<>();
        String sqlQuery = "SELECT * FROM labels";
        try(PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)){
            ResultSet set = preparedStatement.executeQuery(sqlQuery);
            while (set.next()) {
                Label label = mapResultSetToLabel(set);
                labels.add(label);
            }
        } catch (SQLException e){
            e.getErrorCode();
        } finally {
            closeConnect();
        }
        return labels;
    }
}

