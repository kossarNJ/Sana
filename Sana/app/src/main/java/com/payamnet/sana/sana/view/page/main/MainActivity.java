package com.payamnet.sana.sana.view.page.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.payamnet.sana.sana.R;
import com.payamnet.sana.sana.server.CallSearchWebService;
import com.payamnet.sana.sana.view.ViewHandler;
import com.payamnet.sana.sana.view.adapters.search.DocumentListAdapter;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity {

    static ArrayList<com.payamnet.sana.sana.model.Document> DOCS = new ArrayList<>();
    public static ViewHandler viewHandler;
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
        MainActivity.viewHandler = new ViewHandler();


        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() <= searchText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width() + searchText.getLeft() + searchText.getPaddingLeft()) {
                        String query = searchText.getText().toString();
                        new CallSearchWebService(MainActivity.this).execute(query);
                        return true;
                    }
                }
                return false;
            }
        });

        RecyclerView recyclerViewDocs = (RecyclerView) findViewById(R.id.doc_search_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewDocs.setLayoutManager(linearLayoutManager);
        DocumentListAdapter adapter = new DocumentListAdapter(this, MainActivity.DOCS);
        recyclerViewDocs.setAdapter(adapter);
        MainActivity.viewHandler.setDocumentListAdapter(adapter);
    }
}