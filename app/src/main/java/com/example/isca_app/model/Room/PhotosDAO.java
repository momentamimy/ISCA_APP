package com.example.isca_app.model.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.isca_app.model.Room.entity.Page;
import com.example.isca_app.model.Room.entity.Photo;

import java.util.List;

@Dao
public interface PhotosDAO {



    @Insert
    public  long addPhoto(Photo photo);

    @Update
    public void updatePhoto(Photo photo);

    @Delete
    public void deletePhoto(Photo photo);

    @Query("select * from Photos")
    public LiveData<List<Photo>> getPhotos();


    @Query("select * from Photos where  pageId==:PageId")
    public List<Photo> getPhotos(int PageId);
}
