package assignment.sumit.com.upcomingmovies.Model;

import java.io.Serializable;

/**
 * Created by Velociter on 02-11-2017.
 */

public class Movies implements Serializable{
    public int id;
    public String title = "";
    public String release_date = "";
    public boolean adult ;
    public String poster_path = "";
    public String overview = "";
    public double vote_average;
    public Movies(int a,String b,String c,boolean d,String e,String f,double g){
        id = a;
        title = b;
        release_date = c;
        adult = d;
        poster_path = e;
        overview = f;
        vote_average = g;
    }
}
