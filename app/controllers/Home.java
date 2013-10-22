package controllers;

import models.Post;
import models.User;
import models.VoteStatus;
import play.data.validation.Required;

import java.util.List;

public class Home extends BaseController {
	public static void index() {
		List<Post> posts = Post.find(
				"order by postedDate desc"
		).from(0).fetch(10);
		render(posts);
	}

	public static void vote(@Required(message = "Id is required") Long id,
							@Required(message = "VoteStatus is required") VoteStatus status) {
		if(Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			models.Post post = models.Post.findById(id);
			post.vote(user.id, status);
			renderText(post.score());
		}
	}
}