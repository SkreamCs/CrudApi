package abdu.tumg.model;

import java.util.List;

public class Post {
    private int id;
    private String content;
    private String created;
    private String updated;
    private List<Label> postLabel;
    private PostStatus postStatus;

    public Post(String content, String created, String updated, List<Label> postLabel, PostStatus postStatus) {
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.postLabel = postLabel;
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

    public void setPostLabel(List<Label> postLabel) {
        this.postLabel = postLabel;
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

    public List<Label> getPostLabel() {
        return postLabel;
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
                ", postLabel=" + postLabel +
                ", postStatus=" + postStatus +
                '}';
    }
}

