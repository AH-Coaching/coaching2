package ua.com.androidhelper.ui.ui;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import ua.com.androidhelper.R;
import ua.com.androidhelper.ui.db.SQLiteContract;
import ua.com.androidhelper.ui.AHService;

public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new BlankFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_camera) {
            Toast.makeText(this,"Tada",Toast.LENGTH_SHORT).show();
            //fillDate();
            Intent intent = new Intent(this, AHService.class);
            startService(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void fillDate(){
        ContentResolver contentResolver = getContentResolver();


        ContentValues cv = new ContentValues();
        cv.put(SQLiteContract.Items.COLUMN_ITEM_TEXT,"Test");
        contentResolver.insert(SQLiteContract.Items.CONTENT_URI, cv);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_details, container, false);
            return rootView;
        }
    }

}
