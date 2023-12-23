package abdu.tumg.repository.repositoryImpl;

import abdu.tumg.model.*;
import abdu.tumg.repository.PostRepository;
import abdu.tumg.repository.WriterRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static abdu.tumg.utils.JdbcUtils.*;

public class JdbcWriterRepositoryImpl implements WriterRepository {

    JdbcPostRepositoryImpl jdbcPostRepository;

    public JdbcWriterRepositoryImpl(JdbcPostRepositoryImpl jdbcPostRepository) {
        this.jdbcPostRepository = jdbcPostRepository;
    }
    @Override
    public Writer getByID(Integer integer) {
        String SQL = "SELECT * from writers where id = ?";
        try (PreparedStatement preparedStatement = getPreparedStatement(SQL);) {
            preparedStatement.setInt(1, integer);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                return mapResultSetToWriter(set);
            }
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
        return null;
    }

    private Writer mapResultSetToWriter(ResultSet resultSet) {
        try {
            Writer writer = new Writer(resultSet.getString("firstName"), resultSet.getString("lastName"), Status.valueOf(resultSet.getString("status")), getWriterPosts(resultSet));
            writer.setId(resultSet.getInt("id"));
            return writer;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return null;
    }

    @Override
    public void update(Writer writer) {
        String sqlQuery = "UPDATE writers SET firstName = ?, lastName = ?, status = ?  WHERE id = ?";
        try (PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)) {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.setString(3, String.valueOf(Status.UNDER_REVIEW));
            preparedStatement.setInt(4, writer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
    }

    @Override
    public void delete(Integer integer) {
        Writer writer = getByID(integer);
        writer.setStatus(Status.DELETED);
        String sqlQuery = "UPDATE writers SET status = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)) {
            preparedStatement.setString(1, String.valueOf(writer.getStatus()));
            preparedStatement.setInt(2, writer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
    }

    @Override
    public void save(Writer writer) {
        String sqlQuery = "INSERT INTO writers(firstName, lastName, status) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = getPreparedStatementWithKeys(sqlQuery)) {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.setString(3, writer.getStatus().toString());
            preparedStatement.executeUpdate();
            ResultSet set = preparedStatement.getGeneratedKeys();
            int id = 0;
            if (set.next()) {
                id = set.getInt(1);
            }
            for (Post post : writer.getPosts()) {
                  jdbcPostRepository.saveWithKey(post, id);
            }
            writer.setId(set.getInt(1));
        } catch(SQLException e) {
        e.getErrorCode();
    } finally {
            closeConnect();
        }

}


private List<Post> getWriterPosts(ResultSet set) {
    String SQL = "select posts.* from posts " +
            "inner join writers on writers.id = writer_id " +
            "where writers.id = ?";
    try (PreparedStatement preparedStatement = getPreparedStatement(SQL)) {
        preparedStatement.setInt(1, set.getInt("id"));
        ResultSet setPost = preparedStatement.executeQuery();
        List<Post> posts = new ArrayList<>();
        while (setPost.next()) {
            posts.add(jdbcPostRepository.mapResultSetToPost(setPost));
        }
        return posts;
    } catch (SQLException e) {
        e.getErrorCode();
    } finally {
        closeConnect();
    }
    return null;
 }
    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        String sqlQuery = "SELECT * FROM writers";
        try (PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)) {
            ResultSet set = preparedStatement.executeQuery(sqlQuery);
            while (set.next()) {
                Writer writer = mapResultSetToWriter(set);
                writers.add(writer);
            }
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
        return writers;
    }
}
