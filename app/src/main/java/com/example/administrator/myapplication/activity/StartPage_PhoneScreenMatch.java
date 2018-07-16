package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

//import java.util.logging.Handler;

/**
 * Created by ASUS on 2018/6/10.
 */

public class StartPage_PhoneScreenMatch extends Activity {
    private TextView show;
    private Button get;
    private Button my_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_phonescreenmatch);
        handler.sendEmptyMessageDelayed(0,2000);



        //get = (Button) findViewById(R.id.get_btn);
        //get.setOnClickListener((View.OnClickListener) this);

        show = (TextView) findViewById(R.id.show_tv);
        show.setText(getScreenParams());
        //my_button = (Button)findViewById(R.id.my_button);
        //my_button.setText( "Next" );
       // my_button.setOnClickListener((View.OnClickListener) this);
    }

    private Handler handler=new Handler() {
        public void handleMessage(Message msg){
            getHome();
            super.handleMessage(msg);
        }

    };
        public void getHome(){
            Intent intent=new Intent(StartPage_PhoneScreenMatch.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        public void flush() {

        }


        public void close() throws SecurityException {

        }




    public void onClick(View v) {
        show.setText(getScreenParams());
        Intent intent = new Intent(StartPage_PhoneScreenMatch.this,
                MainActivity.class);
        startActivity(intent);
    }



    public String getScreenParams() {
        DisplayMetrics dm = new DisplayMetrics();
//        dm = getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int heightPixels = dm.heightPixels;
        int widthPixels = dm.widthPixels;
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        int densityDpi = dm.densityDpi;
        float density = dm.density;
        float scaledDensity = dm.scaledDensity;
        float heightDP = heightPixels / density;
        float widthDP = widthPixels / density;
        String str = "heightPixels: " + heightPixels + "px";
        str += "\nwidthPixels: " + widthPixels + "px";
        str += "\nxdpi: " + xdpi + "dpi";
        str += "\nydpi: " + ydpi + "dpi";
        str += "\ndensityDpi: " + densityDpi + "dpi";
        str += "\ndensity: " + density;
        str += "\nscaledDensity: " + scaledDensity;
        str += "\nheightDP: " + heightDP + "dp";
        str += "\nwidthDP: " + widthDP + "dp";
        return str;
    }

}
