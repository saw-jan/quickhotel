package quick.hotel.quickhotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText contact;
    EditText password;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    public static final String PREFS = "prefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        contact = findViewById(R.id.lcontact);
        password = findViewById(R.id.lpassword);


        progressDialog = new ProgressDialog(Login.this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    //login
    public void logIn(View v){

        String cont = contact.getText().toString();
        final String pass = password.getText().toString();
        if(Functions.IsEmptyText(cont) || Functions.IsEmptyText(pass)){
            Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT).show();
        }
        else {
            if(Functions.isNumeric(cont) && cont.length()==10){
                progressDialog.setMessage("Checking...");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, Path.fetchUser,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String result = response.trim();
                                if(!result.equals("invalid")) {
                                    try {
                                        JSONObject jObj = new JSONObject(response);
                                        JSONArray dataArray = jObj.getJSONArray("user");
                                        for (int a = 0; a < dataArray.length(); a++) {
                                            JSONObject data = dataArray.getJSONObject(a);
                                            String contact = data.getString("Contact");
                                            String name = data.getString("Name");

                                            //sessions
                                            SharedPreferences prefs = getSharedPreferences(PREFS,0);
                                            SharedPreferences.Editor edit = prefs.edit();
                                            edit.putString("username",name);
                                            edit.putString("contact",contact);
                                            edit.apply();
                                            Toast.makeText(Login.this, "Welcome "+name, Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                            //call dash.
                                            Intent call = new Intent(Login.this,Dash.class);
                                            startActivity(call);
                                            finish();
                                        }
                                    }
                                    catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, "Invalid Username or Password. Please Try Again !", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Contact",contact.getText().toString().trim());
                        params.put("Password",password.getText().toString().trim());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                requestQueue.add(request);
            }else{
                Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //register
    public void linkRegister(View v){
        Intent register = new Intent(Login.this, Register.class);
        startActivity(register);
        finish();
    }
    boolean exit = false;
    public void onBackPressed() {
            if (exit) {
                System.exit(1);
            }
            exit = true;
            //double back pressed exit
            Toast.makeText(Login.this, "Press again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
}
