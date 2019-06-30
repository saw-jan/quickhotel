package quick.hotel.quickhotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Welcome extends AppCompatActivity {
    private static int time_out = 4000;
    private ImageView logo;
    public static final String PREFS = "prefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final SharedPreferences prefs = getSharedPreferences(PREFS,0);

        logo = findViewById(R.id.logo);
        Animation fade_in = AnimationUtils.loadAnimation(this,R.anim.fadein);
        logo.startAnimation(fade_in);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                if(prefs.getString("username","")!=""){
                    Intent call = new Intent(Welcome.this,Dash.class);
                    startActivity(call);
                    finish();
                }else{
                    Intent loginIntent = new Intent(Welcome.this, Login.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        },time_out);
    }
}
