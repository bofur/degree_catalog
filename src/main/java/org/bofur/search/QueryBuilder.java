package org.bofur.search;

import org.bofur.R;
import org.bofur.SearchActivity;
import org.bofur.bean.Department;
import org.bofur.bean.Facility;
import org.bofur.bean.Speciality;
import org.bofur.search.statement.CompositStatement;
import org.bofur.search.statement.EqualStatement;
import org.bofur.search.statement.InStatement;
import org.bofur.search.statement.LikeStatement;
import org.bofur.search.statement.SelectStatement;
import org.bofur.search.statement.SelectWithConditionStatement;
import org.bofur.search.statement.Statement;

import android.widget.EditText;

public class QueryBuilder {
	private SearchActivity activity;
	private CompositStatement conditions;
	
	public QueryBuilder(SearchActivity activity) {
		this.activity = activity;
	}
	
	public String getQuery() {
		conditions = new CompositStatement();
		
		addNameCond(getTextFromEdit(R.id.title_text));
		addYearStatement(getTextFromEdit(R.id.year));
		addStudentCondition(getTextFromEdit(R.id.first_name_text), 
				getTextFromEdit(R.id.second_name_text), getTextFromEdit(R.id.last_name_text));
				
		addSpecialityStatement(activity.getModerator().getSpeciality());
		addDepartmentStatement(activity.getModerator().getDepartment());
		addFacilityStatement(activity.getModerator().getFacility());

    	return new SelectStatement("*", "degrees", conditions).generate();
	}
	
	public void addNameCond(String name) {
		conditions.add(new LikeStatement("name", name));
	}
	
	public void addStudentCondition(String firstName, String secondName, String lastName) {
    	CompositStatement studentConditions = new CompositStatement();
    	studentConditions.add(new LikeStatement("first_name", firstName));
    	studentConditions.add(new LikeStatement("second_name", secondName));
    	studentConditions.add(new LikeStatement("last_name", lastName));
    	
    	Statement select = new SelectWithConditionStatement("id", "students", studentConditions);
    	Statement in = new InStatement("student_id", select);
    	conditions.add(in);
	}
	
	public void addSpecialityStatement(Speciality speciality) {
    	Statement equal = new EqualStatement("speciality_id", speciality);
    	Statement select = new SelectWithConditionStatement("id", "students", equal);
    	Statement in = new InStatement("student_id", select);
    	conditions.add(in);
	}
	
	public void addDepartmentStatement(Department department) {
    	Statement equal = new EqualStatement("department_id", department);
    	Statement specialitiesSelect = new SelectWithConditionStatement("id", "specialites", equal);
    	Statement inStudents = new InStatement("student_id", specialitiesSelect);
    	Statement studentsSelect = new SelectWithConditionStatement("id", "students", inStudents);
    	Statement in = new InStatement("student_id", studentsSelect);
    	
    	conditions.add(in);
	}
	
	public void addFacilityStatement(Facility facility) {
    	Statement equal = new EqualStatement("facility_id", facility);
    	Statement departmentsSelect = new SelectWithConditionStatement("id", "departments", equal);
    	Statement inDepartments = new InStatement("department_id", departmentsSelect);
    	Statement specialitiesSelect = new SelectWithConditionStatement("id", "specialities", inDepartments);
    	Statement inStudents = new InStatement("speciality_id", specialitiesSelect);
    	Statement studentsSelect = new SelectWithConditionStatement("id", "students", inStudents);
    	Statement in = new InStatement("student_id", studentsSelect);
    	
    	conditions.add(in);
	}
	
	public void addYearStatement(String year) {
		conditions.add(new EqualStatement("year", year));
	}
	
	private String getTextFromEdit(int editId) {
		return ((EditText)activity.findViewById(editId)).getText().toString();
	}
}
