package com.example.isca_app.model.Retrofit;

import android.app.Application;
import android.arch.paging.DataSource;

public class PhotoDataSourceFactory extends DataSource.Factory {

    PhotoDataSource photoDataSource;

    public PhotoDataSourceFactory(Application application) {
        photoDataSource=new PhotoDataSource(application);
    }

    @Override
    public DataSource create() {

        return photoDataSource;
    }

}
