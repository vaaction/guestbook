package models;

import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.util.List;

public class PostTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void createPost() {
		// Create a new user and save it
		User bob = new User("bob@gmail.com", "secret", "Bob").save();

		// Create a new post
		new Post(bob, "My first post_id", "Hello world").save();

		// Test that the post has been created
		assertEquals(1, Post.count());

		// Retrieve all posts created by Bob
		List<Post> bobPosts = Post.find("byAuthor", bob).fetch();

		// Tests
		assertEquals(1, bobPosts.size());
		Post firstPost = bobPosts.get(0);
		assertNotNull(firstPost);
		assertEquals(bob, firstPost.author);
		assertEquals("My first post_id", firstPost.title);
		assertEquals("Hello world", firstPost.content);
		assertNotNull(firstPost.postedDate);
	}

}
