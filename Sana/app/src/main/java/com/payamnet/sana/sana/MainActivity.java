package com.payamnet.sana.sana;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.payamnet.sana.sana.constants.Constants;
import com.payamnet.sana.sana.constants.URLS;
import com.payamnet.sana.sana.constants.XMLTemplates;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity {


    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);



        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/YEKAN.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build());

        searchText = (EditText) findViewById(R.id.autotext);

        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() <= searchText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width() + searchText.getLeft() + searchText.getPaddingLeft()) {
                        String usernameStr = searchText.getText().toString();
                        new CallSearchWebService().execute(usernameStr);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private class CallSearchWebService extends AsyncTask <String, Void, String> {

        @Override
        protected void onPostExecute(String s) { // TODO: 8/2/18 These messages need to be changed. Both need to be hardcoded.
            if (s != null) {
                Toast.makeText(MainActivity.this, "s result is: " + s, Toast.LENGTH_LONG).show();
                Log.i(Constants.TAG, "onPostExecute: s result is: " + s);
            } else {
                Toast.makeText(MainActivity.this, "s result was null. something went wrong", Toast.LENGTH_LONG).show();
                Log.i(Constants.TAG, "onPostExecute: Sth went wrong.");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(URLS.SEARCH_URL);

                String requestXML = XMLTemplates.SEARCH_REQUEST_XML.replace("<V_Value>string</V_Value>", "<V_Value>" + params[0] + "</V_Value>");
                // TODO: 7/18/18 Maybe do sth about these? :-""" :-?

                StringEntity entity = new StringEntity(requestXML, "UTF-8");
                httpPost.setEntity(entity);
                httpPost.setHeader("Content-type", "text/xml; charset=UTF-8");


                CloseableHttpResponse response = client.execute(httpPost);

                if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
                    HttpEntity responseEntity = response.getEntity();
                    Document dom = null;

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    try {
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        dom = builder.parse(responseEntity.getContent());
                    } catch (ParserConfigurationException | IllegalStateException | SAXException e) {
                        e.printStackTrace();
                    }
                    if (dom != null) {
                        NodeList fSearchResult = dom.getElementsByTagName("FSearchResult"); // TODO: 7/18/18 Maybe change this to sth hardcoded? :-?
                        if (fSearchResult != null) {
                            if (fSearchResult.getLength() > 0 && fSearchResult.item(0).hasChildNodes()) {
//                                Log.i(Constants.TAG, "doInBackground: children number: " + fSearchResult.item(0).getChildNodes().getLength());
//                                Log.i(Constants.TAG, "doInBackground: grand child: " + fSearchResult.item(0).getFirstChild().getChildNodes().getLength());
                                return fSearchResult.item(0).getFirstChild().getTextContent();
                            }
                        }
                    }
                }
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
