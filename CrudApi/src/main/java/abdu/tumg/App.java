package abdu.tumg;

import abdu.tumg.controller.LabelController;
import abdu.tumg.controller.PostController;
import abdu.tumg.controller.WriterController;
import abdu.tumg.model.Label;
import abdu.tumg.model.Post;
import abdu.tumg.model.PostStatus;
import abdu.tumg.repository.repositoryImpl.JdbcLabelRepositoryImpl;
import abdu.tumg.repository.repositoryImpl.JdbcPostRepositoryImpl;
import abdu.tumg.repository.repositoryImpl.JdbcWriterRepositoryImpl;
import abdu.tumg.view.GeneralView;
import abdu.tumg.view.LabelView;
import abdu.tumg.view.PostView;
import abdu.tumg.view.WriterView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static abdu.tumg.repository.GenericRepository.JDBC_DRIVER;

public class App {
    public static void main( String[] args ) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
        GeneralView generalView = new GeneralView();
        generalView.startApp();
    }

}

