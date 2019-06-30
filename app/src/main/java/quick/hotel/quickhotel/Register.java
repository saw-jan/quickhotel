package quick.hotel.quickhotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
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

public class Register extends AppCompatActivity {

    EditText fullname,ncont,npass,cpass;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullname = findViewById(R.id.fullName);
        ncont = findViewById(R.id.mobileNum);
        npass = findViewById(R.id.newpassword);
        cpass = findViewById(R.id.confirm);

        progressDialog = new ProgressDialog(Register.this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    public void register(View v){
        String name = fullname.getText().toString();
        final String contact = ncont.getText().toString();
        String newpass = npass.getText().toString();
        String confirm = cpass.getText().toString();
        if(Functions.IsEmptyText(name) || Functions.IsEmptyText(contact) || Functions.IsEmptyText(newpass) || Functions.IsEmptyText(confirm)){
            Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT).show();
        }
        else {
            if(Functions.isNumeric(contact) && contact.length()==10){
                if(newpass.equals(confirm)) {
                    progressDialog.setMessage("Checking...");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, Path.registerUser,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String result = response.trim();
                                    if (result.equals("inserted")) {
                                        progressDialog.dismiss();
                                        //call login.
                                        //Toast.makeText(Register.this, "User has been registered", Toast.LENGTH_LONG).show();
                                        Intent call = new Intent(Register.this, Login.class);
                                        startActivity(call);
                                        finish();
                                    }
                                    else if(result.equals("exist")){
                                        ncont.setTextColor(0xFFFF0000);
                                        progressDialog.dismiss();
                                        Toast.makeText(Register.this, "Contact already registered", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Register.this, "Server Error", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "Connection Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Name", fullname.getText().toString());
                            params.put("Contact", ncont.getText().toString());
                            params.put("Password", npass.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                    requestQueue.add(request);
                }else{
                    Toast.makeText(Register.this, "Password didn't match", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            }
        }

        //Intent intent =  new Intent(Register.this, Login.class);
        //startActivity(intent);
        //finish();
    }
    //on back  pressed
    public void onBackPressed() {
        Intent backIntent = new Intent(Register.this, Login.class);
        startActivity(backIntent);
        finish();
    }
}
