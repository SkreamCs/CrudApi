package abdu.tumg.controller;

import abdu.tumg.model.Label;
import abdu.tumg.model.Post;
import abdu.tumg.model.PostStatus;
import abdu.tumg.model.Writer;
import abdu.tumg.repository.WriterRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterController {
    private WriterRepository writerRepository;

    public WriterController(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }
    public Writer createObject(String firstName, String lastName, PostStatus postStatus, List<Post> writers) {
        Writer writer = new Writer(firstName, lastName, postStatus, writers);
        try {
            writerRepository.save(writer);
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return writer;
    }
    public Writer getByObject(int id) {
        try {
            return writerRepository.getByID(id);
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return null;
    }
    public List<Writer> getAllWriter() {
        try {
            return writerRepository.getAll();
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return null;
    }
    public Writer editObject(Writer writerUpgrade) {
        try {
             writerRepository.update(writerUpgrade);
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return writerUpgrade;
    }
    public void deleteObject(int id) {
        try {
            writerRepository.delete(id);
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }
}
