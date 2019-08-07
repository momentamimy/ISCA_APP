package com.example.isca_app.model.Room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Pages",foreignKeys = @ForeignKey(entity = SearchKeyword.class,
        parentColumns = "id",childColumns = "keywordId",onDelete = CASCADE))
public class Page {
    @ColumnInfo(name = "pageNumber")
    Integer pageNumber;
    @ColumnInfo(name = "NumberOFpages")
    int NumberOFpages;
    @ColumnInfo(name = "perpage")
    int perpage;
    @ColumnInfo(name = "total")
    String total;

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "keywordId")
    int keywordId;

    public Page()
    {

    }

    public Page(int pageNumber, int numberOFpages, int perpage, String total, int keywordId) {
        this.pageNumber = pageNumber;
        NumberOFpages = numberOFpages;
        this.perpage = perpage;
        this.total = total;
        this.id = id;
        this.keywordId = keywordId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getNumberOFpages() {
        return NumberOFpages;
    }

    public void setNumberOFpages(int numberOFpages) {
        NumberOFpages = numberOFpages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }
}
