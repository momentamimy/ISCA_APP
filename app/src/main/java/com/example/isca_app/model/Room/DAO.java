package com.example.isca_app.model.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.isca_app.model.Room.entity.SearchKeyword;

import java.util.List;

@Dao
public interface DAO {
    @Insert
    public  long addKeyord(SearchKeyword searchKeyword);

    @Update
    public void updateKeyord(SearchKeyword searchKeyword);

    @Delete
    public void deleteKeyord(SearchKeyword searchKeyword);

    @Query("select * from SearchKeywords where keyWord == :searchkeyword")
    public SearchKeyword getKeyword(String searchkeyword);

    @Query("select * from SearchKeywords")
    public LiveData<List<SearchKeyword>> getSearchKeywords();

    /*
    @Query("select * from SearchKeywords where id ==:searchKeywordId")
    public LiveData<SearchKeyword> getSearchKeyword(long searchKeywordId);
    */
}
