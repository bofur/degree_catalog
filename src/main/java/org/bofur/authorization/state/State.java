package org.bofur.authorization.state;

import org.bofur.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

abstract public class State {
	protected View dialogView;
	private int dialogViewResourceId;
	
	abstract public void showCreateUserDialog(Context context);
	abstract public void showSetupPasswordDialog(Context context);
	abstract public void showAuthorizationDialog(Context context);
	abstract public int getOptionMenuResource();
	
	public State(int dialogViewResourceId) {
		this.dialogViewResourceId = dialogViewResourceId;
	}
	
	protected void showDialog(final Context context, int userTitleResId) {
		dialogView = View.inflate(context, dialogViewResourceId, null);
		final AlertDialog dialog = new AlertDialog.Builder(context)
			.setPositiveButton(R.string.ok_button, null)
			.setTitle(userTitleResId)
			.setCancelable(false)
			.setView(dialogView)
			.create();
		
		dialog.setOnShowListener(new OnShowListener() {
			OnClickListener okListener = new OnClickListener() {
				public void onClick(View v) {
					try {
						validate();
						onSuccess();
						dialog.dismiss();
					} catch (Exception e) {
						showError(context, e.getMessage());
					}
				}
			};
			
			public void onShow(DialogInterface d) {
				Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
				okButton.setOnClickListener(okListener);
			}
		});
		
		dialog.show();
	}
	
	abstract protected void validate() throws Exception;
	abstract protected void onSuccess();

	private void showError(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	protected void setupDialog(AlertDialog dialog) {}
}
