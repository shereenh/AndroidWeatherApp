package asu.com.tryout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import asu.com.tryout.AutoComplete.PlacesAutoCompleteAdapter;
import asu.com.tryout.GsonClasses.TopLevel;
import asu.com.tryout.RecyclerStuff.WeatherAdapter;

public class ServiceActivity extends AppCompatActivity {

    EditText editCity;
    Button makePretty;
    ImageButton getWeather;
    String city,answer,prettyAnswer;
    TextView textView;
    TopLevel topLevel;
    DecimalFormat format;
    WeatherLog OneWeatherLog;
    RelativeLayout mainLayout;

    AutoCompleteTextView autocompleteView;
    private PlacesAutoCompleteAdapter mAdapter;
    HandlerThread mHandlerThread;
    Handler mThreadHandler;
    private static String TAG = ServiceActivity.class.getSimpleName();

    private List<WeatherLog> weatherLog = new ArrayList<>();
    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;



    public ServiceActivity(){

        if (mThreadHandler == null) {
            // Initialize and start the HandlerThread
            // which is basically a Thread with a Looper
            // attached (hence a MessageQueue)
            mHandlerThread = new HandlerThread(TAG, android.os.Process.THREAD_PRIORITY_BACKGROUND);
            mHandlerThread.start();

            // Initialize the Handler
            mThreadHandler = new Handler(mHandlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        ArrayList<String> results = mAdapter.resultList;

                        if (results != null && results.size() > 0) {
                            System.out.println("1Thread: "+Thread.currentThread().getName());
                            try{
                                mAdapter.notifyDataSetChanged();}catch(Exception e){
                                System.out.println("Attention!!!");
                                e.printStackTrace();
                            }
                        }
                        else {
                            mAdapter.notifyDataSetInvalidated();
                        }
                    }
                }
            };
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        mainLayout = (RelativeLayout)findViewById(R.id.mainLinear);

        format = new DecimalFormat("##.00");

        editCity = findViewById(R.id.city);
        textView = findViewById(R.id.result);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        weatherAdapter = new WeatherAdapter(weatherLog);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager
                (getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(weatherAdapter);

        //prepareWeatherData();



        getWeather = findViewById(R.id.getWeather);
        getWeather.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                //city = editCity.getText().toString();
                city = autocompleteView.getText().toString();
                anotherHandle(city);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                //makePretty.setVisibility(View.VISIBLE);
            }
        });

//        makePretty = findViewById(R.id.makePretty);
//        makePretty.setVisibility(View.INVISIBLE);
//        makePretty.setOnClickListener(new android.view.View.OnClickListener() {
//            @Override
//            public void onClick(android.view.View v) {
//
//                doGson();
//                textView.setText(prettyAnswer);
//            }
//        });


        //requestHandle();


        //Autocomplete
        autocompleteView = (AutoCompleteTextView)findViewById(R.id.city_name);

        mAdapter = new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item);
        autocompleteView.setAdapter(mAdapter);

        //  autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));

        System.out.println("Thread: "+Thread.currentThread().getName());

        autocompleteView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Just so you know: "+s);
                final String value = s.toString();

                // Remove all callbacks and messages
                mThreadHandler.removeCallbacksAndMessages(null);

                // Now add a new one
                mThreadHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // Background thread
                        mAdapter.resultList = mAdapter.mPlaceAPI.autocomplete(value);

                        // Footer
                        if (mAdapter.resultList.size() > 0)
                            mAdapter.resultList.add("footer");

                        // Post to Main Thread
                        mThreadHandler.sendEmptyMessage(1);
                    }
                }, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // doAfterTextChanged();
            }
        });

        autocompleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autocompleteView.setText("");
                System.out.println("CLEARED!");
            }

        });

    }

    void doGson() {

        try {
            Gson gson = new GsonBuilder().create();
            topLevel = gson.fromJson(answer, TopLevel.class);
            System.out.println("Gson:"+topLevel);

        }catch(Exception e){
            e.printStackTrace();
        }

        int i;
        String w = topLevel.weather.getMain();
        if(w.contains("Cloud")){
            i=0;
        }else if(w.contains("Sun")){
            i=1;
        }else if(w.contains("Rain")){
            i=2;
        }else if(w.contains("Snow")){
            i=3;
        }
        else{ i=4;}


        OneWeatherLog = new WeatherLog(topLevel.weather.getMain(),round((topLevel.main.getTemp()*(9.0/5.0))-459.67),topLevel.main.getPressure(),
                topLevel.main.getHumidity(),topLevel.wind.getSpeed(),topLevel.sys.getCountry(),topLevel.getName(),i);

        weatherLog.add(OneWeatherLog);
        weatherAdapter.notifyDataSetChanged();


//        prettyAnswer =
//                "Weather: "+topLevel.weather.getMain()+" ("+topLevel.weather.getDescription1()+")"
//                +"\n\nTemperature: "+round((topLevel.main.getTemp()*(9.0/5.0))-459.67) +" F"
//                +"\nHumidity: "+topLevel.main.getHumidity()
//                +"\nPressure: "+topLevel.main.getPressure()
//                +"\nWind Speed: "+topLevel.wind.getSpeed()+"  Deg: "+topLevel.wind.getDeg()
//                +"\n\nCountry: "+topLevel.sys.getCountry()
//                +"\nSunrise: "+topLevel.sys.getSunrise()+"  Sunset: "+topLevel.sys.getSunset();

    }



    void anotherHandle(String thisCity){

        RequestQueue mRequestQueue;

// Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

        String url ="https://api.openweathermap.org/data/2.5/weather?apikey=c0ab963717343ca50ce1fb9be23b5fb2&q="+thisCity;

// Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        answer = response;
                        answer = answer.replaceAll("[\\[\\]]","");
                        System.out.println("Response is: "+answer);
                        if(!answer.isEmpty()){
                            doGson();
                        }else{
                            Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("That didn't work!");
                    }
                });

// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);


    }

//    void showText(){
//        answer = answer.replaceAll("[\\[\\]]","");
//        textView.setText("Results:\n"+ answer);
//    }


//    void requestHandle(){
//
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url ="http://api.openweathermap.org/data/2.5/weather?apikey=c0ab963717343ca50ce1fb9be23b5fb2&q=Bangalore";
//
//// Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        System.out.println("Response is: "+ response.substring(0,500));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("That didn't work!");
//            }
//        });
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
//    }

    public static double round(double value) {

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Get rid of our Place API Handlers
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacksAndMessages(null);
            mHandlerThread.quit();
        }
    }

    private void prepareWeatherData(){
        WeatherLog w = new WeatherLog("Rain",21.9,45.9,32.3,
                22.9,"US","jjj",1);
        weatherLog.add(w);

        w = new WeatherLog("Snow",21.9,45.9,32.3,
                22.9,"US","bhj",3);
        weatherLog.add(w);

        w = new WeatherLog("Sun",21.9,45.9,32.3,
                22.9,"US","ABC",2);
        weatherLog.add(w);
    }


}
