package com.payamnet.sana.sana.server;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.payamnet.sana.sana.R;
import com.payamnet.sana.sana.constants.Messages;
import com.payamnet.sana.sana.constants.URLS;
import com.payamnet.sana.sana.server.utils.XMLCustomizer;
import com.payamnet.sana.sana.view.page.login.LoginActivity;
import com.payamnet.sana.sana.view.page.main.MainActivity;

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
            ((LoginActivity) this.context).finish();
        } else {
            Toast.makeText(this.context, Messages.NOT_AUTHENTICATED, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(URLS.LOGIN_URL);

            String requestXML = XMLCustomizer.LOGIN_XML(params[0], params[1]);

            StringEntity entity = new StringEntity(requestXML);
            httpPost.setEntity(entity);
            httpPost.setHeader(context.getString(R.string.xml_header_name), context.getString(R.string.xml_header_value));

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
                    NodeList fLoginResult = dom.getElementsByTagName(context.getString(R.string.xml_login_result_tag));
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
