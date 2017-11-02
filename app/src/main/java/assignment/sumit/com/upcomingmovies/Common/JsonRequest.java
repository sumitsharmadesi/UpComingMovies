package assignment.sumit.com.upcomingmovies.Common;

import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonRequest
{
    public static JSONObject postData(String url, JSONObject obj) {
        JSONObject obj1 = null;
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 30*1000);
        HttpClient httpclient = new DefaultHttpClient(myParams );
        String json=obj.toString();
        Log.e("params",""+obj);
        try {
            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");
            StringEntity se = new StringEntity(obj.toString());
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());
            Log.e("response",""+temp);
            obj1 = new JSONObject(temp);
        }  catch (Exception e) {
            try {
                Log.e("excf",""+e);
                obj1 = new JSONObject();
                obj1.put("Error", ""+e);
            }catch (Exception e1){

            }
        }
        return obj1;
    }
    public static JSONObject postData1(String url,String param) {
        JSONObject obj1 = null;
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 45*1000);
        HttpClient httpclient = new DefaultHttpClient(myParams );
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("api_key", param));
            String paramsString = URLEncodedUtils.format(nameValuePairs, "UTF-8");
            HttpGet httppost = new HttpGet(url.toString()+ "?" + paramsString);
            httppost.setHeader("Content-type", "application/json");
            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());
            obj1 = new JSONObject(temp);

        }  catch (Exception e) {
            try {
                obj1 = new JSONObject();
                obj1.put("Error", ""+e);
            }catch (Exception e1){

            }
        }
        return obj1;
    }
    public static String postData2(String url, JSONObject obj) {
        JSONObject obj1 = null;
        String temp = "";
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 45*1000);
        HttpClient httpclient = new DefaultHttpClient(myParams );
        String json=obj.toString();
        try {
            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");
            StringEntity se = new StringEntity(obj.toString());
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            temp = EntityUtils.toString(response.getEntity());
        }  catch (Exception e) {

        }
        return temp;
    }
    public static String baseEncode(String a){
        String result = "";
        try{
            byte[] data1 = a.getBytes("UTF-8");
            result = Base64.encodeToString(data1, Base64.NO_WRAP).toString();
        }catch (Exception e){

        }
        return result;
    }
    public static String baseDecode(String a){
        String result = "";
        try{
            byte[] data = Base64.decode(a, Base64.NO_WRAP);
            result = new String(data, "UTF-8");
        }catch (Exception e){

        }
        return result;
    }
    }

