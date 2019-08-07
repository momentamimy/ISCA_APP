package com.example.isca_app.model.Retrofit;

import java.util.List;

public class Photos {

    int page;
    int pages;
    int perpage;
    String total;
    List<PhotoModel> photo;

    public Photos(int page, int pages, int perpage, String total, List<PhotoModel> photo) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photo = photo;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
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

    public List<PhotoModel> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotoModel> photo) {
        this.photo = photo;
    }
}
