package quick.hotel.quickhotel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        StringRequest request = new StringRequest(Request.Method.POST, Path.searched,
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
                                    int id = Integer.parseInt(data.getString("ID"));
                                    String name = data.getString("Name");
                                    String contact = data.getString("Contact");
                                    String city = data.getString("City");
                                    String street = data.getString("Street");
                                    String srate = data.getString("SRate");
                                    String photo = data.getString("Photo");
                                    //Toast.makeText(getContext(), name, Toast.LENGTH_LONG).show();

                                    //linear view
                                    final LinearLayout l1 = new LinearLayout(getContext());
                                    LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    p1.setMargins(5, 5, 5, 5);
                                    l1.setLayoutParams(p1);
                                    l1.setOrientation(LinearLayout.HORIZONTAL);
                                    l1.setPadding(10, 10, 10, 10);
                                    l1.setBackgroundColor(Color.WHITE);
                                    l1.setClickable(true);
                                    l1.setId(id);
                                    content.addView(l1);
                                    //image view
                                    final ImageView h_pp = new ImageView(getContext());
                                    LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(150, 150);
                                    h_pp.setLayoutParams(p2);
                                    if(photo.length()>0){
                                        String url =Path.media+photo;
                                        Picasso.get().load(url).resize(60,60).into(h_pp);

                                    }else {
                                        h_pp.setImageResource(R.drawable.ic_default);
                                    }
                                    l1.addView(h_pp);
                                    //linear view
                                    final LinearLayout l1_1 = new LinearLayout(getContext());
                                    LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    p3.setMargins(10, 0, 0, 0);
                                    l1_1.setLayoutParams(p3);
                                    l1_1.setOrientation(LinearLayout.VERTICAL);
                                    l1_1.setPadding(10, 10, 10, 10);
                                    l1.addView(l1_1);
                                    //Text view
                                    final TextView tv1 = new TextView(getContext());
                                    LinearLayout.LayoutParams p4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tv1.setLayoutParams(p4);
                                    tv1.setText(name);
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
                                    tv3.setText(contact);
                                    tv3.setTextSize(12);
                                    l1_1.addView(tv1);
                                    l1_1.addView(tv2);
                                    l1_1.addView(tv3);
                                    //linear view
                                    final LinearLayout l1_2 = new LinearLayout(getContext());
                                    LinearLayout.LayoutParams p7 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    p7.setMargins(0, 40, 0, 0);
                                    //p7.gravity = RelativeLayout.ALIGN_PARENT_END;
                                    l1_2.setLayoutParams(p7);
                                    l1_2.setGravity(View.TEXT_ALIGNMENT_VIEW_START);
                                    l1_2.setOrientation(LinearLayout.VERTICAL);
                                    l1.addView(l1_2);
                                    //Text view
                                    final TextView tvr1 = new TextView(getContext());
                                    LinearLayout.LayoutParams p8 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tvr1.setLayoutParams(p8);
                                    tvr1.setText("Starting Rate");
                                    tvr1.setTextSize(12);
                                    tvr1.setTypeface(Typeface.DEFAULT_BOLD);
                                    //Text view;
                                    final TextView tvr2 = new TextView(getContext());
                                    LinearLayout.LayoutParams p9 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tvr2.setLayoutParams(p9);
                                    tvr2.setText("RS. "+srate);
                                    tvr2.setTypeface(Typeface.DEFAULT_BOLD);
                                    tvr2.setTextColor(Color.parseColor("#009688"));
                                    l1_2.addView(tvr1);
                                    l1_2.addView(tvr2);
                                    l1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            String hID = Integer.toString(v.getId());
                                            Intent single = new Intent(getActivity(), Single.class);
                                            single.putExtra("hID",hID);
                                            startActivity(single);
                                            getActivity().finish();
                                            //Toast.makeText(search.this, ""+v.getId(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Cannot find Hotels here", Toast.LENGTH_LONG).show();
                            final TextView alt = new TextView(getContext());
                            LinearLayout.LayoutParams p8 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            alt.setLayoutParams(p8);
                            alt.setPadding(15,15,15,15);
                            alt.setText("No Hotels Found");
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
                params.put("Search", Functions.capsFirst(Dash.srchText));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

        return scroll;
        //return inflater.inflate(R.layout.fragment_result, container, false);
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
