package lt.mano.shadywallpaperfrontend.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lt.mano.shadywallpaperfrontend.R;
import lt.mano.shadywallpaperfrontend.data.*;
import lt.mano.shadywallpaperfrontend.data.Thread;
import lt.mano.shadywallpaperfrontend.net.RandomDeserializer;
import lt.mano.shadywallpaperfrontend.net.ShadyWallpaperService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public abstract class BaseActivity extends ActionBarActivity{

    protected static final int NAV_DRAWER_ITEM_BROWSE = 0;
    protected static final int NAV_DRAWER_ITEM_FILTER = 1;

    private static final int NAV_DRAWER_TITLE_BROWSE = R.string.activity_browse_title;
    private static final int NAV_DRAWER_TITLE_FILTER = R.string.activity_filter_title;

    private ListView navigationDrawer;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private ShadyWallpaperService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setupToolbar(toolbar);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(navigationDrawer != null){
            navigationDrawer.setItemChecked(getSelfNavItem(), true);
        }
    }

    private void setupNavDrawer(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawer = (ListView) findViewById(R.id.navigation_drawer);

        if(navigationDrawer != null){
            navigationDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    onNavigationDrawerItemSelected(i);
                }
            });

            navigationDrawer.setAdapter(new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_activated_1,
                    android.R.id.text1,
                    new String[]{
                            getString(NAV_DRAWER_TITLE_BROWSE),
                            getString(NAV_DRAWER_TITLE_FILTER)
                    }));
            navigationDrawer.setItemChecked(getSelfNavItem(), true);
        }
    }

    protected void setupToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    protected boolean isNavDrawerOpened(){
        return drawerLayout.isDrawerOpen(Gravity.START);
    }

    protected void openNavDrawer(){
        drawerLayout.openDrawer(Gravity.START);
    }

    protected void closeNavDrawer(){
        drawerLayout.closeDrawer(Gravity.START);
    }

    protected abstract int getSelfNavItem();

    private void onNavigationDrawerItemSelected(int position){
        closeNavDrawer();
        switch (position){
            case NAV_DRAWER_ITEM_BROWSE:
                startActivity(new Intent(this, BrowseActivity.class));
                finish();
                break;
            case NAV_DRAWER_ITEM_FILTER:
                startActivity(new Intent(this, FilterActivity.class));
                break;
            default:
                throw new IllegalArgumentException("No drawer handling for position " + position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public ShadyWallpaperService getService() {
        if (service == null) {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Thread.class, new RandomDeserializer<Thread>())
                    .create();

            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint("http://shadywallpaperservice.apphb.com")
                    //.setConverter(new GsonConverter(gson))
                    .build();
            service = adapter.create(ShadyWallpaperService.class);

        }
        return service;
    }

}
