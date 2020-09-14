package com.vilect.asm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.navigation.NavigationView;
import com.vilect.asm.R;
import com.vilect.asm.adapter.NewsRecyclerViewCustomAdapter;
import com.vilect.asm.util.NewsSaxHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParserFactory;

public class NewsActivity extends AppCompatActivity {

    private String link = "https://beta.vnexpress.net/rss/tin-moi-nhat.rss";
    private RecyclerView rv;
    private DrawerLayout drawerLayout;
    private NavigationView nav;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mapping();
        setNav();

        GetNews getNews = new GetNews(NewsActivity.this);
        getNews.execute();

    }

    private void mapping()
    {
        rv = findViewById(R.id.rvNews);
        drawerLayout = findViewById(R.id.drawerNews);
        nav = findViewById(R.id.navNews);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

    }

    private void setNav()
    {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.itHome:{
                        Intent intent = new Intent(NewsActivity.this, MainUIActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    }

                    case R.id.itNews:{
                        drawerLayout.closeDrawers();
                        break;
                    }

                    case R.id.itPersonal:{
                        Intent intent = new Intent(NewsActivity.this, PersonalActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    }

                    default: break;
                }
                return false;
            }
        });
    }

    class GetNews extends AsyncTask<Void, Void, Void>
    {
        private Context context;
        //truyền rv vào để setAdapter sau khi lấy được dữ liệu

        public GetNews(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try
            {
                //tạo đường dẫn
                URL url = new URL(link);
                //mở luồng lên internet
                URLConnection urlConnection = url.openConnection();
                //tạo luồng cho phép dữ liệu từ internet chảy về
                InputStream is = urlConnection.getInputStream();

                //tiến hành đọc RSS
                XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();

                //class này như là một cái khuôn, hướng dẫn xmlReader quét dữ liệu
                NewsSaxHandler newsSaxHandler = new NewsSaxHandler();
                xmlReader.setContentHandler(newsSaxHandler);
                xmlReader.parse(new InputSource(is));

                //lúc này newsArrayList sẽ được thêm đối tượng news tự động


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rv.setLayoutManager(linearLayoutManager);
            rv.setAdapter(new NewsRecyclerViewCustomAdapter(context));
        }
    }
}
