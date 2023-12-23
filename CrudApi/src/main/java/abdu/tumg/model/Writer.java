package abdu.tumg.model;

import java.util.List;

public class Writer {
    private int id;
    private String firstName;
    private String lastName;
    private Status status;
    private List<Post> posts;

    public Writer(String firstName, String lastName, Status status, List<Post> postWriter) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.posts = postWriter;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
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

    public void setStatus(Status status) {
        this.status = status;
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
    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                ", postWriter=" + posts +
                '}';
    }
}

