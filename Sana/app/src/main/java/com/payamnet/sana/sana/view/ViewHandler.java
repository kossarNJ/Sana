package com.payamnet.sana.sana.view;

import com.payamnet.sana.sana.view.adapters.search.DocumentListAdapter;

/**
 * Created by kosar on 8/4/18.
 */

public class ViewHandler {
    private DocumentListAdapter documentListAdapter;

    public DocumentListAdapter getDocumentListAdapter() {
        return documentListAdapter;
    }

    public void setDocumentListAdapter(DocumentListAdapter documentListAdapter) {
        this.documentListAdapter = documentListAdapter;
    }
}
