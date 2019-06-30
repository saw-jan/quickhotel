package quick.hotel.quickhotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Single extends AppCompatActivity {

    public static String pass="";
    ImageView cov;
    TextView hname,hadd,hcon,hdesc,hfac,single,doub;
    LinearLayout sng,dbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        final String hID = getIntent().getExtras().getString("hID");
        //Toast.makeText(Single.this,hID,Toast.LENGTH_SHORT).show();


        cov = findViewById(R.id.cover);
        hname = findViewById(R.id.h_name);
        hadd = findViewById(R.id.h_add);
        hcon = findViewById(R.id.h_cont);
        hdesc = findViewById(R.id.h_desc);
        hfac = findViewById(R.id.h_fac);
        single = findViewById(R.id.sRate);
        doub = findViewById(R.id.dRate);

        sng = findViewById(R.id.rsingle);
        dbl = findViewById(R.id.rdouble);
        sng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Single.this, "Single", Toast.LENGTH_SHORT).show();
                pass = hID;
                Intent sing = new Intent(Single.this, SingleBed.class);
                startActivity(sing);
                finish();
            }
        });
        dbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Single.this, "Double", Toast.LENGTH_SHORT).show();
                pass = hID;
                Intent doub = new Intent(Single.this, DoubleBed.class);
                startActivity(doub);
                finish();
            }
        });

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Path.hsingle,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String result = response.trim();
                        if(!result.equals("invalid")) {
                            progressDialog.dismiss();
                            //Toast.makeText(search.this, "1", Toast.LENGTH_LONG).show();
                            try {
                                JSONObject Obj = new JSONObject(response);
                                JSONArray hArray = Obj.getJSONArray("hotel");
                                for (int a = 0; a < hArray.length(); a++) {
                                    JSONObject data = hArray.getJSONObject(a);
                                    int id = Integer.parseInt(data.getString("ID"));
                                    String name = data.getString("Name");
                                    String contact = data.getString("Contact");
                                    String city = data.getString("City");
                                    String street = data.getString("Street");
                                    String desc = data.getString("Description");
                                    String fac = data.getString("Facilities");
                                    String srate = data.getString("SRate");
                                    String drate = data.getString("DRate");
                                    String photo = data.getString("Photo");


                                    if(photo.length()>0){
                                        String url =Path.media+photo;
                                        Picasso.get().load(url).fit().centerCrop().into(cov);

                                    }else {
                                        cov.setBackgroundResource(R.drawable.ic_default);
                                    }
                                    hname.setText(name);
                                    hadd.setText(city+", "+street);
                                    hcon.setText(contact);
                                    hdesc.setText(desc);
                                    hfac.setText(fac);
                                    single.setText("Rs. "+srate);
                                    doub.setText("Rs. "+drate);

                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(Single.this, "Cannot load details", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Single.this, "Connection Error"+error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID", hID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void onBackPressed() {
        Intent back = new Intent(Single.this, Dash.class);
        startActivity(back);
        finish();
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
