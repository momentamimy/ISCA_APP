package com.example.isca_app.model.Room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Photos",foreignKeys = {
        @ForeignKey(entity = Page.class, parentColumns = "id",childColumns = "pageId",onDelete = CASCADE)
})
public class Photo {
    @ColumnInfo(name = "owner")
    String owner;
    @ColumnInfo(name = "secret")
    String secret;
    @ColumnInfo(name = "server")
    String server;
    @ColumnInfo(name = "farm")
    int farm;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "ispublic")
    int ispublic;
    @ColumnInfo(name = "isfriend")
    int isfriend;
    @ColumnInfo(name = "isfamily")
    int isfamily;
    @ColumnInfo(name = "photoid")
    long id;

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int idPrim;
    @ColumnInfo(name = "pageId")
    long PageId;
    @ColumnInfo(name = "keywordId")
    long KeywordId;

    public Photo() {
    }

    public Photo(String owner, String secret, String server, int farm, String title, int ispublic, int isfriend, int isfamily, long id, long pageId, long keywordId) {
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.ispublic = ispublic;
        this.isfriend = isfriend;
        this.isfamily = isfamily;
        this.id = id;
        PageId = pageId;
        KeywordId = keywordId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIspublic() {
        return ispublic;
    }

    public void setIspublic(int ispublic) {
        this.ispublic = ispublic;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public int getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(int isfamily) {
        this.isfamily = isfamily;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPageId() {
        return PageId;
    }

    public void setPageId(long pageId) {
        PageId = pageId;
    }

    public long getKeywordId() {
        return KeywordId;
    }

    public void setKeywordId(long keywordId) {
        KeywordId = keywordId;
    }

    public int getIdPrim() {
        return idPrim;
    }

    public void setIdPrim(int idPrim) {
        this.idPrim = idPrim;
    }
}
