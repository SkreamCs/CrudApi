package abdu.tumg.model;


public class Label {
    private String name;
    private int id;
    private Status status;

    public Label(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Label{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
