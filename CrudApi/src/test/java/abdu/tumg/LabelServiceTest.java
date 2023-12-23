package abdu.tumg;

import abdu.tumg.controller.LabelController;
import abdu.tumg.model.Label;
import abdu.tumg.model.PostStatus;
import abdu.tumg.model.Status;
import abdu.tumg.repository.LabelRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LabelServiceTest {
    private LabelController labelController;
    @Mock
    LabelRepository labelRepository;
    @Before
    public void init() {
        labelRepository = Mockito.mock(LabelRepository.class);
        labelController = new LabelController(labelRepository);
    }
    @Test
    public void createTest()  {

        Label label = labelController.createObject("Alex", Status.ACTIVE);

       verify(labelRepository, times(1)).save(label);

    }
    @Test
    public void updateTest() {
        Label label = new Label("Mikhail", Status.ACTIVE);
        label.setId(1);

        when(labelRepository.getByID(1)).thenReturn(label);

        Label labelUpdate = new Label("Misha", Status.UNDER_REVIEW);
        labelUpdate.setId(1);

        labelController.editObject(labelUpdate);

        verify(labelRepository, times(1)).update(labelUpdate);

    }
    @Test
    public void getByIdTest()  {
        Label label = new Label("Kira", Status.ACTIVE);
        label.setId(1);

        when(labelRepository.getByID(1)).thenReturn(label);

        Label labelExpected = labelController.getByIdLabelController(1);

        verify(labelRepository, times(1)).getByID(1);
        assertEquals(labelExpected.toString(), label.toString());
    }
    @Test
    public void deleteTest()  {
        Label label = new Label("Kira", Status.ACTIVE);
        label.setId(1);

       when(labelRepository.getByID(1)).thenReturn(label);

        labelController.deleteObject(1);

        verify(labelRepository, times(1)).delete(1);
    }
    @Test
    public void getAllTest()  {
        List<Label> labels = List.of(new Label("Kira",Status.DELETED), new Label("Mikhail", Status.UNDER_REVIEW));

        when(labelRepository.getAll()).thenReturn(labels);

        List<Label> labelExpected  = labelController.getAllLabel();

        assertEquals(labelExpected, labels);
    }
 }
