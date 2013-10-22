package controllers;

import models.*;

public class Security extends Secure.Security {

	static void onDisconnected() {
		Home.index();
	}

	static boolean authenticate(String email, String password) {
		return User.connect(email, password) != null;
	}
}