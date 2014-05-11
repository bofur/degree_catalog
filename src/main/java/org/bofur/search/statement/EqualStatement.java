package org.bofur.search.statement;

import org.bofur.bean.Bean;

public class EqualStatement implements Statement {
	private String field;
	private String value;
	
	public EqualStatement(String field, String value) {
		this.field = field;
		this.value = value;
	}
	
	public EqualStatement(String field, Bean bean) {
		this.field = field;
		this.value = bean != null ? bean.getId() + "" : "";
	}
	
	public String generate() {
		if (value == null || value.isEmpty()) return "";
		return field + " = " + value;
	}
}
