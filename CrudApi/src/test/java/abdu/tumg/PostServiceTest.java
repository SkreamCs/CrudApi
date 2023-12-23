package abdu.tumg;

import abdu.tumg.controller.LabelController;
import abdu.tumg.controller.PostController;
import abdu.tumg.model.Label;
import abdu.tumg.model.Post;
import abdu.tumg.model.PostStatus;
import abdu.tumg.model.Status;
import abdu.tumg.repository.LabelRepository;
import abdu.tumg.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class PostServiceTest {
    private PostController postController;
    @Mock
    PostRepository postRepository;
    @Before
    public void init() {
         postRepository = Mockito.mock(PostRepository.class);
        postController = new PostController(postRepository);
    }
    @Test
    public void createTest()  {

        Post post = postController.createObject("hard","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", Status.ACTIVE)), PostStatus.ACTIVE);

        verify(postRepository, times(1)).save(post);

    }
    @Test
    public void updateTest() throws SQLException {
        Post post = new Post("hard","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", Status.ACTIVE)), PostStatus.ACTIVE);
        post.setId(1);

        when(postRepository.getByID(1)).thenReturn(post);

        Post postUpdate = new Post("Misha","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", Status.ACTIVE)), PostStatus.UNDER_REVIEW);
        postUpdate.setId(1);

        postController.editObject(postUpdate);

        verify(postRepository, times(1)).update(postUpdate);

    }
    @Test
    public void getByIdTest() throws SQLException {
        Post post = new Post("easy","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", Status.ACTIVE)), PostStatus.ACTIVE);
        post.setId(1);

        when(postRepository.getByID(1)).thenReturn(post);

        Post postExpected = postController.getByIdObject(1);

        verify(postRepository, times(1)).getByID(1);
        assertEquals(postExpected.toString(), post.toString());
    }
    @Test
    public void deleteTest() throws SQLException {
        Post post = new Post("hard","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", Status.ACTIVE)), PostStatus.ACTIVE);
        post.setId(1);

        when(postRepository.getByID(1)).thenReturn(post);

        postController.deleteObject(1);

        verify(postRepository, times(1)).delete(1);
    }
    @Test
    public void getAllTest() throws SQLException {
        List<Post> posts = List.of( new Post("hard","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", Status.ACTIVE)), PostStatus.ACTIVE), new Post( "hard-easy","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", Status.ACTIVE)), PostStatus.ACTIVE));

        when(postRepository.getAll()).thenReturn(posts);

        List<Post> labelExpected  = postController.getAllPost();

        assertEquals(labelExpected, posts);
    }
}

