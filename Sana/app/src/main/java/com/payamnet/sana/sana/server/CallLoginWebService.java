package com.payamnet.sana.sana.server;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.payamnet.sana.sana.MainActivity;
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

/**
 * Created by kosar on 8/4/18.
 */

public class CallLoginWebService extends AsyncTask<String, Void, String> {
    private Context context;

    public CallLoginWebService(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            Toast.makeText(this.context, Messages.WELCOME, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.context, MainActivity.class);
            this.context.startActivity(intent);
            ((MainActivity)this.context).finish();
        } else {
            Toast.makeText(this.context, Messages.NOT_AUTHENTICATED, Toast.LENGTH_LONG).show();
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

