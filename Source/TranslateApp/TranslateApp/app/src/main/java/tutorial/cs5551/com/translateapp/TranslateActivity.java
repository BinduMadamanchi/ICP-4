package tutorial.cs5551.com.translateapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TranslateActivity extends AppCompatActivity {

    String API_URL = "https://api.fullcontact.com/v2/person.json?";
    String API_KEY = "b29103a702edd6a";
    String sourceText;
    Spinner mySpinner2;
    Spinner mySpinner3;
    TextView outputTextView;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        outputTextView = (TextView) findViewById(R.id.result_Text);

        mySpinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(TranslateActivity.this,android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Spinner2));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(myAdapter2);

        mySpinner3 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(TranslateActivity.this,android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Spinner3));
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner3.setAdapter(myAdapter3);


    }
    public void logOut(View v)
    {
        Intent i = new Intent(TranslateActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public void translateText(View v) {
        TextView sourceTextView = (TextView) findViewById(R.id.inputText);
        String fromLang = mySpinner2.getSelectedItem().toString();
        String toLang = mySpinner3.getSelectedItem().toString();
        String fromLanguage;
        String toLanguage;
        switch (fromLang)
        {
            case "Telugu":
                fromLanguage ="te";break;
            case "English":
                fromLanguage ="en";break;
            case "Hindi":
                fromLanguage ="hi";break;
            case "Malayalam":
                fromLanguage ="ml";break;
            case "French":
                fromLanguage ="fr";break;
            case "Urdu":
                fromLanguage ="ja";break;
            case "Japanese":
                fromLanguage ="ja";break;
            case "German":
                fromLanguage ="de";break;
            case "Tamil":
                fromLanguage ="ta";break;
            default:
                fromLanguage ="en";break;

        }
        switch (toLang)
        {
            case "Telugu":
                toLanguage ="te";break;
            case "English":
                toLanguage ="en";break;
            case "Hindi":
                toLanguage ="hi";break;
            case "Malayalam":
                toLanguage ="ml";break;
            case "French":
                toLanguage ="fr";break;
            case "Urdu":
                toLanguage ="ja";break;
            case "Japanese":
                toLanguage ="ja";break;
            case "German":
                toLanguage ="de";break;
            case "Tamil":
                toLanguage ="ta";break;
            default:
                toLanguage ="en";break;
        }

        sourceText = sourceTextView.getText().toString();

        String getURL = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
                "key=trnsl.1.1.20151023T145251Z.bf1ca7097253ff7e." +
                "c0b0a88bea31ba51f72504cc0cc42cf891ed90d2&text=" + sourceText +"&" +
                "lang="+fromLanguage+"-"+toLanguage+"&[format=plain]&[options=1]&[callback=set]";//The API service URL
        final String response1 = "";
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(getURL)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();
                    try {
                        jsonResult = new JSONObject(result);
                        JSONArray convertedTextArray = jsonResult.getJSONArray("text");
                        final String convertedText = convertedTextArray.get(0).toString();
                        Log.d("okHttp", jsonResult.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                outputTextView.setText(convertedText);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception ex) {
            outputTextView.setText(ex.getMessage());

        }

    }
}
