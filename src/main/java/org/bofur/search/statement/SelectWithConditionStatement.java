package org.bofur.search.statement;

public class SelectWithConditionStatement implements Statement{
	private String field;
	private String table;
	private Statement condition; 
	
	public SelectWithConditionStatement(String field, String table, Statement condition) {
		this.field = field;
		this.table = table;
		this.condition = condition;	
	}

	public String generate() {
		String cond = condition.generate();
		if (cond.isEmpty()) return "";
		return "SELECT " + field + " FROM " + table + " WHERE " + cond;
	}

}
