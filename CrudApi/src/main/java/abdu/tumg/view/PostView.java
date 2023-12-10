package abdu.tumg.view;

import abdu.tumg.controller.Controller;
import abdu.tumg.controller.PostController;
import abdu.tumg.model.Label;
import abdu.tumg.model.Post;
import abdu.tumg.model.PostStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final Scanner SCANNER = new Scanner(System.in);
    private PostController postController;

    public PostView(PostController postController) {
        this.postController = postController;
    }
    public void postStart() {
        while (true) {
            System.out.println();
            System.out.println("Вы находитесь в среде разроботки сущности Post\n");
            System.out.println("Выберите нужный пункт\n");
            System.out.println(
                    "1.Создать сущность Post\n"
                            + "2.Изменить сущность Post\n"
                            + "3.Удалить сущность Post\n"
                            + "4.<- Вернуться назад");
            switch (SCANNER.nextInt()) {
                case 1: {
                    createPostView();
                    break;
                }
                case 2: {
                   updateView();
                    break;
                }
                case 3: {
                    System.out.println();
                        postController.getAllPost().stream().forEach(System.out::println);
                        System.out.println("Введите id сущности которую хотите удалить");
                        postController.deleteObject(SCANNER.nextInt());
                    break;
                }
                case 4: {
                    return;
                }
              }
            }
        }
        public void createPostView() {
            System.out.println("Введите значение для свойства content:");
            String content = SCANNER.next();
            SCANNER.nextLine();
            System.out.println("Введите значение для свойства created в формате yyyy-mm-dd hh:mm:ss");
            String created = SCANNER.nextLine();
            System.out.println("Введите значение для свойства updated в формате yyyy-mm-dd hh:mm:ss");
            String updated = SCANNER.nextLine();
            System.out.println("Введите количество создания элементов для коллеции типа Label:");
            List<Label> collectLabel = new ArrayList<>();
            int size = SCANNER.nextInt();
            for (int i = 1; i <= size; i++) {
                System.out.println();
                System.out.println("Создание обьекта номер " + i);
                System.out.println("Введите name для Label");
                Label label = new Label(SCANNER.next(), PostStatus.ACTIVE);
                label.setId(i);
                collectLabel.add(label);
            }
            System.out.println("Обьект post успешно создан id: " + postController.createObject(content,created,updated,collectLabel,PostStatus.ACTIVE).getId());
        }
        public void updateView() {
            System.out.println("Введите id обьекта который хотите изменить");
            postController.getAllPost().stream().forEach(System.out::println);
            Post postUpgrade = postController.getByIdObject(SCANNER.nextInt());
            while (true) {
                System.out.println("Выберите нужный пункт");
                System.out.println(
                        "1.Изменить content\n"
                                + "2.Изменить created\n"
                                + "3.Изменить updated\n"
                                + "4.Изменить коллекцию Label\n"
                                + "5.Сохранить изменения и выйти");
                int paragraph = SCANNER.nextInt();
                switch (paragraph) {
                    case 1: {
                        System.out.println("Введите новой значение для content");
                        postUpgrade.setContent(SCANNER.next());
                        break;
                    }
                    case 2: {
                        System.out.println("Введите новое значение для свойства created:");
                        postUpgrade.setCreated(SCANNER.next());
                        break;
                    }
                    case 3: {
                        System.out.println("Введите новое значение для updated:");
                        postUpgrade.setUpdated(SCANNER.next());
                        break;
                    }
                    case 4: {
                        System.out.println("Выберите номер обьекта который хотите изменить:");
                        postUpgrade.getPostLabel().stream().forEach(System.out::println);
                        int number = SCANNER.nextInt() - 1;
                        System.out.println("Введите новое значение для name");
                        postUpgrade.getPostLabel().set(number, new Label(SCANNER.next(), PostStatus.UNDER_REVIEW));
                        break;
                    }
                    case 5: {
                        System.out.println("id " + postController.editObject(postUpgrade).getId());
                        return;
                    }
                }
            }
        }
    }

