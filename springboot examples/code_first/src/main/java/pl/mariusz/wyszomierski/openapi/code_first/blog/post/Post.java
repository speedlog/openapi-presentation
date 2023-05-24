package pl.mariusz.wyszomierski.openapi.code_first.blog.post;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Post {

    private UUID id;
    private String title;
    private String description;
    private OffsetDateTime creationTime;
    private OffsetDateTime lastUpdateDate;

    public Post() {
        // needed for deserialization
    }

    public Post(String title, String description) {
        this.title = title;
        this.description = description;
        this.creationTime = OffsetDateTime.now();
    }

    public void updatePost(String title, String description) {
        this.title = title;
        this.description = description;
        this.lastUpdateDate = OffsetDateTime.now();
    }

    public UUID getId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public OffsetDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
