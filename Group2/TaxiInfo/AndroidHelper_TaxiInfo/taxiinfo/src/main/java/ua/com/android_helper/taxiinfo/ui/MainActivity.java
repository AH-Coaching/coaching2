package ua.com.android_helper.taxiinfo.ui;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ua.com.android_helper.taxiinfo.R;
import ua.com.android_helper.taxiinfo.db.SQLiteContract;
import ua.com.android_helper.taxiinfo.utils.Keys;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
                onNavigationDrawerItemSelected(1);
    }


    @Override
    public void onNavigationDrawerItemSelected(long position) {
        // update the main content by replacing fragments
        Bundle bundle = new Bundle();
        bundle.putLong(SQLiteContract.City.COLUMN_CITY_ID, position);

        Fragment fragment = new MainListFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }



    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
       // actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Fill data", Toast.LENGTH_SHORT).show();
            fillData();
            return true;
        }else   if (id == R.id.add_service) {
            Fragment fragment = new AddService();
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().addToBackStack("addservice")
                    .replace(R.id.container, fragment)
                    .commit();


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillData() {

        /*ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(SQLiteContract.City.COLUMN_CITY_ID, 2);
        values.put(SQLiteContract.City.COLUMN_CITY_NAME, "Test 2");
        values.put(SQLiteContract.City.COLUMN_CITY_VERSION, 1);
        contentResolver.insert(SQLiteContract.City.CONTENT_URI, values);

        values = new ContentValues();
        values.put(SQLiteContract.Taxiname.COLUMN_CITY_ID, 2);
        values.put(SQLiteContract.Taxiname.COLUMN_TAXI_NAME, "Taxi megasuper");
        values.put(SQLiteContract.Taxiname.COLUMN_TAXI_INFO, "nichego");
        values.put(SQLiteContract.Taxiname.COLUMN_TAXI_RATE, 1);
        contentResolver.insert(SQLiteContract.Taxiname.CONTENT_URI, values);*/


    /*    ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(SQLiteContract.Details.COLUMN_DETAILS_SERVICE_ID, 2);
        values.put(SQLiteContract.Details.COLUMN_DETAILS_NUMBER_VALUE, "04435522078");
        values.put(SQLiteContract.Details.COLUMN_DETAILS_TYPE, "type1");
        contentResolver.insert(SQLiteContract.Details.CONTENT_URI, values);*/

    }


}