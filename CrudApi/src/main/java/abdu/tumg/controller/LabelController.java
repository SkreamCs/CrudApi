package abdu.tumg.controller;

import abdu.tumg.model.Label;
import abdu.tumg.model.PostStatus;
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

    public Label createObject(String name, PostStatus postStatus) {
        Label label = new Label(name, postStatus);
        try {
                jdbcLabelRepository.save(label);
            } catch (SQLException e) {
                e.getErrorCode();
            }
        return label;
    }
    public List<Label> getAllLabel() {
        try {
            List<Label> labels = jdbcLabelRepository.getAll();
            return labels;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return null;
    }
    public Label editObject(Label labelUpdate) {
        try {
            jdbcLabelRepository.update(labelUpdate);
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return labelUpdate;
    }
    public Label getByIdLabelController(int id) {
        try {
           return jdbcLabelRepository.getByID(id);
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return null;
    }
    public void deleteObject(int id) {
        try {
            jdbcLabelRepository.delete(id);
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }
}
