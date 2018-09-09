package com.payamnet.sana.sana.constants;

/**
 * Created by kosar on 7/18/18.
 */

public class XMLTemplates {
    public static final String LOGIN_REQUEST_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <FLogin xmlns=\"http://tempuri.org/\">\n" +
            "      <V_UN>username</V_UN>\n" +
            "      <V_Pass>password</V_Pass>\n" +
            "    </FLogin>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>";

    public static final String SEARCH_REQUEST_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <FSearch xmlns=\"http://tempuri.org/\">\n" +
            "      <V_Value>string</V_Value>\n" +
            "    </FSearch>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>";
}
