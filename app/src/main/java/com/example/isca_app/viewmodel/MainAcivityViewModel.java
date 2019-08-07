package com.example.isca_app.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.example.isca_app.model.Retrofit.PhotoDataSourceFactory;
import com.example.isca_app.model.Retrofit.PhotoModel;
import com.example.isca_app.model.Room.DAO;
import com.example.isca_app.model.Room.PhotoRepository;
import com.example.isca_app.model.Room.SearchKeywordsAppDatabase;
import com.example.isca_app.model.Room.entity.Page;
import com.example.isca_app.model.Room.entity.Photo;
import com.example.isca_app.model.Room.entity.SearchKeyword;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainAcivityViewModel extends AndroidViewModel {
    //LiveData<PhotoDataSource> photoDataSourceLiveData;
    private Executor executor;
    private LiveData<PagedList<PhotoModel>> photosPagedList;
    private PhotoDataSourceFactory factory;

    private PhotoRepository photoRepository;

    public MainAcivityViewModel(@NonNull Application application) {
        super(application);
        photoRepository = new PhotoRepository(application);

        createPagedList(application);

    }

    public LiveData<PagedList<PhotoModel>> getPhotosPagedList() {
        return photosPagedList;
    }

    public PhotoDataSourceFactory getFactory() {
        return factory;
    }

    public void createPagedList(Application application)
    {
        factory=new PhotoDataSourceFactory(application);
        //photoDataSourceLiveData=factory.getMutableLiveData();

        PagedList.Config config=(new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();


        executor= Executors.newFixedThreadPool(5);

        photosPagedList = (new LivePagedListBuilder<Long,PhotoModel>(factory,config))
                .setFetchExecutor(executor)
                .build();
    }




    public void addSearchKeyword(String keyword)
    {
        photoRepository.addSearchKeyword(keyword);
    }


    public LiveData<List<SearchKeyword>> getSearchKeywords()
    {
        return photoRepository.getSearchKeywords();
    }


    public void addPage(Page page) throws ExecutionException, InterruptedException {
        photoRepository.addPage(page);
    }


    /*public LiveData<List<Page>> getPages()
    {
        return photoRepository.getPages();
    }*/



    public void addPhoto(Photo photo)
    {
        photoRepository.addPhoto(photo);
    }


    /*public LiveData<List<Photo>> getPhotos()
    {
        return photoRepository.getPhotos();
    }*/
}
