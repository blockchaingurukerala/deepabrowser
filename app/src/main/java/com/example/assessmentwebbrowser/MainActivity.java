package com.example.assessmentwebbrowser;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.telephony.mbms.DownloadProgressListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

EditText url_field;
ImageButton refrbtn, gobtn, micbtn,bookmarkBtn ;
WebView webView;
ProgressBar progressBar;
BottomSheetDialog bottomSheetDialog;
LinearLayout history, historyExit;
Toolbar toolbar;
//private int desktopmodesValue=0;
DBHandler dbHandler;
DBBookmarkshandler dbBookmarkshandler;

    private BottomNavigationView.OnNavigationItemSelectedListener OnNavigationItemSelectedListner
            =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.navigation_home:
                    Intent intent =new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;

                case R.id.navigation_left:
                    onBackPressed();
                    return true;

                case R.id.dashboard:
                    bottomSheetDialog.show();


                    return true;
                case R.id.navigation_right:
                    onForwardPressed();
                    return true;

            }
            return false;
        }
    };





    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        url_field =findViewById(R.id.url_field);
        refrbtn=findViewById(R.id.refrbtn);
        gobtn=findViewById(R.id.gobtn);
        webView=findViewById(R.id.webView);
        progressBar= findViewById(R.id.progressBar);
        url_field.setOnEditorActionListener(editorListner);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.co.nz");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        progressBar.setProgress(100);
        micbtn=findViewById(R.id.micbtn);
        createBottomSheetDialogue();
        bookmarkBtn = findViewById(R.id.bookmarkbtn);
        dbHandler=new DBHandler(this,null,null,1);
        dbBookmarkshandler= new DBBookmarkshandler(this,null,null,1);
        saveData();


        /* get URL from another activity*/

        if (getIntent().getStringExtra("urls")!=null){
            webView.loadUrl(getIntent().getStringExtra("urls"));
        }

        /*--------------------------------*/

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

       /*Download Listner*/
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, final String userAgent, String contentDisposition, String mimetype, long contentLength) {
                saveData();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.v("TAG", "Permission Granted");
//                        DownloadAlerter(url, userAgent, contentDisposition, mimetype);
                        saveData();
                    } else {
                        Log.v("TAG", "Permission is revoked");
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    }
                } else {
                    Log.v("TAG", "Permission is Granted");
//                    DownloadAlerter(url, userAgent, contentDisposition, mimetype);
                }
            }
        });


        refrbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            webView.reload();
            }
        });

        gobtn.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         url_field = findViewById(R.id.url_field);
                                         String urlstr = url_field.getText().toString();

                                         if (!urlstr.startsWith("https://")){
                                             urlstr="http:///www.google.com/search?q="+ urlstr;
                                         }
                                         webView.loadUrl(urlstr);
                                         webView.setWebViewClient(new WebViewClient() {

                                             @Override
                                             public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                                 super.onPageStarted(view, url, favicon);
                                                 progressBar.setVisibility(View.VISIBLE);
                                                 url_field.setText(url);
                                             }

                                             @Override
                                             public void onPageFinished(WebView view, String url) {
                                                 super.onPageFinished(view, url);
                                                 progressBar.setVisibility(View.GONE);
                                             }
                                         });

                                         webView.loadUrl(urlstr);
                                         WebSettings webSettings = webView.getSettings();
                                         webSettings.setJavaScriptEnabled(true);
                                     }
                                 });

//test


        micbtn.setOnClickListener(view -> {
                    Intent micIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    micIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    micIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                    //if mic or voice recognition is not supported-
                    if (micIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(micIntent, 10);
                    } else {
                        Toast.makeText(MainActivity.this, "Speech Recognition not supported on this Device", Toast.LENGTH_SHORT).show();
                    }
                });
        bookmarkBtn.setEnabled(true);
        bookmarkBtn.setClickable(true);
        bookmarkBtn.setFocusableInTouchMode(true);
        bookmarkBtn.setFocusable(true);

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBookmarkBtnPressed();
                Toast.makeText(MainActivity.this,"Added to Bookmarks", Toast.LENGTH_SHORT).show();

            }
        });




//        BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListner);

    }

    private TextView.OnEditorActionListener editorListner = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId){
                case EditorInfo
                        .IME_ACTION_GO:
                    Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                String urlstr = url_field.getText().toString();
                if (!urlstr.startsWith("http://")){
                    urlstr= "http://www.google.com/search?q=" +urlstr;
                }
                webView.loadUrl(urlstr);
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.extendedmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.history:
                Intent historyIn= new Intent(MainActivity.this,History.class);
                startActivity(historyIn);
            break;
        }

        switch (item.getItemId()){
            case R.id.bookmarks:
                Intent bookmarksIn= new Intent(MainActivity.this, Bookmarks.class);
                startActivity(bookmarksIn);
                break;
        }
        switch (item.getItemId()){
            case R.id.about:
                Intent aboutIn= new Intent(MainActivity.this, About.class);
                startActivity(aboutIn);
                break;
        }
//        switch (item.getItemId()){
//            case R.id.desktop:
//                break;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        } else
        {
            MainActivity.super.onBackPressed();
        }
    }


    public void onBookmarkBtnPressed1(){
        Websites websites=new Websites(webView.getUrl());
        dbBookmarkshandler.addUrl(websites);
        saveData();
    }

    public void onForwardPressed(){
        if (webView.canGoForward()){
            webView.goForward();
        }
        else {
            Toast.makeText(this, "Cant go forward" , Toast.LENGTH_SHORT).show();
        }
    }

    private void onBookmarkBtnPressed(){
        Websites websites=new Websites(webView.getUrl());
        dbBookmarkshandler.addUrl(websites);
        saveData();

    }

    private void saveData(){
        Websites websites = new Websites(webView.getUrl());
        dbHandler.addUrl(websites);
    }

//    public void DownloadAlerter(final String url, final String userAgent, String contentDisposition, String mimetype){
//        //get filename from URL
//
//        final String fileName= URLUtil.guessFileName(url, contentDisposition,mimetype);
//        urlsend=url;
//        fileNameSend=fileName;
//
//
//
//    }

    private void createBottomSheetDialogue(){
        if (bottomSheetDialog==null){
            View view= LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialogue,null);
//            history=view.findViewById(R.id.history);
//            historyExit=view.findViewById(R.id.historyExit);
            bottomSheetDialog=new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(view);
        }
    }
}