package pl.wyszomierski.mariusz.openapi.contract_first.blog.post;

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
import pl.wyszomierski.mariusz.openapi.model.EditablePost;
import pl.wyszomierski.mariusz.openapi.model.Post;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController implements pl.wyszomierski.mariusz.openapi.api.ApiApi {

    private final PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody EditablePost editablePost) {
        var newPost = new Post().title(editablePost.getTitle()).description(editablePost.getDescription());
        repository.save(newPost);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable("id") UUID postId,
                                           @RequestBody EditablePost editablePost) {
        Post post = repository.findById(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        post.title(editablePost.getTitle());
        post.description(editablePost.getDescription());
        repository.save(post);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") UUID id) {
        repository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> viewPost(@PathVariable("id") UUID id) {
        var post = repository.findById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

}
