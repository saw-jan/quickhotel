package quick.hotel.quickhotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.Objects;

public class BookHistory extends FragmentActivity implements HistoryFragment.OnFragmentInteractionListener{

    public static final String PREFS = "prefs";
    TextView name,cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_history);

        final SharedPreferences prefs = getSharedPreferences(PREFS,0);

        name =findViewById(R.id.name);
        cont = findViewById(R.id.cont);
        name.setText(prefs.getString("username",""));
        cont.setText(prefs.getString("contact",""));

    }
    public void onBackPressed() {
        Intent back = new Intent(BookHistory.this, Dash.class);
        startActivity(back);
        finish();
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
