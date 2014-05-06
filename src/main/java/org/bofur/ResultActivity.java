package org.bofur;

import java.util.ArrayList;

import org.bofur.adapter.ArrayListAdapter;
import org.bofur.adapter.SearchResultAdapter;
import org.bofur.bean.Degree;
import org.bofur.bean.Department;
import org.bofur.bean.Facility;
import org.bofur.bean.Speciality;
import org.bofur.bean.Student;

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
        
        Facility facility = new Facility(1, "Факультет");
        Department department = new Department(1, facility, "Информатика и программное обеспечение");
        Speciality speciality = new Speciality(1, department, "Программное обеспечение вычислительной техники и автоматизированных систем");
        
        Student student = new Student(1, speciality, "Андрей", "Юрьевич", "Кузнецов");
        Degree degree = new Degree(1, "Супер крутой диплом", student, 2005);
        
        ArrayList<Degree> degrees = new ArrayList<Degree>();
        degrees.add(degree);
        
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
