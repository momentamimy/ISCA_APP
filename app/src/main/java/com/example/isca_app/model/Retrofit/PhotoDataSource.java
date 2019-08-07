package com.example.isca_app.model.Retrofit;

import android.app.Application;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.isca_app.model.Room.PhotoRepository;
import com.example.isca_app.model.Room.entity.Page;
import com.example.isca_app.model.Room.entity.Photo;
import com.example.isca_app.model.Room.entity.SearchKeyword;
import com.example.isca_app.service.CheckNetworkConnection;
import com.example.isca_app.service.ConnectionDetector;
import com.example.isca_app.service.PhotoDataService;
import com.example.isca_app.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class PhotoDataSource extends PageKeyedDataSource<Integer, PhotoModel> {
    public final  static String method = "flickr.Photos.search";
    public final  static String api_key = "d209679a09441c1be63a8489cfd4dadd";
    public final  static String format = "json";
    public static String tags = "";
    public final  static String nojsoncallback = "1";


    Application application;
    PhotoRepository photoRepository;
    public PhotoDataSource(Application application) {
        this.application =application;
        photoRepository = new PhotoRepository(application);
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, PhotoModel> callback) {
        if (CheckNetworkConnection.hasInternetConnection(application)) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(application)) {

                PhotoDataService dataService = RetrofitInstance.getService();
                Call<PhotoResponse> call = dataService.getPhotosWithPage(method,api_key,format,tags,nojsoncallback,1);

                call.enqueue(new Callback<PhotoResponse>() {
                    @Override
                    public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                        PhotoResponse photoResponse= response.body();
                        ArrayList<PhotoModel> photosList=(ArrayList<PhotoModel>) photoResponse.getPhotos().getPhoto();

                        savePage(response);
                        callback.onResult(photosList,null, 2);
                    }

                    @Override
                    public void onFailure(Call<PhotoResponse> call, Throwable t) {
                    }
                });
            }
            else
            {
                getFirstPage(callback);
            }
        }
        else
        {
            getFirstPage(callback);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PhotoModel> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, PhotoModel> callback) {

        if (CheckNetworkConnection.hasInternetConnection(application)) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(application)) {

                PhotoDataService dataService = RetrofitInstance.getService();
                Call<PhotoResponse> call = dataService.getPhotosWithPage(method, api_key, format, tags, nojsoncallback, params.key);

                call.enqueue(new Callback<PhotoResponse>() {
                    @Override
                    public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                        PhotoResponse photoResponse = response.body();
                        ArrayList<PhotoModel> photosList = (ArrayList<PhotoModel>) photoResponse.getPhotos().getPhoto();

                        savePage(response);
                        callback.onResult(photosList, params.key + 1);
                    }

                    @Override
                    public void onFailure(Call<PhotoResponse> call, Throwable t) {
                    }
                });
            }
            else
                {
                    getPage(params,callback);
                }
        }
        else
            {
                getPage(params,callback);
            }
    }

    public void getFirstPage(final LoadInitialCallback<Integer, PhotoModel> callback)
    {
        PhotoRepository photoRepository=new PhotoRepository(application);

        SearchKeyword keyword= null;
        try {
            keyword = photoRepository.getKeyword(tags);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (keyword!=null)
        {
            try {

                List<Page> pages=photoRepository.getPages(keyword.getId());
                sortPages(pages);
                List<Photo> photos=photoRepository.getPhoto(pages.get(0).getId());

                callback.onResult(CreatephotoModels(photos),null,  2);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void getPage(final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, PhotoModel> callback)
    {
        PhotoRepository photoRepository=new PhotoRepository(application);

        SearchKeyword keyword= null;
        try {
            keyword = photoRepository.getKeyword(tags);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (keyword!=null)
        {
            try {

                List<Page> pages=photoRepository.getPages(keyword.getId());
                sortPages(pages);

                if (params.key-1!=pages.size()) {
                    List<Photo> photos = photoRepository.getPhoto(pages.get(params.key - 1).getId());

                    callback.onResult(CreatephotoModels(photos), params.key + 1);

                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void savePage(Response<PhotoResponse> response)
    {
        PhotoResponse photoResponse= response.body();
        ArrayList<PhotoModel> photosList=(ArrayList<PhotoModel>) photoResponse.getPhotos().getPhoto();
        Photos pageRetrofit=photoResponse.getPhotos();
        SearchKeyword keyword= null;
        try {
            keyword = photoRepository.getKeyword(tags);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Page pageRoom=new Page(pageRetrofit.getPage(),pageRetrofit.getPages(), pageRetrofit.getPerpage(),pageRetrofit.getTotal(), keyword.getId());
        try {
            long l=photoRepository.addPage(pageRoom);
            pageRoom.setId((int) l);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (int i=0;i<photosList.size();i++)
        {
            PhotoModel photoModel=photosList.get(i);
            Photo photo=new Photo(photoModel.getOwner(), photoModel.getSecret(), photoModel.getServer(), photoModel.getFarm(),photoModel.getTitle(),photoModel.getIspublic(),photoModel.getIsfriend(), photoModel.getIsfamily(),Long.parseLong(photoModel.getId()), pageRoom.getId(), keyword.getId());
            photoRepository.addPhoto(photo);
        }
    }


    public List<Page> sortPages(List<Page> pages){
    Collections.sort(pages, new Comparator<Page>() {
        @Override
        public int compare(Page u1, Page u2) {
            return u1.getPageNumber().compareTo(u2.getPageNumber());
        }
    });
    return pages;
    }

    public List<PhotoModel> CreatephotoModels(List<Photo> photos)
    {
        List<PhotoModel> photoModels = new ArrayList<>();
        for (int i=0;i<photos.size();i++)
        {
            Photo photo=photos.get(i);
            photoModels.add(new PhotoModel(String.valueOf(photo.getId()), photo.getOwner(), photo.getSecret(), photo.getServer(), photo.getFarm(), photo.getTitle(), photo.getIspublic(), photo.getIsfriend(), photo.getIsfamily()));
        }
        return photoModels;
    }
}
