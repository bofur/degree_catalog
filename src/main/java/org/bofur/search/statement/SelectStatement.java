package org.bofur.search.statement;

public class SelectStatement implements Statement {
	private String field;
	private String table;
	private Statement condition;
	
	public SelectStatement(String field, String table, Statement condition) {
		this.field = field;
		this.table = table;
		this.condition = condition;
	}

	public String generate() {
		String cond = condition.generate();
		if (cond.isEmpty()) return "SELECT " + field + " FROM " + table;
		return "SELECT " + field + " FROM " + table + " WHERE " + cond;
	}
}
