package com.payamnet.sana.sana.view.page.login;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.payamnet.sana.sana.MainActivity;
import com.payamnet.sana.sana.R;
import com.payamnet.sana.sana.constants.Messages;
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
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by kosar on 7/10/18.
 */

public class LoginActivity extends FragmentActivity {

    private TextView userName;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/YEKAN.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build());

        userName = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);

        TextView login = (TextView) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = userName.getText().toString();
                String password_Str = password.getText().toString();
                new CallWebService().execute(usernameStr, password_Str);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    class CallWebService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Toast.makeText(LoginActivity.this, Messages.WELCOME, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, Messages.NOT_AUTHENTICATED, Toast.LENGTH_LONG).show();
                Log.i("debug", "onClick: not authenticated.");
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(URLS.LOGIN_URL);

                String requestXML = XMLTemplates.LOGIN_REQUEST_XML.replace("<V_UN>username</V_UN>", "<V_UN>" + params[0] + "</V_UN>");
                requestXML = requestXML.replace("<V_Pass>password</V_Pass>", "<V_Pass>" + params[1] + "</V_Pass>");
                // TODO: 7/18/18 Maybe do sth about these? :-""" :-?


                StringEntity entity = new StringEntity(requestXML);
                httpPost.setEntity(entity);
                httpPost.setHeader("Content-type", "text/xml;charset=UTF-8");

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
                        NodeList fLoginResult = dom.getElementsByTagName("FLoginResult"); // TODO: 7/18/18 Maybe change this to sth hardcoded? :-?
                        if (fLoginResult != null) {
                            if (fLoginResult.getLength() > 0 && fLoginResult.item(0).hasChildNodes()) {
                                return fLoginResult.item(0).getFirstChild().getTextContent();
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }

                    } else {
                        return null;
                    }
                } else {
                    return null;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
