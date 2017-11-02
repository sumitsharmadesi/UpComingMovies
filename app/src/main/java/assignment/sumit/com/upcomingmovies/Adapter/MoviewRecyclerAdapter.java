package assignment.sumit.com.upcomingmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.ArrayList;

import assignment.sumit.com.upcomingmovies.Activity.Details;
import assignment.sumit.com.upcomingmovies.Model.Movies;
import assignment.sumit.com.upcomingmovies.R;

public class MoviewRecyclerAdapter extends RecyclerView.Adapter<MoviewRecyclerAdapter.MoviewHolder>{
    public ArrayList<Movies> movieslist = new ArrayList<Movies>();
    private Context mcontext;
    private ImageLoader mloader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions;

    public MoviewRecyclerAdapter(Context con, ArrayList<Movies> list){
        mcontext = con;
        movieslist = list;
        mOptions = new DisplayImageOptions.Builder().cacheInMemory(true) // default
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler())
                .build();

    }
    @Override
    public MoviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movielistitem, parent, false);
        final MoviewHolder mh = new MoviewHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(MoviewHolder holder, final int position) {
        holder.date.setText(movieslist.get(position).release_date);
        if(movieslist.get(position).adult){
            holder.certificate.setText("A");
        }else {
            holder.certificate.setText("U/A");
        }
        holder.moviename.setText(movieslist.get(position).title);
        mloader.displayImage("https://image.tmdb.org/t/p/w500"+movieslist.get(position).poster_path,holder.thumbnail,mOptions);
        holder.moviecardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, Details.class);
                intent.putExtra("class",movieslist.get(position));
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieslist.size();
    }

    public class MoviewHolder extends RecyclerView.ViewHolder{
        TextView date,certificate,moviename;
        ImageView thumbnail;
        CardView moviecardview;
        public MoviewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            certificate = itemView.findViewById(R.id.certificate);
            moviename = itemView.findViewById(R.id.moviename);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            moviecardview = itemView.findViewById(R.id.moviecardview);
        }
    }
}
