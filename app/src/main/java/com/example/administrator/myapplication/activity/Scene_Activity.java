package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scene_Activity extends AppCompatActivity {

    public JSONObject object;
    //JsonUtil jsonUtil;
    Handler handler = new Handler();
    final String url = "http://193.112.144.229/travel/?spot/show";
    String id;
    String r;

    ArrayList<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        final TextView textView = (TextView) findViewById(R.id.scene_detail_name);
        final TextView textView1 = (TextView) findViewById(R.id.scene_detail_desc);
        // final ImageView imageView = (ImageView) findViewById(R.id.strategy_img);
        //final MyImageView myImageView = (MyImageView) findViewById(R.id.scene_detail_img);

        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");
        Log.i("id",id);
        // new JsonUtil(url, id).Post_Data();

        //获取后台数据
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String urlPath = url;
                URL url = null;
                InputStream inputStream=null;
                try {
                    url = new URL(urlPath);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", id);
                    String param = jsonObject.toString();
                    Log.i("参数", param);
                    HttpURLConnection con = con = (HttpURLConnection) url.openConnection(); //开启连接
                    con.setConnectTimeout(5000);
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setRequestMethod("POST");
                    con.setRequestProperty("ser-Agent", "Fiddler");
                    con.setRequestProperty("Content-Type", "application/json");
                    //写输出流，将要转的参数写入流里
                    OutputStream os = con.getOutputStream();
                    os.write(param.getBytes()); //字符串写进二进流
                    os.flush();
                    os.close();

                    inputStream = con.getInputStream();

                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder res = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        res.append(line);
                    }
                    r = res.toString();
                    Log.i("返回数据", r);
                    //final Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    //  final ImageThread img = new ImageThread(urlPath, handler, imageView);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (r != null){
                                Log.i("数据",r);
                                try {
                                    JSONObject jsonObject1 = new JSONObject(r);
                                    String resultCode = jsonObject1.getString("success");
                                    if (resultCode.equals("true")){
                                        JSONArray resultJsonArray = jsonObject1.getJSONArray("data");
                                        for (int i=0; i<resultJsonArray.length(); i++){
                                            object = resultJsonArray.getJSONObject(i);

                                            Map<String, Object> map = new HashMap<String, Object>();

                                            try {
                                                String name = object.getString("name");
                                                String id = object.getString("id");
                                                String brief = object.getString("brief");
                                                String picture = "http://193.112.144.229/travel" + object.getString("picture");
                                                map.put("name", name);
                                                map.put("id", id);
                                                map.put("brief",brief);
                                                map.put("picture", picture);
                                                lists.add(map);

                                                Log.i("返回数据",name);
                                                Log.i("返回数据",id);
                                                Log.i("返回数据",picture);

                                                textView.setText(name);
                                                textView1.setText(brief);

                                                //  Bitmap bitmap = getHttpBitmap(picture);

                                                //myImageView.setImageURL(picture);


                                            }catch (JSONException e){
                                                e.printStackTrace();
                                            }
                                        }

                                    }

                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }

//                            MyListViewAdapter=new MyListViewAdapter(context,handler,students); //传递关键参数MainActivity上下文对象context，MainActivity主线程的handler对象,处理好的List<Student>对象
//                            listView.setAdapter(MyListViewAdapter);
                        }
                    });
                    //jsonJX(r);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
