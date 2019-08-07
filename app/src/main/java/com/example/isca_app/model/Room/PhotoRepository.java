package com.example.isca_app.model.Room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.isca_app.model.Room.entity.Page;
import com.example.isca_app.model.Room.entity.Photo;
import com.example.isca_app.model.Room.entity.SearchKeyword;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PhotoRepository {

    SearchKeywordsAppDatabase appDatabase;
    DAO dao;
    PagesDAO pagesDAO;
    PhotosDAO photosDAO;
    public PhotoRepository(Application application) {

        appDatabase = SearchKeywordsAppDatabase.getInstance(application);
        dao = appDatabase.getSearchKeywordDAO();
        pagesDAO = appDatabase.getPageDAO();
        photosDAO = appDatabase.getPhotoDAO();
    }


    public void addSearchKeyword(final String keyword)
    {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.addKeyord(new SearchKeyword(keyword));
            }
        });
        //new addKeywordDataAsyncTask().execute(keyword);
    }


    public LiveData<List<SearchKeyword>> getSearchKeywords()
    {
        return dao.getSearchKeywords();
    }

    public SearchKeyword getKeyword(String keyword) throws ExecutionException, InterruptedException {
        return new getKeywordDataAsyncTask().execute(keyword).get();
    }


    private class getKeywordDataAsyncTask extends AsyncTask<String,Void,SearchKeyword> {


        @Override
        protected SearchKeyword doInBackground(String... strings) {
            return dao.getKeyword(strings[0]);
        }
    }



    public long addPage(final Page page) throws ExecutionException, InterruptedException {
        return new addPageDataAsyncTask().execute(page).get();
    }


    public List<Page> getPages(int keywordId) throws ExecutionException, InterruptedException {
        return new getPagesDataAsyncTask().execute(keywordId).get();
    }



    private class addPageDataAsyncTask extends AsyncTask<Page,Void,Long> {


        @Override
        protected Long doInBackground(Page... pages) {
            return pagesDAO.addPage(pages[0]);
        }
    }
    private class getPagesDataAsyncTask extends AsyncTask<Integer,Void,List<Page>> {

        @Override
        protected List<Page> doInBackground(Integer... integers) {
            return pagesDAO.getPages(integers[0]);
        }
    }


    public void addPhoto(final Photo photo)
    {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                photosDAO.addPhoto(photo);
            }
        });
        //new addPhotoDataAsyncTask().execute(photo);
    }


    public List<Photo> getPhoto(int pageId) throws ExecutionException, InterruptedException {
        return new getPhotosDataAsyncTask().execute(pageId).get();
    }


    private class getPhotosDataAsyncTask extends AsyncTask<Integer,Void,List<Photo>> {

        @Override
        protected List<Photo> doInBackground(Integer... integers) {
            return photosDAO.getPhotos(integers[0]);
        }
    }
}
