package assignment.sumit.com.upcomingmovies.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import assignment.sumit.com.upcomingmovies.Adapter.BanerAdapter;
import assignment.sumit.com.upcomingmovies.Common.JsonRequest;
import assignment.sumit.com.upcomingmovies.Model.Movies;
import assignment.sumit.com.upcomingmovies.R;
public class Details extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private Toolbar mToolbar;
    public static final String API_KEY = "0e724475f05adc065b247de90bd331a0";
    Movies moviedetails;
    private TextView mtitle,moverview;
    private RatingBar mratingBar;
    RelativeLayout rellayHomeParent;
    ViewPager intro_images;
    LinearLayout ViewPagerIndicater;
    int currentViewPager;
    ImageView dots[];
    BanerAdapter mAdapter;
    private Handler mHandler;
    public static final int DELAY = 2000;
    String[] imagesarray;
    Runnable mRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if(currentViewPager==mAdapter.getCount()-1) {
                intro_images.setCurrentItem(currentViewPager);
                currentViewPager=0;
                mHandler.postDelayed(mRunnable, DELAY);
            }
            else {
                intro_images.setCurrentItem(currentViewPager++);
                mHandler.postDelayed(mRunnable, DELAY);
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        moviedetails = (Movies) getIntent().getSerializableExtra("class");
        mToolbar = findViewById(R.id.toolbar);
        if(mToolbar!=null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(""+moviedetails.title);
            mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
            mToolbar.setNavigationIcon(R.drawable.back_icon);
        }
        mtitle = findViewById(R.id.title);
        moverview = findViewById(R.id.overview);
        mratingBar = findViewById(R.id.ratingBar);
        mtitle.setText(moviedetails.title);
        moverview.setText(moviedetails.overview);
        mratingBar.setRating(Float.parseFloat(""+moviedetails.vote_average)/2);
        mHandler=new Handler();
        ViewPagerIndicater= (LinearLayout) findViewById(R.id.ViewPagerIndicater);
        intro_images = (ViewPager) findViewById(R.id.pager_introduction);
        intro_images.addOnPageChangeListener(this);
        rellayHomeParent= (RelativeLayout) findViewById(R.id.relalyHomePage);
        GetImages task = new GetImages();
        task.execute(""+moviedetails.id);
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
    private void setUiPageViewController(String[] arr) {
        if(arr.length>5) {
            dots = new ImageView[5];
            for (int i = 0; i < 5; i++) {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 0, 4, 0);
                ViewPagerIndicater.addView(dots[i], params);
            }
            mHandler.postDelayed(mRunnable, DELAY);
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        }else {
            dots = new ImageView[arr.length];
            for (int i = 0; i < arr.length; i++) {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 0, 4, 0);
                ViewPagerIndicater.addView(dots[i], params);
            }
            mHandler.postDelayed(mRunnable, DELAY);
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        try {
            currentViewPager = position;
            Log.e("currentViewPager", "" + currentViewPager);
            for (int i = 0; i < dots.length; i++) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            }

            dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public class GetImages extends AsyncTask<String,Void,JSONObject>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Details.this);
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                return JsonRequest.postData1("https://api.themoviedb.org/3/movie/"+strings[0]+"/images", API_KEY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            Log.e("dbjd",""+jsonObject);
            dialog.cancel();
            try {
                JSONArray backdrops = jsonObject.getJSONArray("backdrops");
                imagesarray = new String[backdrops.length()];
                for(int i=0;i<backdrops.length();i++){
                    JSONObject obj = backdrops.getJSONObject(i);
                    String file_path = obj.getString("file_path");
                    imagesarray[i] = file_path;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mAdapter = new BanerAdapter(Details.this,imagesarray);
            intro_images.setCurrentItem(0);
            intro_images.setAdapter(mAdapter);
            setUiPageViewController(imagesarray);
        }
    }
}
