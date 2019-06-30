package quick.hotel.quickhotel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public static final String PREFS = "prefs";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final SharedPreferences prefs = getActivity().getSharedPreferences(PREFS,0);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Checking...");
        progressDialog.show();

        final ScrollView scroll = new ScrollView(getContext());
        LinearLayout.LayoutParams sv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        scroll.setLayoutParams(sv);

        final LinearLayout content = new LinearLayout(getContext());
        LinearLayout.LayoutParams pc = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        content.setLayoutParams(pc);
        content.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(content);
        final TextView alt = new TextView(getContext());
        LinearLayout.LayoutParams p8 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        alt.setLayoutParams(p8);
        alt.setPadding(15,15,15,15);
        alt.setText("History");
        alt.setTextSize(16);
        alt.setTextColor(Color.parseColor("#666666"));
        alt.setTypeface(Typeface.DEFAULT_BOLD);
        content.addView(alt);

        StringRequest request = new StringRequest(Request.Method.POST, Path.history,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String result = response.trim();
                        if(!result.equals("invalid")) {
                            progressDialog.dismiss();
                            //Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject Obj = new JSONObject(response);
                                JSONArray hArray = Obj.getJSONArray("history");
                                for (int a = 0; a < hArray.length(); a++) {
                                    JSONObject data = hArray.getJSONObject(a);
                                    final String bid = data.getString("ID");
                                    String hname = data.getString("Hotel");
                                    String city = data.getString("City");
                                    String street = data.getString("Street");
                                    String hcon = data.getString("Hcontact");
                                    String bdate = data.getString("BookDate");
                                    String btime = data.getString("BookTime");
                                    String person = data.getString("Person");
                                    String room = data.getString("Room");
                                    String code = data.getString("Code");
                                    String stat = data.getString("Status");

                                    //Toast.makeText(getContext(), "History", Toast.LENGTH_LONG).show();
                                    final LinearLayout l1 = new LinearLayout(getContext());
                                    LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    p1.setMargins(5, 5, 5, 5);
                                    l1.setLayoutParams(p1);
                                    l1.setOrientation(LinearLayout.VERTICAL);
                                    l1.setPadding(10, 10, 10, 10);
                                    l1.setBackgroundColor(Color.WHITE);
                                    content.addView(l1);

                                    final TextView tv1 = new TextView(getContext());
                                    LinearLayout.LayoutParams p4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tv1.setLayoutParams(p4);
                                    tv1.setText(hname);
                                    tv1.setTextSize(16);
                                    tv1.setTypeface(Typeface.DEFAULT_BOLD);
                                    //Text view
                                    final TextView tv2 = new TextView(getContext());
                                    LinearLayout.LayoutParams p5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tv2.setLayoutParams(p5);
                                    tv2.setText(city+", "+street);
                                    tv2.setTextSize(10);
                                    //Text view
                                    final TextView tv3 = new TextView(getContext());
                                    LinearLayout.LayoutParams p6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tv3.setLayoutParams(p6);
                                    tv3.setText(hcon);
                                    tv3.setTextSize(12);
                                    l1.addView(tv1);
                                    l1.addView(tv2);
                                    l1.addView(tv3);
                                    final LinearLayout l1_1 = new LinearLayout(getContext());
                                    LinearLayout.LayoutParams p1_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    p1_1.setMargins(0, 10, 0, 5);
                                    l1_1.setLayoutParams(p1_1);
                                    l1_1.setOrientation(LinearLayout.VERTICAL);
                                    //l1_1.setPadding(10, 10, 10, 10);
                                    l1.addView(l1_1);

                                    final TextView tv1_1 = new TextView(getContext());
                                    LinearLayout.LayoutParams p4_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    p4_1.setMargins(0, 0, 0, 0);
                                    tv1_1.setLayoutParams(p4_1);
                                    tv1_1.setText(bdate+", "+btime);
                                    tv1_1.setTextSize(16);
                                    tv1_1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_date,0,0,0);
                                    tv1_1.setCompoundDrawablePadding(5);
                                    //tv1_1.setTypeface(Typeface.DEFAULT_BOLD);
                                    //Text view
                                    final TextView tv2_1 = new TextView(getContext());
                                    LinearLayout.LayoutParams p5_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    //p5_1.setMargins(10, 0, 0, 0);
                                    //p5_1.setMarginStart(10);
                                    tv2_1.setLayoutParams(p5_1);
                                    tv2_1.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                    tv2_1.setText(person);
                                    tv2_1.setTextSize(16);
                                    tv2_1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_group,0,0,0);
                                    tv2_1.setCompoundDrawablePadding(5);
                                    //Text view
                                    final TextView tv3_1 = new TextView(getContext());
                                    LinearLayout.LayoutParams p6_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    //p6_1.setMargins(10, 0, 0, 0);
                                    //p6_1.setMarginStart(10);
                                    tv3_1.setLayoutParams(p6_1);
                                    tv3_1.setGravity(View.TEXT_ALIGNMENT_VIEW_START);
                                    tv3_1.setText(room);
                                    tv3_1.setTextSize(16);
                                    tv3_1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_bed_black,0,0,0);
                                    tv3_1.setCompoundDrawablePadding(5);
                                    l1_1.addView(tv1_1);
                                    l1_1.addView(tv2_1);
                                    l1_1.addView(tv3_1);

                                    final TextView cod = new TextView(getContext());
                                    LinearLayout.LayoutParams c = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    c.setMargins(0,10,0,0);
                                    cod.setLayoutParams(c);
                                    cod.setText(person);
                                    cod.setTextSize(14);
                                    cod.setText("CODE: ");
                                    l1.addView(cod);

                                    final TextView codx = new TextView(getContext());
                                    LinearLayout.LayoutParams cx = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    codx.setLayoutParams(cx);
                                    codx.setText(code);
                                    codx.setTextSize(18);
                                    codx.setTypeface(Typeface.DEFAULT_BOLD);
                                    l1.addView(codx);

                                    if(stat.equals("unseen")){
                                        final Button cancel = new Button(getContext());
                                        LinearLayout.LayoutParams cb = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        cancel.setLayoutParams(cb);
                                        cancel.setPadding(15,15,15,15);
                                        cancel.setText("Cancel Booking");
                                        cancel.setClickable(true);
                                        cancel.setBackgroundResource(R.drawable.btn_fill);
                                        l1.addView(cancel);

                                        final TextView id = new TextView(getContext());
                                        LinearLayout.LayoutParams i = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        id.setLayoutParams(i);
                                        id.setText(bid);
                                        id.setVisibility(View.INVISIBLE);
                                        l1.addView(id);

                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                StringRequest req = new StringRequest(Request.Method.POST, Path.cancelBook,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String res) {
                                                                String result = res.trim();
                                                                if(!result.equals("invalid")) {
                                                                    Toast.makeText(getActivity(), "Booking Cancelled", Toast.LENGTH_SHORT).show();
                                                                    Intent call = new Intent(getContext(),BookHistory.class);
                                                                    startActivity(call);
                                                                    getActivity().finish();

                                                                }else{
                                                                    Toast.makeText(getActivity(), "Cannot cancel booking now", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(getActivity(), "Connection Error"+error, Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("ID",id.getText().toString().trim());
                                                        return params;
                                                    }
                                                };
                                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                                requestQueue.add(req);
                                            }
                                        });
                                    }else{
                                        final Button app = new Button(getContext());
                                        LinearLayout.LayoutParams cb = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        app.setLayoutParams(cb);
                                        app.setPadding(15,15,15,15);
                                        app.setText("Approved");
                                        app.setClickable(false);
                                        l1.addView(app);
                                    }
                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            final TextView alt = new TextView(getContext());
                            LinearLayout.LayoutParams p8 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            alt.setLayoutParams(p8);
                            alt.setPadding(15,50,15,15);
                            alt.setText("No History Yet");
                            alt.setTextSize(16);
                            alt.setTextColor(Color.parseColor("#666666"));
                            alt.setTypeface(Typeface.DEFAULT_BOLD);
                            content.addView(alt);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Connection Error"+error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Name", prefs.getString("username",""));
                params.put("Contact", prefs.getString("contact",""));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
        return scroll;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
