package assignment.sumit.com.upcomingmovies.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import assignment.sumit.com.upcomingmovies.R;

/**
 * Created by Velociter on 02-11-2017.
 */

public class DeveloperBy extends AppCompatActivity{
    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developedby);
        mToolbar = findViewById(R.id.toolbar);
        if(mToolbar!=null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Developed By");
            mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
            mToolbar.setNavigationIcon(R.drawable.back_icon);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
