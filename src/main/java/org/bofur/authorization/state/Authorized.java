package org.bofur.authorization.state;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bofur.R;
import org.bofur.bean.User;
import org.bofur.dao.DaoFactory;

import android.content.Context;
import android.widget.EditText;

public class Authorized extends State {
	private User user;
	
	public Authorized(User user) {
		super(R.layout.setup_password_dialog);
		this.user = user;
	}
	
	public void showCreateUserDialog(Context context) {
		throw new IllegalStateException();
	}

	public void showSetupPasswordDialog(final Context context) {
		showDialog(context, R.string.change_password_title);
	}

	public void showAuthorizationDialog(Context context) {
		throw new IllegalStateException();
	}

	public int getOptionMenuResource() {
		return R.menu.main;
	}

	protected void validate() throws Exception {
		String oldPassword = ((EditText)dialogView.findViewById(R.id.old_password)).getText().toString();
		String password = ((EditText)dialogView.findViewById(R.id.password)).getText().toString();
		String confirmation = ((EditText)dialogView.findViewById(R.id.confirmation)).getText().toString();
		
		if (oldPassword.isEmpty() || password.isEmpty() || confirmation.isEmpty())
			throw new Exception("Не все поля заполнены");

		if (oldPassword.equals(password))
			throw new Exception("Старый и новый пароль совпадают");

		if (user.getPassword().equals(new String(Hex.encodeHex(DigestUtils.md5(oldPassword)))) == false) 
			throw new Exception("Неверно введен старый пароль");
		
		if (password.equals(confirmation) == false)
			throw new Exception("Неверно введен старый пароль");
		
		if (DaoFactory.getUserDao().tryLogin(user) == false)
			throw new Exception("Пароль и подтверждение не совпадают");
	}
	
	@Override
	protected void onSuccess() {
		String password = ((EditText)dialogView.findViewById(R.id.password)).getText().toString();
		user.setPassword(new String(Hex.encodeHex(DigestUtils.md5(password))));
		DaoFactory.getUserDao().update(user);
	}

}
