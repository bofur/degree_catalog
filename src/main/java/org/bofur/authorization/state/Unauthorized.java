package org.bofur.authorization.state;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bofur.R;
import org.bofur.authorization.AuthorizationManager;
import org.bofur.bean.User;
import org.bofur.dao.DaoFactory;

import android.content.Context;
import android.widget.EditText;

public class Unauthorized extends State{
	public Unauthorized() {
		super(R.layout.authrization_dialog);
	}
	
	public void showCreateUserDialog(Context context) {}

	public void showSetupPasswordDialog(Context context) {
		throw new IllegalStateException();
	}

	public void showAuthorizationDialog(final Context context) {
		showDialog(context, R.string.login_title);
	}

	public int getOptionMenuResource() {
		return R.menu.unauthorized_menu;
	}
	
	@Override
	protected void validate() throws Exception {
		String login = ((EditText)dialogView.findViewById(R.id.login)).getText().toString();
		String password = ((EditText)dialogView.findViewById(R.id.password)).getText().toString();
		
		if (login.isEmpty() || password.isEmpty())
			throw new Exception("Не все поля заполнены");

		password = new String(Hex.encodeHex(DigestUtils.md5(password)));
		User user = new User(login, password);
		if (DaoFactory.getUserDao().tryLogin(user) == false)
			throw new Exception("Неверно введен логин или пароль");
	}

	@Override
	protected void onSuccess() {
		State state = new Authorized(DaoFactory.getUserDao().getUser());
		AuthorizationManager.getInstance().changeState(state);
	}
}
