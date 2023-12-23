package abdu.tumg.view;

import abdu.tumg.controller.LabelController;
import abdu.tumg.model.Label;
import abdu.tumg.model.PostStatus;
import abdu.tumg.model.Status;

import java.util.Scanner;

public class LabelView {
    private final Scanner SCANNER = new Scanner(System.in);
    private LabelController labelController;

    public LabelView(LabelController labelController) {
        this.labelController = labelController;
    }

    public void labelStart() {
        while (true) {
            System.out.println();
            System.out.println("Вы находитесь в среде разроботки сущности Label\n");
            System.out.println("Выберите нужный пункт\n");
            System.out.println(
                    "1.Создать сущность Label\n"
                            + "2.Изменить сущность Label\n"
                            + "3. Удалить сущность Label\n"
                            + "4.<- Вернуться назад");
            switch (SCANNER.nextInt()) {
                case 1: {
                    createLabelView();
                    break;
                }
                case 2: {
                    updateView();
                    break;
                }
                case 3: {
                    System.out.println();
                        labelController.getAllLabel().stream().forEach(System.out::println);
                        System.out.println("Введите id сущности которую хотите удалить");
                        labelController.deleteObject(SCANNER.nextInt());
                    break;
                }
                case 4: {
                    return;
                }
            }
        }
    }
    public void createLabelView() {
        System.out.println("Введите name для Label");
        System.out.println("Обьект Label успешно создан id: " + labelController.createObject(SCANNER.next(), Status.ACTIVE).getId());
    }
    public void updateView() {
        labelController.getAllLabel().stream().forEach(System.out::println);
        System.out.println("Введите id обьекта который хотите изменить");
        Label label = labelController.getByIdLabelController(SCANNER.nextInt());
        while (true) {
            System.out.println(
                    "1.Изменить name\n"
                            + "2.Выйти и сохранить изменения");
            switch (SCANNER.nextInt()) {
                case 1: {
                    System.out.println("Введите новое значени для name");
                    label.setName(SCANNER.next());
                    break;
                }
                case 2: {
                    System.out.println("id: " + labelController.editObject(label).getId());
                    return;
                }
            }
        }
    }
}
