package quick.hotel.quickhotel;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class search extends FragmentActivity implements ResultFragment.OnFragmentInteractionListener{


    EditText srch;
    TextView loc;
    public static int passID=0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        srch = findViewById(R.id.new_search);
        loc = findViewById(R.id.locname);
        loc.setText("'"+Functions.capsFirst(Dash.srchText)+"'");

        srch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (srch.getRight() - srch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(!srch.getText().toString().trim().equals("")) {
                            Dash.srchText = srch.getText().toString();
                            Intent searchActivity = new Intent(search.this, search.class);
                            startActivity(searchActivity);
                            finish();
                        }else{

                        }
                        //Toast.makeText(getBaseContext(),"Search",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });
/*
        progressDialog = new ProgressDialog(search.this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        progressDialog.setMessage("Checking...");
        progressDialog.show();

        final String srchTxt = Dash.srchText;
        LinearLayout wrapper = new LinearLayout(this);
        LinearLayout.LayoutParams wr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        wrapper.setLayoutParams(wr);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.setBackgroundColor(Color.parseColor("#dddddd"));

        //search bar
        LinearLayout srchBar = new LinearLayout(this);
        LinearLayout.LayoutParams sb = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,48);
        srchBar.setLayoutParams(sb);
        srchBar.setOrientation(LinearLayout.VERTICAL);
        srchBar.setBackgroundColor(Color.parseColor("#009688"));
        //dark 005A52
        final EditText srch = new EditText(this);
        LinearLayout.LayoutParams s = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        s.setMargins(15, 15, 15, 15);
        srch.setLayoutParams(s);
        srch.setPaddingRelative(15,0,15,0);
        srch.setBackgroundResource(R.drawable.textbox_fill);
        //srch.setText("Hotel Muna");
        srch.setTextColor(Color.parseColor("#444"));
        srch.setHint("Location");
        srch.setCompoundDrawablePadding(15);
        srch.setFocusable(true);
        srch.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_search,0);
        srch.setTextSize(14);
        srchBar.addView(srch);

        //Texts
        LinearLayout txt = new LinearLayout(this);
        LinearLayout.LayoutParams tx = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        tx.setMargins(0, 0, 0, 15);
        txt.setLayoutParams(tx);
        txt.setOrientation(LinearLayout.HORIZONTAL);

        final TextView pre = new TextView(this);
        LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pre.setLayoutParams(tp);
        pre.setPadding(15,15,15,15);
        pre.setPaddingRelative(0,0,0,0);
        pre.setBackgroundResource(R.drawable.textbox_fill);
        pre.setText("Hotels in");
        pre.setTextColor(Color.parseColor("#ddd"));
        final TextView post = new TextView(this);
        LinearLayout.LayoutParams tpo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tpo.setMargins(15, 15, 15, 15);
        post.setLayoutParams(tpo);
        post.setPadding(15,15,15,15);
        post.setPaddingRelative(4,0,0,0);
        post.setBackgroundResource(R.drawable.textbox_fill);
        post.setText("'"+srchTxt+"'");
        post.setTextColor(Color.parseColor("#ddd"));
        txt.addView(pre);
        txt.addView(post);

        final ScrollView scroll = new ScrollView(search.this);
        LinearLayout.LayoutParams sv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        scroll.setLayoutParams(sv);

        final LinearLayout content = new LinearLayout(search.this);
        LinearLayout.LayoutParams pc = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        content.setLayoutParams(pc);
        content.setOrientation(LinearLayout.VERTICAL);

        srch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (srch.getRight() - srch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        content.removeAllViews();
                        Toast.makeText(getBaseContext(),"Search",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });


        //dummy
        /*
        //image view
        final ImageView h_pp = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(60, 60);
        h_pp.setLayoutParams(p2);

        h_pp.setImageResource(R.drawable.ic_default);
        //linear view
        final LinearLayout l1_1 = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p3.setMargins(10, 0, 0, 0);
        l1_1.setLayoutParams(p3);
        l1_1.setOrientation(LinearLayout.VERTICAL);
        l1_1.setPadding(10, 10, 10, 10);
        //Text view
        final TextView tv1 = new TextView(getApplicationContext());
        LinearLayout.LayoutParams p4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv1.setLayoutParams(p4);
        tv1.setText("Hotel Muna");
        tv1.setTextSize(16);
        tv1.setTypeface(Typeface.DEFAULT_BOLD);
        //Text view
        final TextView tv2 = new TextView(getApplicationContext());
        LinearLayout.LayoutParams p5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv2.setLayoutParams(p5);
        tv2.setText("Pokhara, Lakeside");
        tv2.setTextSize(10);
        //Text view
        final TextView tv3 = new TextView(getApplicationContext());
        LinearLayout.LayoutParams p6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv3.setLayoutParams(p6);
        tv3.setText("9812345678");
        tv3.setTextSize(12);

        l1_1.addView(tv1);
        l1_1.addView(tv2);
        l1_1.addView(tv3);

        //linear view
        final LinearLayout l1_2 = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams p7 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p7.setMargins(0, 40, 0, 0);
        p7.gravity = Gravity.RIGHT;
        l1_2.setLayoutParams(p7);
        l1_2.setOrientation(LinearLayout.VERTICAL);
        //Text view
        final TextView tvr1 = new TextView(getApplicationContext());
        LinearLayout.LayoutParams p8 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvr1.setLayoutParams(p8);
        tvr1.setText("Starting Rate");
        tvr1.setTextSize(12);
        tvr1.setTypeface(Typeface.DEFAULT_BOLD);
        //Text view
        final TextView tvr2 = new TextView(getApplicationContext());
        LinearLayout.LayoutParams p9 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvr2.setLayoutParams(p9);
        tvr2.setText("RS. 200");
        tvr2.setTypeface(Typeface.DEFAULT_BOLD);
        //tvr2.setTextColor(Color.argb());

        l1_2.addView(tvr1);
        l1_2.addView(tvr2);

        l1.addView(h_pp);
        l1.addView(l1_1);
        l1.addView(l1_2);
        contain.addView(l1);*/

        //main
        /*StringRequest request = new StringRequest(Request.Method.POST, Path.searched,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String result = response.trim();
                        if(!result.equals("invalid")) {
                            progressDialog.dismiss();
                            //Toast.makeText(search.this, "1", Toast.LENGTH_LONG).show();
                            try {
                                JSONObject Obj = new JSONObject(response);
                                JSONArray hArray = Obj.getJSONArray("hotels");
                                for (int a = 0; a < hArray.length(); a++) {
                                    JSONObject data = hArray.getJSONObject(a);
                                    final String[] id = {data.getString("ID")};
                                    String name = data.getString("Name");
                                    String contact = data.getString("Contact");
                                    String city = data.getString("City");
                                    String street = data.getString("Street");
                                    String srate = data.getString("SRate");
                                    String photo = data.getString("Photo");
                                    Toast.makeText(search.this, name, Toast.LENGTH_LONG).show();

                                    //linear view
                                    final LinearLayout l1 = new LinearLayout(getApplicationContext());
                                    LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    p1.setMargins(5, 5, 5, 5);
                                    l1.setLayoutParams(p1);
                                    l1.setOrientation(LinearLayout.HORIZONTAL);
                                    l1.setPadding(10, 10, 10, 10);
                                    l1.setBackgroundColor(Color.WHITE);
                                    l1.setClickable(true);
                                    l1.setId(Integer.parseInt(id[0]));
                                    //image view
                                    final ImageView h_pp = new ImageView(getApplicationContext());
                                    LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(110, 110);
                                    h_pp.setLayoutParams(p2);
                                    if(photo.length()>0){
                                        String url =Path.media+photo;
                                        Picasso.get().load(url).resize(60,60).into(h_pp);

                                    }else {
                                        h_pp.setImageResource(R.drawable.ic_default);
                                    }
                                    //linear view
                                    final LinearLayout l1_1 = new LinearLayout(getApplicationContext());
                                    LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    p3.setMargins(10, 0, 0, 0);
                                    l1_1.setLayoutParams(p3);
                                    l1_1.setOrientation(LinearLayout.VERTICAL);
                                    l1_1.setPadding(10, 10, 10, 10);
                                    //Text view
                                    final TextView tv1 = new TextView(getApplicationContext());
                                    LinearLayout.LayoutParams p4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tv1.setLayoutParams(p4);
                                    tv1.setText(name);
                                    tv1.setTextSize(16);
                                    tv1.setTypeface(Typeface.DEFAULT_BOLD);
                                    //Text view
                                    final TextView tv2 = new TextView(getApplicationContext());
                                    LinearLayout.LayoutParams p5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tv2.setLayoutParams(p5);
                                    tv2.setText(city+", "+street);
                                    tv2.setTextSize(10);
                                    //Text view
                                    final TextView tv3 = new TextView(getApplicationContext());
                                    LinearLayout.LayoutParams p6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tv3.setLayoutParams(p6);
                                    tv3.setText(contact);
                                    tv3.setTextSize(12);

                                    l1_1.addView(tv1);
                                    l1_1.addView(tv2);
                                    l1_1.addView(tv3);
                                    //linear view
                                    final LinearLayout l1_2 = new LinearLayout(getApplicationContext());
                                    LinearLayout.LayoutParams p7 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    p7.setMargins(0, 40, 0, 0);
                                    p7.gravity = Gravity.RIGHT;
                                    l1_2.setLayoutParams(p7);
                                    l1_2.setOrientation(LinearLayout.VERTICAL);
                                    //Text view
                                    final TextView tvr1 = new TextView(getApplicationContext());
                                    LinearLayout.LayoutParams p8 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tvr1.setLayoutParams(p8);
                                    tvr1.setText("Starting Rate");
                                    tvr1.setTextSize(12);
                                    tvr1.setTypeface(Typeface.DEFAULT_BOLD);
                                    //Text view;
                                    final TextView tvr2 = new TextView(getApplicationContext());
                                    LinearLayout.LayoutParams p9 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tvr2.setLayoutParams(p9);
                                    tvr2.setText("RS. "+srate);
                                    tvr2.setTypeface(Typeface.DEFAULT_BOLD);
                                    //tvr2.setTextColor(Color.argb());

                                    l1_2.addView(tvr1);
                                    l1_2.addView(tvr2);
                                    l1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            //pass = v.getId();
                                            Intent single = new Intent(search.this, Single.class);
                                            startActivity(single);
                                            finish();
                                            //Toast.makeText(search.this, ""+v.getId(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    l1.addView(h_pp);
                                    l1.addView(l1);
                                    l1.addView(l1_2);
                                    content.addView(l1);

                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(search.this, "Cannot find Hotels here", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(search.this, "Connection Error"+error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Search", capsFirst(srchTxt));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        scroll.addView(content);
        wrapper.addView(srchBar);
        wrapper.addView(txt);
        wrapper.addView(scroll);
        body.addView(wrapper);
        /*final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Path.searched, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response != null) {
                    try {
                        JSONArray h_array = response.getJSONArray("hotels");
                        for (int a = 0; a < h_array.length(); a++) {
                            JSONObject h_object = h_array.getJSONObject(a);
                            String f_name = h_object.getString("Name");
                            String f_img = h_object.getString("Image");
                            //linear child
                            final LinearLayout l2_1 = new LinearLayout(getApplicationContext());
                            LinearLayout.LayoutParams p5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                            l2_1.setLayoutParams(p5);
                            l2_1.setOrientation(LinearLayout.VERTICAL);
                            l2_1.setPadding(10, 10, 10, 10);
                            //image
                            final ImageView f_pp = new ImageView(getApplicationContext());
                            LinearLayout.LayoutParams p6 = new LinearLayout.LayoutParams(100, 100);
                            p6.gravity= Gravity.CENTER;
                            f_pp.setLayoutParams(p6);
                            if(f_img.length()>0){
                                String url =Path.media+f_img;
                                Picasso.get().load(url).resize(100,100).into(f_pp);

                            }else {
                                f_pp.setImageResource(R.drawable.ic_default);
                            }
                            //text
                           final TextView l_name = new TextView(getApplicationContext());
                            LinearLayout.LayoutParams p7 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            p7.gravity= Gravity.CENTER;
                            l_name.setLayoutParams(p7);
                            l_name.setText(f_name);
                            l_name.setTextSize(12);

                            l2_1.addView(f_pp);
                            l2_1.addView(l_name);
                            l2.addView(l2_1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "No detail", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
        requestQueue.add(jsonObjectRequest);
*/
    }

    /*protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag, fragment);
        fragmentTransaction.commit();
    }*/
    public void onBackPressed() {
        Intent back = new Intent(search.this, Dash.class);
        startActivity(back);
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
