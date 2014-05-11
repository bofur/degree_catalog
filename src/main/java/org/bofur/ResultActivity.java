package org.bofur;

import java.util.ArrayList;

import org.bofur.adapter.ArrayListAdapter;
import org.bofur.adapter.SearchResultAdapter;
import org.bofur.bean.Degree;
import org.bofur.dao.DaoFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ResultActivity extends Activity implements OnItemClickListener {
	private ArrayListAdapter<Degree> adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        
        String condition = getIntent().getStringExtra("condition");
        ArrayList<Degree> degrees = DaoFactory.getDegreeDao().getByConditions(condition);
        
        adapter = new SearchResultAdapter(this, degrees);
        ListView searchResult = (ListView)findViewById(R.id.search_result);
        searchResult.setAdapter(adapter);
        searchResult.setOnItemClickListener(this);
    }

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, DegreeActivity.class);
		intent.putExtra("degree", (Parcelable)adapter.getItem(position));
		startActivity(intent);
	}
}
