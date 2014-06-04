package org.bofur.authorization.state;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bofur.R;
import org.bofur.authorization.AuthorizationManager;
import org.bofur.bean.User;
import org.bofur.dao.DaoFactory;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

public class NoUser extends State {
	public NoUser() {
		super(R.layout.create_user_dialog);
	}

	public void showCreateUserDialog(final Context context) {
		Log.i("LOGS", "showCreateUserDialog");
		showDialog(context, R.string.create_user_title);
	}

	public void showSetupPasswordDialog(Context context) {
		throw new IllegalStateException();
	}
	
	public void showAuthorizationDialog(Context context) {
		throw new IllegalStateException();
	}
	
	public int getOptionMenuResource() {
		return R.menu.unauthorized_menu;
	}
	
	@Override
	protected void validate() throws Exception {
		String login = ((EditText)dialogView.findViewById(R.id.login)).getText().toString();
		String password = ((EditText)dialogView.findViewById(R.id.password)).getText().toString();
		String confirmation = ((EditText)dialogView.findViewById(R.id.confirmation)).getText().toString();
		
		if (login.isEmpty() || password.isEmpty() || confirmation.isEmpty())
			throw new Exception("Не все поля заполнены");
		
		if (password.length() < 6) 
			throw new Exception("Паролье должен содержать не менее 6 символов");
			
		if (password.equals(confirmation) == false) 
			throw new Exception("Пароль и подтверждение не совпадают");
	}
	
	@Override
	protected void onSuccess() {
		State state = new Authorized(createUser());
		AuthorizationManager.getInstance().changeState(state);
	}
	
	@Override
	protected void setupDialog(AlertDialog dialog) {
		dialog.setCancelable(false);
	}
	
	private User createUser() {
		String login = ((EditText)dialogView.findViewById(R.id.login)).getText().toString();
		String password = ((EditText)dialogView.findViewById(R.id.password)).getText().toString();
		password = new String(Hex.encodeHex(DigestUtils.md5(password)));
		
		User user = new User(login, password);
		DaoFactory.getUserDao().save(user);
		return user;
	}
}
