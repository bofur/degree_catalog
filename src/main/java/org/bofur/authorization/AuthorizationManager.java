package org.bofur.authorization;

import org.bofur.authorization.state.NoUser;
import org.bofur.authorization.state.State;
import org.bofur.authorization.state.Unauthorized;
import org.bofur.bean.User;
import org.bofur.dao.DaoFactory;

import android.content.Context;

public class AuthorizationManager {
	private static AuthorizationManager instance;
	
	private State state;
	
	public static AuthorizationManager getInstance() {
		if (instance == null) instance = new AuthorizationManager();
		return instance;
	}
	
	public int getOptionMenuResource() {
		return state.getOptionMenuResource();
	}
	
	public void showCreateUserDialog(Context context) {
		state.showCreateUserDialog(context);
	}
	
	public void showSetupPasswordDialog(Context context) {
		state.showSetupPasswordDialog(context);
	}
	
	public void showAuthorizationDialog(Context context) {
		state.showAuthorizationDialog(context);
	}
	
	public void changeState(State state) {
		this.state = state;
	}
	
	private AuthorizationManager() {
		User user = DaoFactory.getUserDao().getUser(); 
		state = user != null ? new Unauthorized() : new NoUser();
	}
}
