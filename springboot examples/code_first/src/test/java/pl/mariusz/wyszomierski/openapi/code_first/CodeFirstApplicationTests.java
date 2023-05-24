package pl.mariusz.wyszomierski.openapi.code_first;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import pl.mariusz.wyszomierski.openapi.code_first.blog.post.EditablePost;
import pl.mariusz.wyszomierski.openapi.code_first.blog.post.Post;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CodeFirstApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	void testCrudOperations() {
		// test create
		String expectedTitle = "expected title";
		String expectedDescription = "expected description";
		var responseCreatePost = createPost(expectedTitle, expectedDescription);
		var createdPost = responseCreatePost.getBody();
		assertEquals(CREATED, responseCreatePost.getStatusCode());
		assertEquals(expectedTitle, createdPost.getTitle());
		assertEquals(expectedDescription, createdPost.getDescription());
		assertNotNull(createdPost.getCreationTime());
		assertNull(createdPost.getLastUpdateDate());

		// test update and read
		String newTitleExpected = "new title";
		String newDescriptionExpected = "new description";
		updatePost(createdPost.getId(), newTitleExpected, newDescriptionExpected);
		var responseAfterUpdate = readPost(createdPost.getId());
		var afterUpdate = responseAfterUpdate.getBody();
		assertEquals(OK, responseAfterUpdate.getStatusCode());
		assertEquals(newTitleExpected, afterUpdate.getTitle());
		assertEquals(newDescriptionExpected, afterUpdate.getDescription());
		assertNotNull(afterUpdate.getCreationTime());
		assertNotNull(afterUpdate.getLastUpdateDate());

		// test delete
		deletePost(afterUpdate.getId());
		ResponseEntity<Post> afterDelete = readPost(createdPost.getId());
		assertEquals(NOT_FOUND, afterDelete.getStatusCode());
	}

	private void deletePost(UUID id) {
		restTemplate.delete(getPostApiUrl() + "/" + id);
	}

	private ResponseEntity<Post> readPost(UUID id) {
		return restTemplate.getForEntity(getPostApiUrl() + "/" + id, Post.class);
	}

	private void updatePost(UUID id, String newTitle, String newDescription) {
		restTemplate.put(getPostApiUrl() + "/" + id, new EditablePost(newTitle, newDescription));
	}

	private ResponseEntity<Post> createPost(String expectedTitle, String expectedDescription) {
		return restTemplate.postForEntity(getPostApiUrl(), new EditablePost(expectedTitle, expectedDescription), Post.class);
	}

	private String getPostApiUrl() {
		return String.format("http://localhost:%d/api/posts", port);
	}

}
