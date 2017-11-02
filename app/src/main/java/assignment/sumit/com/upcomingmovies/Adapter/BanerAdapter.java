package assignment.sumit.com.upcomingmovies.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import assignment.sumit.com.upcomingmovies.R;


public class BanerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    String[] imagesarr;
    private ImageLoader mloader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions;
    public BanerAdapter(Context con,String[] images){
        context = con;
        imagesarr = images;
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
    public int getCount() {
        if(imagesarr.length>5){
            return 5;
        }else {
            return imagesarr.length;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.baneritem, container, false);
        ImageView banerimage = itemView.findViewById(R.id.banerimage);
        mloader.displayImage("https://image.tmdb.org/t/p/w500"+imagesarr[position],banerimage,mOptions);
        container.addView(itemView);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
