package com.example.assessmentwebbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class Bookmarks extends AppCompatActivity {


    DBBookmarkshandler dbBookmarkshandler =new DBBookmarkshandler(this,null,null,1);
    WebView mywebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bookmarks);
        setContentView(R.layout.activity_bookmarks);
        ListView listView =(ListView)findViewById(R.id.bookmarkslistview);
        final List<String> bookmarks=dbBookmarkshandler.databaseToString();
        if(bookmarks.size()>0){
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,bookmarks);
            listView.setAdapter(adapter);
            //listView.setAdapter(adapter);
            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String url=bookmarks.get(position);
                            Intent intent =new Intent(view.getContext(),MainActivity.class);
                            intent.putExtra("urls", url);
                            startActivity(intent);
                            finish();
                        }
                    }
            );

        }


    }
}