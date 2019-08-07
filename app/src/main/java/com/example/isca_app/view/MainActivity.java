package com.example.isca_app.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.isca_app.R;
import com.example.isca_app.adapter.PhotoAdapter;
import com.example.isca_app.model.Retrofit.PhotoModel;
import com.example.isca_app.model.Room.entity.SearchKeyword;
import com.example.isca_app.service.CheckNetworkConnection;
import com.example.isca_app.service.ConnectionDetector;
import com.example.isca_app.viewmodel.MainAcivityViewModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import static com.example.isca_app.model.Retrofit.PhotoDataSource.tags;

public class MainActivity extends AppCompatActivity {

    MaterialSearchView searchView;

    public MainAcivityViewModel mainAcivityViewModel;
    RecyclerView recyclerView;
    PhotoAdapter pAdapter;

    PagedList<PhotoModel> modelPagedList;

    String[] keywords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        recyclerView=findViewById(R.id.Photo_recycleview);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);


        pAdapter = new PhotoAdapter(this,this);
        mainAcivityViewModel= ViewModelProviders.of(this).get(MainAcivityViewModel.class);


        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);

        if (tags!="")
        {
            startCreatingSearch(tags);
        }


        LiveData<List<SearchKeyword>> searchKeywords=mainAcivityViewModel.getSearchKeywords();
        searchKeywords.observe(this, new Observer<List<SearchKeyword>>() {
            @Override
            public void onChanged(@Nullable List<SearchKeyword> searchKeywords) {
                keywords=new String[searchKeywords.size()];
                for (int i=0;i<searchKeywords.size();i++)
                {
                    keywords[i]=searchKeywords.get(i).getKeyWord();
                    Log.w("keysearch",keywords[i]);
                }
                searchView.setSuggestions(keywords);
            }
        });


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startCreatingSearch(query);
                Log.d("clickQuery",query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                Log.d("changeQuery",newText);
                return false;
            }
        });

    }


    public void createSearch()
    {
        mainAcivityViewModel.getPhotosPagedList().observe(this, new Observer<PagedList<PhotoModel>>() {
            @Override
            public void onChanged(@Nullable PagedList<PhotoModel> photoModels) {

                modelPagedList=photoModels;

                if (photoModels.size()==0)
                {
                    Toast.makeText(getApplicationContext(),"please search with another word",Toast.LENGTH_LONG).show();
                }
                pAdapter.submitList(photoModels);

                pAdapter.notifyDataSetChanged();

                mainAcivityViewModel.getPhotosPagedList().removeObserver(this);
                mainAcivityViewModel.createPagedList(getApplication());
            }
        });

    }

    public  boolean keywordIsExist(String keyword)
    {
        for (int i=0;i<keywords.length;i++)
        {
            if (keyword.equals(keywords[i]))
            {
                return true;
            }
        }
        return false;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


    public void startCreatingSearch(String query)
    {
        //Do some magic
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {
                if (!keywordIsExist(query))
                {
                    mainAcivityViewModel.addSearchKeyword(query);
                }
                tags = query;
                getSupportActionBar().setTitle(query);
                createSearch();
            }
            else
            {
                if (keywordIsExist(query))
                {
                    tags = query;
                    getSupportActionBar().setTitle(query);
                    createSearch();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"this search not saved please connect to interne",Toast.LENGTH_LONG).show();
                }
            }
        }
        else
        {
            if (keywordIsExist(query))
            {
                tags = query;
                getSupportActionBar().setTitle(query);
                createSearch();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"this search not saved please connect to internet",Toast.LENGTH_LONG).show();
            }
        }
    }
}
