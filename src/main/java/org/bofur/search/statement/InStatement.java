package org.bofur.search.statement;

public class InStatement implements Statement{
	private String field;
	private Statement value;
	
	public InStatement(String field, Statement value) {
		this.field = field;
		this.value = value;
	}
	
	public String generate() {
		String cond = value.generate();
		if(cond.isEmpty()) return "";
		
		return field + " IN (" + value + ")";
	}
}
