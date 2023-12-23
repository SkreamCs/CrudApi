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
        Post post = new Post(contentCreate,createdCreate,updatedCreate, posts,PostStatus.ACTIVE);
        postRepository.save(post);
        return post;
    }

    public List<Post> getAllPost() {
            List<Post> posts = postRepository.getAll();
            return posts;
    }
    public Post getByIdObject(int id) {
            return postRepository.getByID(id);
    }
    public Post editObject(Post postUpgrade) {
            postRepository.update(postUpgrade);
        return postUpgrade;
    }
    public void deleteObject(int id) {
        postRepository.delete(id);
    }
}



