package com.samujjalm.android.udacitymoviestageone;

/**
 * Created by samujjal on 27/12/15.
 */
public class Movie {
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String image;
    private String title;

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    private String plotSynopsis;

    public String getUserRaing() {
        return userRaing;
    }

    public void setUserRaing(String userRaing) {
        this.userRaing = userRaing;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    private String userRaing;
    private String releaseDate;


}

