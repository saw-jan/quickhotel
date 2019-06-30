package quick.hotel.quickhotel;

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
import android.widget.Button;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BestPrice.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BestPrice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BestPrice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BestPrice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BestPrice.
     */
    // TODO: Rename and change types and number of parameters
    public static BestPrice newInstance(String param1, String param2) {
        BestPrice fragment = new BestPrice();
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
        final LinearLayout content = new LinearLayout(getContext());
        LinearLayout.LayoutParams pc = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        content.setLayoutParams(pc);
        content.setOrientation(LinearLayout.HORIZONTAL);

        StringRequest request = new StringRequest(Request.Method.POST, Path.bestHotels,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String result = response.trim();
                        if(!result.equals("invalid")) {
                            //Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject Obj = new JSONObject(response);
                                JSONArray hArray = Obj.getJSONArray("bestprice");
                                for (int a = 0; a < hArray.length(); a++) {
                                    JSONObject data = hArray.getJSONObject(a);
                                    final String hid = data.getString("ID");
                                    String hname = data.getString("Name");
                                    String rate = data.getString("SRate");
                                    String photo = data.getString("Photo");

                                    /*
                                    * <ImageView
                            android:layout_width="100dp"
                            android:layout_height="100dp"
                            android:background="@drawable/ktm" />

                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:layout_gravity="center_horizontal"
                            android:layout_marginTop="5dp"
                            android:text="Kathmandu" />*/

                                    final LinearLayout l1 = new LinearLayout(getContext());
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    l1.setLayoutParams(lp);
                                    l1.setOrientation(LinearLayout.VERTICAL);
                                    l1.setPadding(10,10,10,10);
                                    l1.setClickable(true);
                                    //l1.setPaddingRelative(0,0,0,0);
                                    content.addView(l1);

                                    final ImageView hp = new ImageView(getContext());
                                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(150, 150);
                                    hp.setLayoutParams(p);
                                    if(photo.length()>0){
                                        String url =Path.media+photo;
                                        Picasso.get().load(url).resize(100,100).into(hp);

                                    }else {
                                        hp.setImageResource(R.drawable.ic_default);
                                    }
                                    l1.addView(hp);

                                    final TextView name = new TextView(getContext());
                                    LinearLayout.LayoutParams np = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    np.gravity = Gravity.CENTER_HORIZONTAL;
                                    name.setLayoutParams(np);
                                    name.setText(hname);
                                    name.setTextSize(12);
                                    l1.addView(name);
                                    final TextView price = new TextView(getContext());
                                    LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    pp.gravity = Gravity.CENTER_HORIZONTAL;
                                    price.setLayoutParams(pp);
                                    price.setText("Rs. "+rate);
                                    price.setTextSize(12);
                                    price.setTextColor(Color.parseColor("#009688"));
                                    l1.addView(price);
                                    final TextView id = new TextView(getContext());
                                    LinearLayout.LayoutParams i = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    id.setLayoutParams(i);
                                    id.setText(hid);
                                    id.setVisibility(View.INVISIBLE);
                                    l1.addView(id);

                                        l1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //Toast.makeText(getActivity(), hid, Toast.LENGTH_SHORT).show();
                                                Intent single = new Intent(getActivity(), Single.class);
                                                single.putExtra("hID",hid);
                                                startActivity(single);
                                                getActivity().finish();
                                            }
                                        });
                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else{
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
                Toast.makeText(getActivity(), "Connection Error"+error, Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

        return content;
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
