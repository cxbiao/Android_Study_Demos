package cn.bryan.designdemo;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by bryan on 2015/7/12.
 */
public class DesignActivity extends AppCompatActivity {


    FloatingActionButton fabBtn;
    CoordinatorLayout rootLayout;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    TabLayout tabLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        getCategory();
        initToolbar();
        initInstances();
    }
    //添加多渠道打包信息
    public void getCategory(){
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String catetory = applicationInfo.metaData.getString("category");
            System.out.println(catetory);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initToolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.setNavigationIcon(R.drawable.ic_plus);
       getSupportActionBar().setHomeButtonEnabled(true);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void  initInstances(){

//       tabLayout= (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("tab1"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab2"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab3"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab4"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab5"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab6"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab7"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab8"));


       //tabLayout.setupWithViewPager();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("Design Library");

        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open ,R.string.close);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

        navigationView= (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navItem1:
                        Snackbar.make(navigationView, "item1", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.navItem2:
                        Snackbar.make(navigationView, "item2", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.navItem3:
                        Snackbar.make(navigationView, "item3", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.navItem4:
                        Snackbar.make(navigationView, "item4", Snackbar.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);
        fabBtn= (FloatingActionButton) findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //总是在底部显示
                Snackbar.make(v, "这是一个snackbar...", Snackbar.LENGTH_SHORT)
                        .setAction("点击", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("action;click");
                            }
                        }).show();
            }
        });

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
