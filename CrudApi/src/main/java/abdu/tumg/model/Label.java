package abdu.tumg.model;

public class Label {
    private String name;
    private int id;
    private PostStatus postStatus;

    public Label(String name, PostStatus postStatus) {
        this.name = name;
        this.postStatus = postStatus;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    @Override
    public String toString() {
        return "Label{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", postStatus=" + postStatus +
                '}';
    }
}
