package abdu.tumg;

import abdu.tumg.controller.PostController;
import abdu.tumg.controller.WriterController;
import abdu.tumg.model.Label;
import abdu.tumg.model.Post;
import abdu.tumg.model.PostStatus;
import abdu.tumg.model.Writer;
import abdu.tumg.repository.WriterRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class WriterServiceTest {
    private WriterController writerController;
    @Mock
    WriterRepository writerRepository;
    @Before
    public void init() {
        writerRepository = Mockito.mock(WriterRepository.class);
        writerController = new WriterController(writerRepository);
    }
    @Test
    public void createTest() throws SQLException {
        List<Post> list = List.of(new Post("hard-easy","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE), new Post("hard","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE));
        Writer writer = writerController.createObject("name", "last", PostStatus.ACTIVE, list);

        verify(writerRepository, times(1)).save(writer);

    }
    @Test
    public void updateTest() throws SQLException {
        List<Post> list = List.of(new Post("hard-easy","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE), new Post("hard","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE));
        Writer writer = writerController.createObject("name", "last", PostStatus.ACTIVE, list);
        writer.setId(1);

        when(writerRepository.getByID(1)).thenReturn(writer);

        List<Post> listUpgrade = List.of(new Post("hard-easy","2019-12-1 19:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE), new Post("hard","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE));
        Writer writerUpgrade = writerController.createObject("names", "last", PostStatus.ACTIVE, listUpgrade);

        writerController.editObject(writerUpgrade);

        verify(writerRepository, times(1)).update(writerUpgrade);

    }
    @Test
    public void getByIdTest() throws SQLException {
        List<Post> list = List.of(new Post("hard-easy","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE), new Post("hard","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE));
        Writer writer = writerController.createObject("name", "last", PostStatus.ACTIVE, list);
        writer.setId(1);

        when(writerRepository.getByID(1)).thenReturn(writer);

        Writer writerExpected = writerController.getByObject(1);

        verify(writerRepository, times(1)).getByID(1);
        assertEquals(writerExpected.toString(), writer.toString());
    }
    @Test
    public void deleteTest() throws SQLException {
        List<Post> list = List.of(new Post("hard-easy","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE), new Post("hard","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE));
        Writer writer = writerController.createObject("name", "last", PostStatus.ACTIVE, list);
        writer.setId(1);

        when(writerRepository.getByID(1)).thenReturn(writer);

        writerController.deleteObject(1);

        verify(writerRepository, times(1)).delete(1);
    }
    @Test
    public void getAllTest() throws SQLException {
        List<Writer> posts = List.of(new Writer("name", "last", PostStatus.ACTIVE, List.of( new Post("hard","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE), new Post( "hard-easy","2019-12-1 20:00:00","2019-12-1 20:00:00", List.of(new Label("Jorg", PostStatus.ACTIVE)), PostStatus.ACTIVE))));

        when(writerRepository.getAll()).thenReturn(posts);

        List<Writer> labelExpected  = writerController.getAllWriter();

        assertEquals(labelExpected, posts);
    }
}
