package abdu.tumg.view;

import abdu.tumg.controller.LabelController;
import abdu.tumg.controller.PostController;
import abdu.tumg.controller.WriterController;
import abdu.tumg.repository.repositoryImpl.JdbcLabelRepositoryImpl;
import abdu.tumg.repository.repositoryImpl.JdbcPostRepositoryImpl;
import abdu.tumg.repository.repositoryImpl.JdbcWriterRepositoryImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class GeneralView {
    String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public void startApp() {
        Scanner scanner = new Scanner(System.in);
        JdbcLabelRepositoryImpl labelRepository = new JdbcLabelRepositoryImpl();
        JdbcPostRepositoryImpl postRepository = new JdbcPostRepositoryImpl();
        JdbcWriterRepositoryImpl writerRepository = new JdbcWriterRepositoryImpl(postRepository);
        LabelController labelController = new LabelController(labelRepository);
        LabelView labelView = new LabelView(labelController);
        PostController postController = new PostController(postRepository);
        PostView postView = new PostView(postController);
        WriterController writerController = new WriterController(writerRepository);
        WriterView writerView = new WriterView(writerController);
        System.out.println("Запуск CRUD приложения...");
        while (true) {
            System.out.println();
            System.out.println("Выберите нужный вам пункт");
            System.out.println(
                    "1.Label\n"
                            + "2.Post\n"
                            + "3.Writer\n"
                            + "4.Выйти из приложения");
            switch (scanner.nextInt()) {
                case 1: {
                    labelView.labelStart();
                    break;
                }
                case 2: {
                    postView.postStart();
                    break;
                }
                case 3: {
                    writerView.postStart();
                    break;
                }
                case 4: {
                    return;
                }
            }
        }
    }
}
