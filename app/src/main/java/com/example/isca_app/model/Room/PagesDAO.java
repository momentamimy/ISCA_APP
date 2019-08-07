package com.example.isca_app.model.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.isca_app.model.Room.entity.Page;
import com.example.isca_app.model.Room.entity.Photo;
import com.example.isca_app.model.Room.entity.SearchKeyword;

import java.util.List;

@Dao
public interface PagesDAO {

    @Insert
    public  long addPage(Page page);

    @Update
    public void updatePage(Page page);

    @Delete
    public void deletePage(Page page);

    @Query("select * from Pages")
    public LiveData<List<Page>> getAllPages();

    @Query("select * from Pages where keywordId==:KeywordId")
    public List<Page> getPages(int KeywordId);

}
