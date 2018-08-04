package com.payamnet.sana.sana.server;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.payamnet.sana.sana.constants.URLS;
import com.payamnet.sana.sana.constants.XMLTemplates;
import com.payamnet.sana.sana.view.page.main.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
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

// TODO: 8/4/18 NEEDS MAJOR REFACTOR!!!!! A LOT OF THINGS NEED TO BE HARDCODED. NAMES NEED TO BE CHANGED. MESSAGES NEED TO BE HARDCODED AS WELL
public class CallSearchWebService extends AsyncTask<String, Void, String> {

    private Context context;
    private ArrayList<com.payamnet.sana.sana.model.Document> newDocs = new ArrayList<>();

    public CallSearchWebService(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s == null) {
            Toast.makeText(this.context, "s result was null. something went wrong", Toast.LENGTH_LONG).show();
        } else {
            MainActivity.viewHandler.getDocumentListAdapter().setDocs((ArrayList<com.payamnet.sana.sana.model.Document>) com.payamnet.sana.sana.model.Document.documentList);
            MainActivity.viewHandler.getDocumentListAdapter().notifyDataSetChanged();
//                            ViewHandler.avLoadingIndicatorView.hide();

        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(URLS.SEARCH_URL);

            String requestXML = XMLTemplates.SEARCH_REQUEST_XML.replace("<V_Value>string</V_Value>", "<V_Value>" + params[0] + "</V_Value>");

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
                    NodeList fSearchResult = dom.getElementsByTagName("FSearchResult");

                    if (fSearchResult != null && fSearchResult.getLength() > 0 && fSearchResult.item(0).hasChildNodes()) {


                        String serverResult = fSearchResult.item(0).getFirstChild().getTextContent();
                        InputStream is = new ByteArrayInputStream(serverResult.getBytes("UTF-8"));
                        Document docAsli = null;
                        try {
                            DocumentBuilder builder2 = factory.newDocumentBuilder();
                            docAsli = builder2.parse(is);
                        } catch (ParserConfigurationException | IllegalStateException | SAXException e) {
                            e.printStackTrace();
                        }

                        ArrayList<com.payamnet.sana.sana.model.Document> documentsAsList = new ArrayList<>();
                        if (docAsli != null) {
                            NodeList dsNodeList = docAsli.getElementsByTagName("DS");
                            Node dsNode;
                            if (dsNodeList != null && dsNodeList.getLength() > 0) {
                                dsNode = dsNodeList.item(0);
                                for (int i = 0; i < dsNode.getChildNodes().getLength(); i++) {
                                    Node doc = dsNode.getChildNodes().item(i);
                                    NodeList attributes = doc.getChildNodes();
                                    com.payamnet.sana.sana.model.Document newDoc = new com.payamnet.sana.sana.model.Document();
                                    boolean foundAttribute = false;
                                    for (int j = 0; j < attributes.getLength(); j++) {
                                        Node attribute = attributes.item(j);
                                        if (attribute.getNodeName().equalsIgnoreCase("DC")) {
                                            newDoc.setId(attribute.getTextContent());
                                            foundAttribute = true;
                                        } else if (attribute.getNodeName().equalsIgnoreCase("Title")) {
                                            newDoc.setTitle(attribute.getTextContent());
                                            foundAttribute = true;
                                        } else if (attribute.getNodeName().equalsIgnoreCase("Author")) {
                                            newDoc.setAuthor(attribute.getTextContent());
                                            foundAttribute = true;
                                        } else if (attribute.getNodeName().equalsIgnoreCase("Publisher")) {
                                            newDoc.setPublisher(attribute.getTextContent());
                                            foundAttribute = true;
                                        } else if (attribute.getNodeName().equalsIgnoreCase("Subject")) {
                                            newDoc.setSubject(attribute.getTextContent());
                                            foundAttribute = true;
                                        }
                                    }
                                    if (foundAttribute) {
                                        documentsAsList.add(newDoc);
                                    }
                                }
                            }
                        }
                        if (documentsAsList.size() > 0) {
                            com.payamnet.sana.sana.model.Document.documentList = new ArrayList<>();
                            this.newDocs = new ArrayList<>();
                            for (com.payamnet.sana.sana.model.Document d : documentsAsList) {
                                com.payamnet.sana.sana.model.Document.documentList.add(d);
                                this.newDocs.add(d);
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

