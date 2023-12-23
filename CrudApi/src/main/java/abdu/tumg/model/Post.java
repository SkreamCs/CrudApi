package abdu.tumg.model;

import java.util.List;

public class Post {
    private int id;
    private String content;
    private String created;
    private String updated;
    private List<Label> labels;
    private PostStatus postStatus;

    public Post(String content, String created, String updated, List<Label> labels, PostStatus postStatus) {
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.postStatus = postStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", postLabel=" + labels +
                ", postStatus=" + postStatus +
                '}';
    }
}

