package abdu.tumg.controller;

import abdu.tumg.model.Label;
import abdu.tumg.model.Post;
import abdu.tumg.model.PostStatus;
import abdu.tumg.repository.PostRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostController {
    private PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public Post createObject(String contentCreate, String createdCreate, String updatedCreate, List<Label> posts, PostStatus postStatus) {
        Post post = new Post(contentCreate,createdCreate,updatedCreate, posts,PostStatus.DELETED);
        try {
             postRepository.save(post);
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return post;
    }

    public List<Post> getAllPost() {
        try {
            List<Post> posts = postRepository.getAll();
            return posts;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return null;
    }
    public Post getByIdObject(int id) {
        try {
            return postRepository.getByID(id);
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return null;
    }
    public Post editObject(Post postUpgrade) {
        try {
            postRepository.update(postUpgrade);
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return postUpgrade;
    }
    public void deleteObject(int id) {
        try {
            postRepository.delete(id);
        } catch (SQLException e ) {
            e.getErrorCode();
        }
    }
}



