package abdu.tumg.view;

import abdu.tumg.controller.LabelController;
import abdu.tumg.controller.PostController;
import abdu.tumg.controller.WriterController;
import abdu.tumg.repository.repositoryImpl.JdbcLabelRepositoryImpl;
import abdu.tumg.repository.repositoryImpl.JdbcPostRepositoryImpl;
import abdu.tumg.repository.repositoryImpl.JdbcWriterRepositoryImpl;

import java.util.Scanner;

import static abdu.tumg.repository.GenericRepository.JDBC_DRIVER;

public class GeneralView {
    public void startApp() {
        Scanner scanner = new Scanner(System.in);
        LabelController labelController = new LabelController(new JdbcLabelRepositoryImpl());
        LabelView labelView = new LabelView(labelController);
        PostController postController = new PostController(new JdbcPostRepositoryImpl());
        PostView postView = new PostView(postController);
        WriterController writerController = new WriterController(new JdbcWriterRepositoryImpl());
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
