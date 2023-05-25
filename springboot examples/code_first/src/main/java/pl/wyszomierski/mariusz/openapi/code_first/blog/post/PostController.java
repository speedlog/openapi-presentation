package pl.wyszomierski.mariusz.openapi.code_first.blog.post;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody @Valid EditablePost editablePost) {
        var newPost = new Post(editablePost.title(), editablePost.description());
        repository.save(newPost);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable("id") UUID postId,
                                           @RequestBody
                                           @Valid EditablePost editablePost) {
        Post post = repository.findById(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        post.updatePost(editablePost.title(), editablePost.description());
        repository.save(post);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") UUID id) {
        repository.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> viewPost(@PathVariable("id") UUID id) {
        var post = repository.findById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    private final PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }
}
