package abdu.tumg.model;

import java.util.List;

public class Writer {
    private int id;
    private String firstName;
    private String lastName;
    private PostStatus postStatus;
    private List<Post> postWriter;

    public Writer(String firstName, String lastName, PostStatus postStatus, List<Post> postWriter) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postStatus = postStatus;
        this.postWriter = postWriter;
    }

    public List<Post> getPostWriter() {
        return postWriter;
    }

    public void setPostWriter(List<Post> postWriter) {
        this.postWriter = postWriter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public PostStatus getPostStatus() {
        return postStatus;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", postStatus=" + postStatus +
                ", postWriter=" + postWriter +
                '}';
    }
}

