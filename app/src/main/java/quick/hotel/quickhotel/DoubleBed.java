package quick.hotel.quickhotel;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class DoubleBed extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    String passID = "";
    TextView user,cont,chki,chko,hname,rate;
    EditText pCount,cPass;
    PickDate datePicker;
    private int flag = 0;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    public static final String PREFS = "prefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_bed);
        final SharedPreferences prefs = getSharedPreferences(PREFS,0);
        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        passID = Single.pass;

        cPass = findViewById(R.id.pass);
        hname = findViewById(R.id.hnam);
        rate = findViewById(R.id.rate);
        user = findViewById(R.id.username);
        cont = findViewById(R.id.cont);
        user.setText(prefs.getString("username",""));
        cont.setText(prefs.getString("contact",""));

        chki = findViewById(R.id.chkin);
        chko = findViewById(R.id.chkout);
        if(prefs.getString("chkin","")!=""){
            chki.setText("Check-In\n"+prefs.getString("chkin",""));
        }
        if(prefs.getString("chkout","")!=""){
            chko.setText("Check-Out\n"+prefs.getString("chkout",""));
        }

        pCount = findViewById(R.id.peop);
        if(prefs.getString("person","")!=""){
            pCount.setText(prefs.getString("person",""));
        }

        datePicker = new PickDate();

        chki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
                flag=1;
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });
        chko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_SHORT).show();
                flag=2;
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        //Toast.makeText(SingleBed.this,passID,Toast.LENGTH_SHORT).show();
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
                                    String drate = data.getString("DRate");

                                    hname.setText(name);
                                    rate.setText(drate+" /2 person");

                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(DoubleBed.this, "Cannot load details", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DoubleBed.this, "Connection Error"+error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID", passID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void bookRoom(View v){

        final SharedPreferences prefs = getSharedPreferences(PREFS,0);

        final String customer = prefs.getString("username","");
        final String contact =prefs.getString("contact","");
        final String hotel = Single.pass;
        final String room = "Double";
        final String chkin = Functions.removeWords(chki.getText().toString().trim(),"Check-In");
        final String chkout = Functions.removeWords(chko.getText().toString().trim(),"Check-Out");
        final String person = pCount.getText().toString().trim();
        final String passW = cPass.getText().toString().trim();
        final String collec="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String suffix="";
        Random random = new Random();
        int len = 1+random.nextInt(4);
        for(int a=0;a<len;a++){
            char ch = collec.charAt(random.nextInt(26));
            suffix +=ch;
        }
        final String code = customer.substring(0,3)+contact.substring(2,10)+suffix;
        //Toast.makeText(SingleBed.this, code, Toast.LENGTH_LONG).show();
        if (!chkin.equals("") && !chkout.equals("")) {
            if (!person.equals("")){
        if(!passW.equals("")){

            progressDialog = new ProgressDialog(DoubleBed.this);
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, Path.book,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.trim();
                            if(!result.equals("invalid")) {
                                progressDialog.dismiss();
                                Toast.makeText(DoubleBed.this, "Booking Made", Toast.LENGTH_LONG).show();

                                prefs.edit().remove("chkin").apply();
                                prefs.edit().remove("chkout").apply();
                                prefs.edit().remove("person").apply();
                                //call dash.
                                Intent call = new Intent(DoubleBed.this,Dash.class);
                                startActivity(call);
                                finish();

                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(DoubleBed.this, "Invalid Password. Please Try Again !", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(DoubleBed.this, "Network Error", Toast.LENGTH_LONG).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Customer",customer);
                    params.put("Contact",contact);
                    params.put("Hotel",hotel);
                    params.put("Room",room);
                    params.put("Checkin",chkin);
                    params.put("Checkout",chkout);
                    params.put("Person",person);
                    params.put("Code",code);
                    params.put("Pass",passW);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(DoubleBed.this);
            requestQueue.add(request);
        }else {
            cPass.setFocusable(true);
            Toast.makeText(DoubleBed.this, "Password Required", Toast.LENGTH_LONG).show();
        }
            }else {
                cPass.setFocusable(true);
                Toast.makeText(DoubleBed.this, "People filed is empty", Toast.LENGTH_LONG).show();
            }
        }else {
            cPass.setFocusable(true);
            Toast.makeText(DoubleBed.this, "Please set Check-in/Check-out date", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String checkedin = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());
        if (flag == 1) {
            chki.setText("check-in\n"+checkedin);
        } else if (flag == 2) {
            chko.setText("check-out\n"+checkedin);
        }
    }
    public void onBackPressed() {
        Intent back = new Intent(DoubleBed.this, Dash.class);
        startActivity(back);
        finish();
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
