package com.payamnet.sana.sana.server;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.payamnet.sana.sana.R;
import com.payamnet.sana.sana.constants.Messages;
import com.payamnet.sana.sana.constants.URLS;
import com.payamnet.sana.sana.model.Document;
import com.payamnet.sana.sana.server.utils.XMLCustomizer;
import com.payamnet.sana.sana.view.page.main.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by kosar on 8/4/18.
 */

public class CallSearchWebService extends AsyncTask<String, Void, String> {

    private Context context;

    public CallSearchWebService(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s == null) {
            Toast.makeText(this.context, Messages.REQUEST_ERROR, Toast.LENGTH_LONG).show();
        } else {
            MainActivity.viewHandler.getDocumentListAdapter().setDocs((ArrayList<Document>) Document.documentList);
            MainActivity.viewHandler.getDocumentListAdapter().notifyDataSetChanged();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(URLS.SEARCH_URL);

            String requestXML = XMLCustomizer.SEARCHXML(params[0]);

            StringEntity entity = new StringEntity(requestXML, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader(context.getString(R.string.xml_header_name), context.getString(R.string.xml_header_value));


            CloseableHttpResponse response = client.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
                HttpEntity responseEntity = response.getEntity();
                org.w3c.dom.Document dom = null;

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                try {
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    dom = builder.parse(responseEntity.getContent());
                } catch (ParserConfigurationException | IllegalStateException | SAXException e) {
                    e.printStackTrace();
                }
                if (dom != null) {
                    NodeList fSearchResult = dom.getElementsByTagName(context.getString(R.string.xml_search_result_tag));

                    if (fSearchResult != null && fSearchResult.getLength() > 0 && fSearchResult.item(0).hasChildNodes()) {
                        String serverResult = fSearchResult.item(0).getFirstChild().getTextContent();
                        InputStream is = new ByteArrayInputStream(serverResult.getBytes("UTF-8"));
                        org.w3c.dom.Document document = null;
                        try {
                            DocumentBuilder builder2 = factory.newDocumentBuilder();
                            document = builder2.parse(is);
                        } catch (ParserConfigurationException | IllegalStateException | SAXException e) {
                            e.printStackTrace();
                        }

                        ArrayList<Document> documentList = new ArrayList<>();
                        if (document != null) {
                            NodeList dsNodeList = document.getElementsByTagName(context.getString(R.string.xml_search_ds_tag));
                            Node dsNode;
                            if (dsNodeList != null && dsNodeList.getLength() > 0) {
                                dsNode = dsNodeList.item(0);
                                for (int i = 0; i < dsNode.getChildNodes().getLength(); i++) {
                                    Node doc = dsNode.getChildNodes().item(i);
                                    NodeList attributes = doc.getChildNodes();
                                    Document newDoc = new Document();
                                    boolean foundAttribute = false;
                                    for (int j = 0; j < attributes.getLength(); j++) {
                                        Node attribute = attributes.item(j);
                                        if (attribute.getNodeName().equalsIgnoreCase(Document.ID_TAG)) {
                                            newDoc.setId(attribute.getTextContent());
                                            foundAttribute = true;
                                        } else if (attribute.getNodeName().equalsIgnoreCase(Document.TITLE_TAG)) {
                                            newDoc.setTitle(attribute.getTextContent());
                                            foundAttribute = true;
                                        } else if (attribute.getNodeName().equalsIgnoreCase(Document.AUTHOR_TAG)) {
                                            newDoc.setAuthor(attribute.getTextContent());
                                            foundAttribute = true;
                                        } else if (attribute.getNodeName().equalsIgnoreCase(Document.PUBLISHER_TAG)) {
                                            newDoc.setPublisher(attribute.getTextContent());
                                            foundAttribute = true;
                                        } else if (attribute.getNodeName().equalsIgnoreCase(Document.SUBJECT_TAG)) {
                                            newDoc.setSubject(attribute.getTextContent());
                                            foundAttribute = true;
                                        }
                                    }
                                    if (foundAttribute) {
                                        documentList.add(newDoc);
                                    }
                                }
                            }
                        }
                        if (documentList.size() > 0) {
                            Document.documentList = new ArrayList<>();
                            for (Document d : documentList) {
                                Document.documentList.add(d);
                            }
                        }
                    }

                    if (fSearchResult != null) {
                        if (fSearchResult.getLength() > 0 && fSearchResult.item(0).hasChildNodes()) {
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

