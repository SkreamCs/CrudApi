package abdu.tumg.controller;

import abdu.tumg.model.*;
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
    public Writer createObject(String firstName, String lastName, Status status, List<Post> writers) {
        Writer writer = new Writer(firstName, lastName, status, writers);
            writerRepository.save(writer);
        return writer;
    }
    public Writer getByObject(int id) {
            return writerRepository.getByID(id);
    }
    public List<Writer> getAllWriter() {
        return writerRepository.getAll();
    }
    public Writer editObject(Writer writerUpgrade) {
             writerRepository.update(writerUpgrade);
        return writerUpgrade;
    }
    public void deleteObject(int id) {
            writerRepository.delete(id);
    }
}
