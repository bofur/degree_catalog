package org.bofur.entities;

import org.bofur.R;
import org.bofur.adapter.ArrayListAdapter;
import org.bofur.bean.Bean;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

abstract public class EntitiesActivity extends Activity implements OnItemClickListener {
	protected ArrayListAdapter<? extends Bean> adapter;
	
	abstract protected OnClickListener getOnEditClickListener(int position);
	abstract protected OnClickListener getOnAddClickListener();
	abstract void removeEntity(int position);
	
	abstract protected void validate() throws Exception;
	
	abstract protected void onShowDilog(AlertDialog dialog);
	abstract protected View createDialogView();
	
	protected void setupEntitesList() {
		ListView entities = (ListView)findViewById(R.id.entities);
		entities.setOnItemClickListener(this);
		entities.setAdapter(adapter);
		
		registerForContextMenu(entities);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 0, 0, R.string.remove_button);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();  
        removeEntity(info.position);
        
        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		AlertDialog dialog = createDialog(R.string.edit_prompt, getOnEditClickListener(position));
		onShowDilog(dialog);
		dialog.show();
	}

	public void add(View view) {
		createDialog(R.string.edit_prompt, getOnAddClickListener()).show();
	}

	private AlertDialog createDialog(int titleResourceId, final OnClickListener listener) {
		final AlertDialog dialog = new AlertDialog.Builder(this)
				.setView(createDialogView())
				.setTitle(titleResourceId)
				.setPositiveButton(R.string.ok_button, null)
				.setNegativeButton(R.string.cancel_button, null)
				.create();
		
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			private OnClickListener validationListener = new OnClickListener() {
				public void onClick(View v) {
					try {
						validate();
						listener.onClick(v);
						dialog.dismiss();
					} catch(Exception e) {
						showError(e.getMessage());
					}
				}
			};
			
			public void onShow(DialogInterface d) {
				Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
				button.setOnClickListener(validationListener);
			}
		});
		
		return dialog;
	}

	private void showError(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}