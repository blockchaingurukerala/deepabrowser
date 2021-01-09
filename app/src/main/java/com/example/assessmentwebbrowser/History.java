package com.example.assessmentwebbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class History extends AppCompatActivity {
    DBHandler dbHandler =new DBHandler(this,null,null,1);
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ListView listview= (ListView) findViewById(R.id.historylistview);
        final List<String> historysites= dbHandler.databaseToString();
        if(historysites.size()>0){
            ArrayAdapter arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,historysites);

            listview.setAdapter(arrayAdapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                    String url= historysites.get(i);
                    Intent intent= new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("urls", url);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}