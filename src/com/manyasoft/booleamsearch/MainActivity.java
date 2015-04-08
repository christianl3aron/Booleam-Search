package com.manyasoft.booleamsearch;




import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private DrawerLayout caDrawerLayout;
    private ListView caDrawerList;
    private ActionBarDrawerToggle caDrawerToggle;

    private String[] caOptionTitles;
    private Tools tools;
    private AdminSQLiteOpenHelper admin_ma;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		admin_ma = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
		tools = new Tools(getApplicationContext());
		
		try {
			if(admin_ma.checkTablaPublications()==0){
				admin_ma.insertPublications();
			}else if(admin_ma.checkTablaPublications()==12){
				admin_ma.insertPublications2();
			}
		} catch (Exception e) {
			tools.showLongMessage("Erro en llenar "+ e.getMessage());
		}
		
		 //mTitle = mDrawerTitle = getTitle();
       caOptionTitles = getResources().getStringArray(R.array.ca_layouts_array);
       caDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_ca);
       caDrawerList = (ListView) findViewById(R.id.left_drawer_ca);

       // set a custom shadow that overlays the main content when the drawer opens
       caDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
       // set up the drawer's list view with items and click listener
       caDrawerList.setAdapter(new ArrayAdapter<String>(this,
               R.layout.drawer_list_item, caOptionTitles));
       caDrawerList.setOnItemClickListener(new DrawerItemClickListener());

       // enable ActionBar app icon to behave as action to toggle nav drawer
       getActionBar().setDisplayHomeAsUpEnabled(true);
       getActionBar().setHomeButtonEnabled(true);

       // ActionBarDrawerToggle ties together the the proper interactions
       // between the sliding drawer and the action bar app icon
       caDrawerToggle = new ActionBarDrawerToggle(
               this,                  /* host Activity */
               caDrawerLayout,         /* DrawerLayout object */
               R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
               R.string.drawer_open,  /* "open drawer" description for accessibility */
               R.string.drawer_close  /* "close drawer" description for accessibility */
               ) {
           public void onDrawerClosed(View view) {
               //getActionBar().setTitle(mTitle);
               invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
           }

           public void onDrawerOpened(View drawerView) {
               //getActionBar().setTitle(mDrawerTitle);
               invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
           }
       };
       caDrawerLayout.setDrawerListener(caDrawerToggle);

       if (savedInstanceState == null) {
       	Fragment fragment = new MainFragment();
           Bundle args = new Bundle();
           fragment.setArguments(args);

           FragmentManager fragmentManager = getFragmentManager();
           fragmentManager.beginTransaction().replace(R.id.content_frame_ca, fragment).commit();
       }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (caDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	 private class DrawerItemClickListener implements ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	 
	             switch (position){
	             case 0:{ /*Intent i=new Intent(CatalogActivity.this,AddOrderActivity.class);
	             		  startActivity(i);
	             		 overridePendingTransition(R.anim.setanim_in, R.anim.setanim_out);*/}break;
	            /* case 1:{ Intent i=new Intent(CatalogActivity.this,HistoryActivity.class);
				   		  startActivity(i);
				   		  overridePendingTransition(R.anim.setanim_in, R.anim.setanim_out);}break;
	             case 2:{ Intent i=new Intent(CatalogActivity.this,MyprofileActivity.class);
	             		startActivity(i);
	             		overridePendingTransition(R.anim.setanim_in, R.anim.setanim_out);}break;
	             case 3:{ Intent i=new Intent(CatalogActivity.this,AddClientActivity.class);
	             		startActivity(i);
	             		overridePendingTransition(R.anim.setanim_in, R.anim.setanim_out);}break;
	             case 4:{ Intent i=new Intent(CatalogActivity.this,ClientListActivity.class);
		   		  		startActivity(i);
	   		  			overridePendingTransition(R.anim.setanim_in, R.anim.setanim_out);}break;*/
	             }
	             
	             caDrawerList.setItemChecked(position, true);
	             caDrawerLayout.closeDrawer(caDrawerList);
	             finish();
	        }
	    }
	 
	 @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        caDrawerToggle.syncState();
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        // Pass any configuration change to the drawer toggls
	        caDrawerToggle.onConfigurationChanged(newConfig);
	    }
    
}
