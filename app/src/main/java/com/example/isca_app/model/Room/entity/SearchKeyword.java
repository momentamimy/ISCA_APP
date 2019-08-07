package com.example.isca_app.model.Room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "SearchKeywords")
public class SearchKeyword {

    @ColumnInfo(name = "keyWord")
    String keyWord;
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int id;


    @Ignore
    public SearchKeyword() {
    }

    public SearchKeyword(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
