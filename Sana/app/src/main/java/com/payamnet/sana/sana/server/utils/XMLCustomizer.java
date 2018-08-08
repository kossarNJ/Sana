package com.payamnet.sana.sana.server.utils;

import com.payamnet.sana.sana.constants.XMLTemplates;

/**
 * Created by kosar on 8/8/18.
 */

public class XMLCustomizer {
    public static String LOGINXML(String username, String password) {
        String requestXML = XMLTemplates.LOGIN_REQUEST_XML.replace("<V_UN>username</V_UN>", "<V_UN>" + username + "</V_UN>");
        requestXML = requestXML.replace("<V_Pass>password</V_Pass>", "<V_Pass>" + password + "</V_Pass>");
        return requestXML;
    }

    public static String SEARCHXML(String query) {
        return XMLTemplates.SEARCH_REQUEST_XML.replace("<V_Value>string</V_Value>", "<V_Value>" + query + "</V_Value>");

    }
}
