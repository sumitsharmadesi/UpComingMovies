package assignment.sumit.com.upcomingmovies.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import assignment.sumit.com.upcomingmovies.Adapter.MoviewRecyclerAdapter;
import assignment.sumit.com.upcomingmovies.Common.JsonRequest;
import assignment.sumit.com.upcomingmovies.Model.Movies;
import assignment.sumit.com.upcomingmovies.R;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    RecyclerView movieslist;
    public static final String API_KEY = "0e724475f05adc065b247de90bd331a0";
    ArrayList<Movies> movielist = new ArrayList<Movies>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        if(mToolbar!=null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Upcoming Movies");
            mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        }
        movieslist = findViewById(R.id.movieslist);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        movieslist.setLayoutManager(mLayoutManager);
        GetMoview task = new GetMoview();
        task.execute();
    }
    public class GetMoview extends AsyncTask<String,Void,JSONObject>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                return JsonRequest.postData1("https://api.themoviedb.org/3/movie/upcoming", API_KEY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            Log.e("result",""+jsonObject);
            dialog.cancel();
            try {
                JSONArray results = jsonObject.getJSONArray("results");
                movielist = new ArrayList<Movies>();
                for(int i=0;i<results.length();i++){
                    JSONObject obj = results.getJSONObject(i);
                    int id = obj.getInt("id");
                    String title = obj.getString("title");
                    String release_date = obj.getString("release_date");
                    boolean adult = obj.getBoolean("adult");
                    String poster_path = obj.getString("poster_path");
                    String overview = obj.getString("overview");
                    double vote_average = obj.getDouble("vote_average");
                    Movies movies = new Movies(id,title,release_date,adult,poster_path,overview,vote_average);
                    movielist.add(movies);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MoviewRecyclerAdapter adp = new MoviewRecyclerAdapter(MainActivity.this,movielist);
            movieslist.setAdapter(adp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main:
                Intent intent = new Intent(MainActivity.this,DeveloperBy.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
