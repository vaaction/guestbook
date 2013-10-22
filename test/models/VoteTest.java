package models;

import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.util.List;

public class VoteTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void vote() {
		// Create a new user and save it
		User bob = new User("bob@gmail.com", "secret", "Bob").save();

		// Create a new post
		Post post = new Post(bob, "My first post_id", "Hello world").save();
		post.vote(bob.id, VoteStatus.UNLIKE);
		// Test that the like has been created
		assertEquals(1, Vote.count());

		Vote bobVote = Vote.find("byUserId", bob.id).first();
		assertEquals(VoteStatus.UNLIKE, bobVote.voteStatus);

		List<Post> posts = Post.findAll();
		assertEquals(VoteStatus.UNLIKE, posts.get(0).votes.iterator().next().voteStatus);

		post.delete();
		assertEquals(0, Vote.count());
	}

}
