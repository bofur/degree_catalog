package org.bofur.search.statement;

public class LikeStatement implements Statement {
	private String field;
	private String value;
	
	public LikeStatement(String field, String value) {
		this.field = field;
		this.value = value;
	}
	
	public String generate() {
		if (value.isEmpty()) return "";
		return field + "LIKE '%" + value + "%'";
	}
}
