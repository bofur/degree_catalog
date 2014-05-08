package org.bofur.entities;

import java.util.ArrayList;

import org.bofur.R;
import org.bofur.adapter.ArrayListAdapter;
import org.bofur.bean.Facility;
import org.bofur.dao.DaoFactory;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class FacilitiesActivity extends EntitiesActivity {
	private ArrayList<Facility> facilities;
	private EditText facilityName;
	private Facility editingFacility;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entities_list);
		
		facilities = DaoFactory.getFacilityDao().getAll();
		adapter = new ArrayListAdapter<Facility>(this, 
				android.R.layout.simple_list_item_1, facilities);

		setupEntitesList();
	}
	
	protected OnClickListener getOnAddClickListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				Facility facility = new Facility(facilityName.getText().toString());
				DaoFactory.getFacilityDao().save(facility);
				facilities.add(facility);
				
				adapter.notifyDataSetChanged();
			}
		};
	}
	
	protected OnClickListener getOnEditClickListener(final int position) {
		editingFacility = (Facility)adapter.getItem(position);
		return new OnClickListener() {
			public void onClick(View v) {
				editingFacility.setName(facilityName.getText().toString());
				DaoFactory.getFacilityDao().update(editingFacility);
				adapter.notifyDataSetChanged();
			}
		};
	}
	
	@Override
	void removeEntity(int position) {
        Facility facility = (Facility) adapter.getItem(position);
        DaoFactory.getFacilityDao().remove(facility);
        facilities.remove(position);
	}
	
	@Override
	protected View createDialogView() {
		return facilityName = new EditText(this);
	}

	@Override
	protected void onShowDilog(AlertDialog dialog) {
		facilityName.setText(editingFacility.getName());
		facilityName.setSelectAllOnFocus(true);
	}
	
	protected void validate() throws Exception {
		String name = facilityName.getText().toString();
		if (name.isEmpty()) throw new Exception("Введите название");
	}

}
