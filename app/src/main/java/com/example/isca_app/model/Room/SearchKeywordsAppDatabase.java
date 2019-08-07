package com.example.isca_app.model.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.isca_app.model.Room.entity.Page;
import com.example.isca_app.model.Room.entity.Photo;
import com.example.isca_app.model.Room.entity.SearchKeyword;

@Database(entities = {SearchKeyword.class, Page.class, Photo.class},version = 1)
public abstract class SearchKeywordsAppDatabase extends RoomDatabase {

    public abstract DAO getSearchKeywordDAO();
    public abstract PagesDAO getPageDAO();
    public abstract PhotosDAO getPhotoDAO();

    private static SearchKeywordsAppDatabase instance;

    public static synchronized SearchKeywordsAppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SearchKeywordsAppDatabase.class, "Search_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
