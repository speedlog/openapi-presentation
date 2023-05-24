package pl.mariusz.wyszomierski.openapi.code_first.blog.post;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PostRepository {

    private final Map<UUID, Post> inMemoryDatabase = new ConcurrentHashMap<>();


    public void save(Post post) {
        inMemoryDatabase.put(post.getId(), post);
    }

    public Post findById(UUID postId) {
        return inMemoryDatabase.get(postId);
    }

    public void delete(UUID id) {
        inMemoryDatabase.remove(id);
    }
}
