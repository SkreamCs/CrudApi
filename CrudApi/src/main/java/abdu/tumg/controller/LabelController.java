package abdu.tumg.controller;

import abdu.tumg.model.Label;
import abdu.tumg.model.PostStatus;
import abdu.tumg.model.Status;
import abdu.tumg.model.Writer;
import abdu.tumg.repository.LabelRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LabelController  {
    private LabelRepository jdbcLabelRepository;

    public LabelController(LabelRepository jdbcLabelRepository) {
        this.jdbcLabelRepository = jdbcLabelRepository;
    }

    public Label createObject(String name, Status status) {
        Label label = new Label(name, status);
        jdbcLabelRepository.save(label);
        return label;
    }
    public List<Label> getAllLabel() {
            List<Label> labels = jdbcLabelRepository.getAll();
            return labels;
    }
    public Label editObject(Label labelUpdate) {
        jdbcLabelRepository.update(labelUpdate);
        return labelUpdate;
    }
    public Label getByIdLabelController(int id) {
           return jdbcLabelRepository.getByID(id);
    }
    public void deleteObject(int id) {
            jdbcLabelRepository.delete(id);

    }
}
