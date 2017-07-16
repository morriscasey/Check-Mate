package com.checkmate.checkmate;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.checkmate.checkmate.FakeLocations.FakeLocations;
import com.checkmate.checkmate.FakeLocations.Models.Location;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class CheckInFragment extends Fragment {
    TextView mins;
    TextView sec;
    String mUser;
    FakeLocations mFakeLocations;
    Button mSendButton;
    Location mCurrentLocation;
    CountdownTask mCountdownTask;
    Timer mTimer;
    public boolean mIsCanceled;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkin, container, false);
        MainActivity mainActivity = (MainActivity)getActivity();
        sec = (TextView)view.findViewById(R.id.seconds);

        sec.setText(Integer.toString(10));
        mIsCanceled = false;

        if(mainActivity.mUser == "bob"){
            mUser = "Mary";
        }else {
            mUser = "Bob";
        }

        mSendButton = (Button)view.findViewById(R.id.send);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountdownTask.cancel(true);
                mTimer.cancel();
                repeat();
                sec.setText(Integer.toString(10));

            }
        });

        mFakeLocations = new FakeLocations(mUser);
        mFakeLocations.createLocationDate();

        repeat();




        return view;
    }

    // Create a method that counts down to 00:00
    public class CountdownTask extends AsyncTask<Integer, Integer, Integer>{
        Integer countDown;
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            sec.setText(Integer.toString(values[0]));
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            sendLocation();
            Log.i("TAG", countDown.toString());

        }

        @Override
        protected Integer doInBackground(Integer... params) {
            countDown = params[0];
            for(int i = countDown; i >= 0; i--){

                publishProgress(countDown);
                android.os.SystemClock.sleep(1000);
                countDown--;

            }
            return countDown;
        }
    }



    // Grab GPS Location
    public void sendLocation(){
        Log.i("TAG", "Clicked");

        if(!mFakeLocations.mLocationsStack.isEmpty()){
            mCurrentLocation = mFakeLocations.mLocationsStack.pop();

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            String url ="https://checkmate-mobiuswheel.c9users.io/POST_location.php";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            Log.i("TAG","Response is: "+ response);
                            Toast.makeText(getActivity(),"Sent Location", Toast.LENGTH_SHORT);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("TAG", "OOPS: " + error);
                    Toast.makeText(getActivity(),"That didn't work!", Toast.LENGTH_SHORT);
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<String,String>();
                    params.put("lat", mCurrentLocation.latitude + "");
                    params.put("long", mCurrentLocation.latitude + "");
                    return params;
                }
            };
            Log.i("TAG", stringRequest.toString());
            // Add the request to the RequestQueue.
            queue.add(stringRequest);


        }
    }

    private void repeat() {
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate( new TimerTask() {
            public void run() {

                try{

                    mCountdownTask = new CountdownTask();
                    mCountdownTask.execute(10);

                }
                catch (Exception e) {
                    // TODO: handle exception
                }

            }
        }, 0, 10000);
    }

    // Send location to emergency contacts if button not pressed
}
