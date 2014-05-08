package org.bofur.search.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.text.TextUtils;

public class CompositStatement implements Statement{
	List<String> statements;
	
	public CompositStatement() {
		statements = new ArrayList<String>();
	}

	public void add(Statement statement) {
		statements.add(statement.generate());
	}
	
	public String generate() {
		statements.removeAll(Arrays.asList("", null));
		return TextUtils.join(" AND ", statements);
	}
}
