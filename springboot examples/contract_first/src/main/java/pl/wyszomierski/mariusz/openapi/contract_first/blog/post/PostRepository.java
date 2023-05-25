package pl.wyszomierski.mariusz.openapi.contract_first.blog.post;

import org.springframework.stereotype.Component;
import pl.wyszomierski.mariusz.openapi.model.Post;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PostRepository {

    private final Map<UUID, Post> inMemoryDatabase = new ConcurrentHashMap<>();


    public void save(Post post) {
        // simulate that post is entity
        if (post.getId() == null) {
            post.setId(UUID.randomUUID());
        }
        if (post.getLastUpdateDate() != null) {
            post.setLastUpdateDate(OffsetDateTime.now());
        }
        if (post.getCreationTime() == null) {
            post.setCreationTime(OffsetDateTime.now());
        }

        inMemoryDatabase.put(post.getId(), post);
    }

    public Post findById(UUID postId) {
        return inMemoryDatabase.get(postId);
    }

    public void delete(UUID id) {
        inMemoryDatabase.remove(id);
    }
}
