package org.bofur;


import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.android.ContextHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SearchActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_options);

        SQLiteDatabase db = openOrCreateDatabase("db", Context.MODE_PRIVATE, null);
//        ContextHolder.setContext(this);
//        Flyway flyway = new Flyway();
//        flyway.setDataSource("jdbc:sqlite:" + db.getPath(), "", "");
//        flyway.migrate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(org.bofur.R.menu.main, menu);
		return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	return super.onOptionsItemSelected(item);
    }
    
    public void search(View view) {
    	Intent searchResult = new Intent(this, ResultActivity.class);
    	startActivity(searchResult);
	}
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }
}

