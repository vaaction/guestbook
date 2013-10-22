package controllers;

import models.User;
import play.data.validation.Required;

public class Post extends BaseController {

	public static void newPost() {
		render();
	}

	public static void savePost(@Required(message="Title is required") String title,
								@Required(message="A message is required") String content) {
		if(Security.isConnected()) {
			validation.max(title.length(), 100);
			validation.max(content.length(), 10000);

			if (validation.hasErrors()) {
				render("Post/newPost.html");
			}

			User user = User.find("byEmail", Security.connected()).first();
			new models.Post(User.connect(user.email, user.password), title, content).save();
			Home.index();
		}
	}
}