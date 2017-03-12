package com.example.yah.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.System.in;

public class WeatherForecast extends AppCompatActivity {
    ProgressBar proBar=null;
    private String ACTIVITY_NAME="WeatherForecast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        proBar = (ProgressBar) findViewById(R.id.progBar);
        proBar.setVisibility(View.VISIBLE);

        ForecastQuery forecast = new ForecastQuery();
        forecast.execute();
    }



    private class ForecastQuery extends AsyncTask<String, Integer, String>{
        private String minT;
        private String maxT;
        private String currentT;
        private Bitmap bm;
        private String iconName;

        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

        private InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            return conn.getInputStream();
        }

        @Override
        protected String doInBackground(String... params) {
            String allTogether="";

            try {
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(downloadUrl(urlString), null);
                    parser.nextTag();

                    while (parser.next() != XmlPullParser.END_DOCUMENT) {
                        if (parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }
                        String name = parser.getName();
                        if (name.equals("temperature")) {
                            currentT = parser.getAttributeValue(null, "value");
                            publishProgress(25);
                            minT = parser.getAttributeValue(null, "min");
                            publishProgress(50);
                            maxT = parser.getAttributeValue(null, "max");
                            publishProgress(75);
                        } else if (name.equals("weather")) {
                            iconName = parser.getAttributeValue(null, "icon");
                        }
                    }

                    allTogether = currentT + " " + minT + " " + maxT + " " + iconName;


                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";

            Log.i(ACTIVITY_NAME, "Searching for FileName: " + iconName + ".png");


            if(fileExistance(iconName + ".png")) {

                FileInputStream fis = null;
                try {
                    File file = new File(getFilesDir(), iconName + ".png");
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bm = BitmapFactory.decodeStream(fis);
                Log.i(ACTIVITY_NAME, "Found " + iconName + ".png locally." );
            }else {
                bm  = HttpUtils.getImage(imageUrl);
                FileOutputStream outputStream = null;
                try {
                    outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bm.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                Log.i(ACTIVITY_NAME, "Found " + iconName + ".png by downloading.");
                new File(getFilesDir(), iconName + ".png");
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            publishProgress(100);

            return allTogether;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            proBar.setVisibility(View.VISIBLE);
            proBar.setProgress(value[0]);
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }

        @Override
        protected void onPostExecute(String result){
            TextView minText = (TextView) findViewById(R.id.minTemp);
            TextView maxText = (TextView) findViewById(R.id.maxTemp);
            TextView currentText = (TextView) findViewById(R.id.currentTemp);

            ImageView weatherImage = (ImageView) findViewById(R.id.weatherImage);
            weatherImage.setImageBitmap(bm);

            minText.setText("Min: " + minT);
            maxText.setText("Max: " + maxT);
            currentText.setText("Current Temp: " + currentT);
            proBar.setVisibility(View.INVISIBLE);

        }



    }



}
