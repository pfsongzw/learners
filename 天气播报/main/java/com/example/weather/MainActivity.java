package com.example.weather;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.AsyncTask;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private TextView weather,temperature,winddirection,windpower,humidity,cityname;
    private Spinner citySpinner;
    private View rootview;
    private ImageView imageView;
    InputStream inputStream;
    EditText searchEditText;
    DatabaseHelper dbHelper;
    HashMap<String,Integer>s_map=new HashMap<>(),b_map=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        citySpinner = findViewById(R.id.citySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.city_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String City = parent.getItemAtPosition(position).toString();
                // 请求天气数据
                Log.d("song","选择");
                updateWeatherFromSpinner(City);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 可选的回调
            }
        });
         searchEditText = findViewById(R.id.searchEditText);
         searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(searchEditText.hasFocus()) {
                    String searchedKey = s.toString();
                    String result = dbHelper.getCityCode(searchedKey);
                    if (result != null) {
                        updateWeatherForCity(result);
                    } else {
                    }
                }
            }
        });
    }
    private void updateWeatherFromSpinner(String city) {
        // 在这里，我们只处理通过spinner选择的城市，并避免searchEditText的更改造成冲突
       String Citycode = dbHelper.getCityCode(city);
        if (Citycode != null) {
            updateWeatherForCity(Citycode);
        }
    }
    public void init() throws IOException//初始化
    {
        dbHelper = new DatabaseHelper(this);
        initDatabaseData();
        s_map.put("晴",R.drawable.sunny);
        s_map.put("多云",R.drawable.cloud);
        s_map.put("阴",R.drawable.somber);
        s_map.put("霾",R.drawable.haze);
        s_map.put("小雨",R.drawable.s_rain);
        s_map.put("中雨",R.drawable.m_rain);
        s_map.put("大雨",R.drawable.h_rain);
        s_map.put("暴雨",R.drawable.b_rain);
        s_map.put("雪",R.drawable.snow);
        s_map.put("雾",R.drawable.fog);
        b_map.put("晴", R.drawable.b_sunny);
        b_map.put("阴", R.drawable.back_overcast_sky);
        b_map.put("多云", R.drawable.back_cloud);
        b_map.put("小雨", R.drawable.back_rain);
        b_map.put("中雨", R.drawable.back_rain);
        b_map.put("大雨", R.drawable.back_rain);
        b_map.put("小雪", R.drawable.back_snow);
        b_map.put("中雪", R.drawable.back_snow);
        b_map.put("大雪", R.drawable.back_snow);
        b_map.put("雾", R.drawable.back_fog);
        b_map.put("霾", R.drawable.back_haze);
        rootview=findViewById(android.R.id.content);
        cityname=findViewById(R.id.City);
        weather=findViewById(R.id.weatherSummary);
        imageView=findViewById(R.id.weatherIcon);
        temperature=findViewById(R.id.temperature);
        winddirection=findViewById(R.id.windDirection);
        windpower=findViewById(R.id.windSpeed);
        humidity=findViewById(R.id.humidity);
    }


    // 创建数据库并填充数据（假设您已在assets中有data.csv文件）
    public void initDatabaseData() throws IOException {
        dbHelper.getWritableDatabase(); // 确保数据库已创建
        InputStream inputStream = null;
        BufferedReader reader;
        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        boolean isInitialized = prefs.getBoolean("DatabaseInitialized", false);
        if (!isInitialized || !dbHelper.isDataInitialized()) { // 检查标志和实际数据
            // 数据初始化过程
            // 假设我们已在Assets中有data.csv文件
            try {
                inputStream = this.getAssets().open("data.csv");
                 reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String name = parts[0].trim();
                        String code = parts[1].trim();
                        dbHelper.addCity(name, code);
                    }
                }
                // 设置SharedPreferences，表示数据库已初始化
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("DatabaseInitialized", true);
                editor.apply();
            } catch (IOException e) {
                Log.e("MainActivity", "Error initializing database", e);
            } finally {
                try {
                    if (inputStream != null) inputStream.close();
                } catch (IOException e) {
                    Log.e("MainActivity", "Error closing inputStream", e);
                }
            }
        }
    }
    private void updateWeatherForCity(String city) {
        // 这里你将执行网络请求，并在获取数据后更新UI。
        Log.d("song","更新");
            new FetchWeatherTask().execute(city);

    }
    private class FetchWeatherTask extends AsyncTask<String, Void, WeatherResponse> {
        private OkHttpClient client = new OkHttpClient();
        private Gson gson = new Gson();
        @Override
        protected WeatherResponse doInBackground(String... params) {
            String apiKey = "9010b07eb243007e2817777c98e41b38";
            String cityCode = params[0]; // 获取调用execute()时传入的参数
            String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=" +apiKey+ "&city=" + cityCode;
            // 构造HTTP请求
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                // 执行请求并获取响应
                Response response = client.newCall(request).execute();
                // 检查响应是否成功且包含内容
                Log.d("song","请求");
                if (response.isSuccessful() && response.body() != null) {
                    // 将响应体转换为字符串格式
                    String jsonData = response.body().string();
                    Log.d("song","请求成功"+jsonData);
                    // 使用Gson解析字符串，转换为WeatherResponse对象
                    WeatherResponse weatherResponse = gson.fromJson(jsonData, WeatherResponse.class);
                    // 返回解析后的WeatherResponse对象
                    return weatherResponse;
                }
            } catch (Exception e) {
                Log.e("WeatherTask", "Error fetching weather data", e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(WeatherResponse weatherResponse) {
            super.onPostExecute(weatherResponse);
            // 检查weatherResponse是否非空且包含天气数据
            if (weatherResponse != null && weatherResponse.lives != null && !weatherResponse.lives.isEmpty()) {
                LiveWeather liveWeather = weatherResponse.lives.get(0);
                updateWeatherDisplay(liveWeather);
            } else {
            }
        }
    }

    public void updateWeatherDisplay(LiveWeather liveWeather) {
        String cor_weather=liveWeather.weather;
        rootview.setBackground(getDrawable(b_map.get(cor_weather)));
        weather.setText(cor_weather);
        temperature.setText("温度: " + liveWeather.temperature);
        winddirection.setText("风向："+liveWeather.winddirection);
        windpower.setText("风力："+liveWeather.windpower);
        humidity.setText("湿度："+liveWeather.humidity);
        imageView.setImageResource(s_map.get(cor_weather));
        cityname.setText(liveWeather.city);
        Log.d("song","更新ui");
    }

    public class LiveWeather {
        String city;
        String weather;
        String temperature;
        String winddirection;
        String windpower;
        String humidity;
    }
    public class WeatherResponse {
        List<LiveWeather> lives;
    }
}
