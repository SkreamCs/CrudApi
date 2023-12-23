package abdu.tumg.repository.repositoryImpl;

import abdu.tumg.model.Label;
import abdu.tumg.model.Post;
import abdu.tumg.model.PostStatus;
import abdu.tumg.model.Status;
import abdu.tumg.repository.LabelRepository;
import abdu.tumg.repository.PostRepository;
import abdu.tumg.repository.WriterRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import static abdu.tumg.utils.JdbcUtils.*;

public class JdbcPostRepositoryImpl implements PostRepository {

    @Override
    public Post getByID(Integer integer)  {
        String SQL = "SELECT * from posts where id = ?";
        try(PreparedStatement preparedStatement = getPreparedStatement(SQL);) {
            preparedStatement.setInt(1, integer);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                return mapResultSetToPost(set);
            }
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
        return null;
    }
     Post mapResultSetToPost(ResultSet resultSet) {
        try {
                List<Label> labels = getPostLabels(resultSet);
                Post post = new Post(resultSet.getString("content"), resultSet.getString("created"), resultSet.getString("updated"), labels, PostStatus.valueOf(resultSet.getString("PostStatus")));
                post.setId(resultSet.getInt("id"));
                return post;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return null;
    }

    @Override
    public void update(Post post) {
        String sqlQuery = "UPDATE posts SET content = ?, created = ?, updated = ?, PostStatus = ?  WHERE id = ?";
        try(PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(post.getCreated()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(post.getUpdated()));
            preparedStatement.setString(4, String.valueOf(PostStatus.UNDER_REVIEW));
            preparedStatement.setInt(5, post.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
    }

    @Override
    public void delete(Integer integer) {
        Post post = getByID(integer);
        post.setPostStatus(PostStatus.DELETED);
        String sqlQuery = "UPDATE posts SET PostStatus = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)) {
            preparedStatement.setString(1, String.valueOf(post.getPostStatus()));
            preparedStatement.setInt(2, post.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
    }

    @Override
    public void save(Post post)  {
        String sqlQuery = "INSERT INTO posts(content, created, updated, PostStatus) VALUES (?, ?, ?, ?)";
        try( PreparedStatement preparedStatement = getPreparedStatementWithKeys(sqlQuery)){
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(post.getCreated()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(post.getUpdated()));
            preparedStatement.setString(4, post.getPostStatus().toString());
            postLabelsSave(post, preparedStatement);
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
    }
    private void savePostLabels(int id, int twoId) {
        String sqlQuery = "INSERT INTO post_labels(post_id, label_id) VALUES(?, ?)";
        try(PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)){
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, twoId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
    }
    private int saveLabelWithKeys(Label label) {
        String SQL = "insert into labels(name, status) VALUES (?,?)";
        try(PreparedStatement preparedStatement = getPreparedStatementWithKeys(SQL)) {
            preparedStatement.setString(1, label.getName());
            preparedStatement.setString(2, label.getStatus().toString());
            preparedStatement.executeUpdate();
            ResultSet setId = preparedStatement.getGeneratedKeys();
            if (setId.next()) {
                return setId.getInt(1);
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return 0;
    }

    @Override
    public List<Post> getAll()  {
            String sqlQuery = "SELECT * FROM posts";
            List<Post> posts = new ArrayList<>();
            try(PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)) {
                ResultSet set = preparedStatement.executeQuery(sqlQuery);
                while (set.next()) {
                    Post post = mapResultSetToPost(set);
                    posts.add(post);
                }
            } catch (SQLException e) {
                e.getErrorCode();
            } finally {
                closeConnect();
            }
            return posts;
        }
        private List<Label> getPostLabels(ResultSet set) {
           List<Label> labels = new ArrayList<>();
            String SQL = "SELECT id, name, status FROM labels " +
                         "INNER JOIN post_labels " +
                         "ON post_labels.label_id = labels.id " +
                         "WHERE post_labels.post_id = ?";
            try(PreparedStatement preparedStatement = getPreparedStatement(SQL)) {
                preparedStatement.setInt(1, set.getInt("id"));
                ResultSet setLabels = preparedStatement.executeQuery();
                while (setLabels.next()) {
                    Label label = new Label(setLabels.getString("name"), Status.valueOf(setLabels.getString("status")));
                    label.setId(setLabels.getInt("id"));
                    labels.add(label);
                }
            } catch (SQLException e) {
                e.getErrorCode();
            } finally {
                closeConnect();
            }
            return labels;
        }
    public void saveWithKey(Post post, int id)  {
        String sqlQuery = "INSERT INTO posts(writer_id, content, created, updated, PostStatus) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = getPreparedStatementWithKeys(sqlQuery)){
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(post.getUpdated()));
            preparedStatement.setString(5, post.getPostStatus().toString());
            postLabelsSave(post, preparedStatement);
        } catch (SQLException e) {
            e.getErrorCode();
        } finally {
            closeConnect();
        }
    }

    private void postLabelsSave(Post post, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.executeUpdate();
        ResultSet set = preparedStatement.getGeneratedKeys();
        int postId = 0;
        if (set.next()) {
            postId = set.getInt(1);
        }
        for (Label label : post.getLabels()){
            int labelId = saveLabelWithKeys(label);
            savePostLabels(postId, labelId);
        }
        post.setId(postId);
    }
}
