package quick.hotel.quickhotel;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Dash extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,BestPrice.OnFragmentInteractionListener{

    public static final String PREFS = "prefs";
    EditText search,person;
    Button chkin;
    Button chkout;
    PickDate datePicker;
    private int flag = 0;
    public static String srchText="";
    LinearLayout ktmd,pkhr,lkh,bhk,tansen;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        SharedPreferences prefs = getSharedPreferences(PREFS,0);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking...");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        search = findViewById(R.id.search);
        person = findViewById(R.id.p_count);
        chkin = findViewById(R.id.checkin);
        chkout = findViewById(R.id.checkout);

        ktmd = findViewById(R.id.ktm);
        pkhr = findViewById(R.id.pkr);
        lkh = findViewById(R.id.lkh);
        bhk = findViewById(R.id.bhk);
        tansen = findViewById(R.id.tan);
        ktmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Dash.this, "Kathmandu", Toast.LENGTH_SHORT).show();
                srchText = "Kathmandu";
                Intent searchActivity = new Intent(Dash.this, search.class);
                startActivity(searchActivity);
                finish();
            }
        });
        pkhr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Dash.this, "Pokhara", Toast.LENGTH_SHORT).show();
                srchText = "Pokhara";
                Intent searchActivity = new Intent(Dash.this, search.class);
                startActivity(searchActivity);
                finish();
            }
        });
        lkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Dash.this, "Lekhnath", Toast.LENGTH_SHORT).show();
                srchText = "Lekhnath";
                Intent searchActivity = new Intent(Dash.this, search.class);
                startActivity(searchActivity);
                finish();
            }
        });
        bhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Dash.this, "Bhaktapur", Toast.LENGTH_SHORT).show();
                srchText = "Bhaktapur";
                Intent searchActivity = new Intent(Dash.this, search.class);
                startActivity(searchActivity);
                finish();
            }
        });
        tansen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Dash.this, "Tansen", Toast.LENGTH_SHORT).show();
                srchText = "Tansen";
                Intent searchActivity = new Intent(Dash.this, search.class);
                startActivity(searchActivity);
                finish();
            }
        });

        datePicker = new PickDate();

        chkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
                flag=1;
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });
        chkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_SHORT).show();
                flag=2;
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });
        person.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //checking invalid characters
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String in = person.getText().toString().trim();
                if(!in.equals("")){
                if(Functions.isNumeric(in)){
                    if(Integer.valueOf(in)>0){

                    }else {
                        Toast.makeText(Dash.this,"Invalid Character",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Dash.this,"Invalid Character",Toast.LENGTH_SHORT).show();
                    person.setText(in.substring(0,in.length()-1));
                }
                }else {
                    person.setHint("0");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //search click event
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (search.getRight() - search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        String text = search.getText().toString().trim();
                        if(!text.equals("")){
                            srchText = search.getText().toString();
                            SharedPreferences prefs = getSharedPreferences(PREFS,0);
                            SharedPreferences.Editor edit = prefs.edit();

                            String chi = Functions.removeWords(chkin.getText().toString().trim(),"check-in");
                            String cho = Functions.removeWords(chkout.getText().toString().trim(),"check-out");
                            //Toast.makeText(getApplicationContext(),chi+", "+cho,Toast.LENGTH_LONG).show();
                            edit.putString("chkin",chi.trim());
                            edit.putString("chkout",cho.trim());
                            edit.putString("person",person.getText().toString());
                            edit.apply();
                            Intent searchActivity = new Intent(Dash.this, search.class);
                            startActivity(searchActivity);
                            finish();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
//        progressDialog.show();

    }
    //toolbar setting
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                //Toast.makeText(this,"Profile",Toast.LENGTH_SHORT).show();
                Intent history = new Intent(this,BookHistory.class);
                startActivity(history);
                finish();
                return true;
            case R.id.logout:
                SharedPreferences prefs =getSharedPreferences(PREFS,this.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                finish();
                Intent calLogin = new Intent(this,Login.class);
                startActivity(calLogin);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //on back pressed
    boolean exit = false;
    public void onBackPressed() {
        if (exit) {
            System.exit(1);
        }
        exit = true;
        //double back pressed exit
        Toast.makeText(Dash.this, "Press again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exit = false;
            }
        }, 2000);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String checkedin = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());
        if (flag == 1) {
            chkin.setText("check-in\n"+checkedin);
        } else if (flag == 2) {
            String chi = Functions.removeWords(chkin.getText().toString().trim(),"check-in");
            String day = chi.split(",",3)[1];
            String d1 = day.trim().substring(day.trim().length()-2,day.trim().length());
            String day2 = checkedin.split(",",3)[1];
            String d2 = day2.trim().substring(day2.trim().length()-2,day2.trim().length());
            //String a = String.valueOf(Integer.parseInt(d2));
            if((Integer.parseInt(d2)-Integer.parseInt(d1))>0){
                chkout.setText("check-out\n"+checkedin);

                //Toast.makeText(Dash.this,  , Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Dash.this, "Invalid Check-out Date", Toast.LENGTH_SHORT).show();
                chkout.setText("check-out\n");
            }
        }
    }
    public boolean isNetAccess() {
        try {
            ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getApplicationContext()).getSystemService(Context.CONNECTIVITY_SERVICE);
            return Objects.requireNonNull(cm).getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"No Internet Connection.",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
