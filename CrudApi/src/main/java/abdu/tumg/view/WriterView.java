package abdu.tumg.view;

import abdu.tumg.controller.WriterController;
import abdu.tumg.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final Scanner SCANNER = new Scanner(System.in);
    private WriterController writerController;

    public WriterView(WriterController writerController) {
        this.writerController = writerController;
    }
    public void postStart() {
        while (true) {
            System.out.println();
            System.out.println("Вы находитесь в среде разроботки сущности Writer\n");
            System.out.println("Выберите нужный пункт\n");
            System.out.println(
                    "1.Создать сущность Writer\n"
                            + "2.Изменить сущность Writer\n"
                            + "3.Удалить сущность Writer\n"
                            + "4.<- Вернуться назад");
            switch (SCANNER.nextInt()) {
                case 1: {
                   createWriterView();
                    break;
                }
                case 2: {
                     updateView();
                    break;
                }
                case 3: {
                    writerController.getAllWriter().stream().forEach(System.out::println);
                    System.out.println("Введите id сущности которую хотите удалить");
                    writerController.deleteObject(SCANNER.nextInt());
                    break;
                }
                case 4: {
                    return;
                }
            }
        }
    }
    public void createWriterView() {
        System.out.println("Введите имя для firstName:");
        String firstName = SCANNER.next();
        System.out.println("Введите lastName");
        String lastName = SCANNER.next();
        System.out.println("Введите число элементов для создания коллекции типа Post");
        List<Post> list = new ArrayList<>();
        int size = SCANNER.nextInt();
        for (int i = 1; i <= size; i++) {
            System.out.println("Создание обьекта Post" + i);
            System.out.println("Введите значение для свойства content:");
            String content = SCANNER.next();
            SCANNER.nextLine();
            System.out.println("Введите значение для свойства created в формате yyyy-mm-dd hh:mm:ss");
            String created = SCANNER.nextLine();
            System.out.println("Введите значение для свойства updated в формате yyyy-mm-dd hh:mm:ss");
            String updated = SCANNER.nextLine();
            System.out.println("Введите количество создания элементов для коллеции типа Label:");
            List<Label> collectLabel = new ArrayList<>();
            int size2 = SCANNER.nextInt();
            for (int j = 1; j <= size2; j++) {
                System.out.println();
                System.out.println("Создание обьекта номер " + j);
                System.out.println("Введите name для Label");
                Label label = new Label(SCANNER.next(), Status.ACTIVE);
                label.setId(j);
                collectLabel.add(label);
            }
            System.out.println("Обьект post успешно создан");
            Post post = new Post(content,created,updated,collectLabel,PostStatus.ACTIVE);
            post.setId(i);
            list.add(post);
        }
        int id = writerController.createObject(firstName,lastName,Status.ACTIVE,list).getId();
        System.out.println("Обьект writer успешно создан id: " + id);
    }
    public void updateView() {
        List<Writer> writers = writerController.getAllWriter();
        writers.stream().forEach(System.out::println);
        System.out.println("Введите id сущности которую хотите изменить");
        Writer writerUpgrade = writerController.getByObject(SCANNER.nextInt());
        while (true) {
            System.out.println(
                    "1.Изменить firstName\n" +
                            "2.Изменить lastName\n" +
                            "3.Выйти из режима редактирования\n" +
                            "Выберите пункт который хотите изменить");
            int paragraph = SCANNER.nextInt();
            switch (paragraph) {
                case 1: {
                    System.out.println("Введите новое значение для свойства firstName:");
                    writerUpgrade.setFirstName(SCANNER.next());
                    break;
                }
                case 2: {
                    System.out.println("Введите новое значение для свойства lastName:");
                    writerUpgrade.setLastName(SCANNER.next());
                    break;
                }
                case 3: {
                    System.out.println("id: " + writerController.editObject(writerUpgrade).getId());
                    return;
                }
            }
        }
    }
}
