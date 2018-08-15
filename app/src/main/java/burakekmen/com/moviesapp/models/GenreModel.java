package burakekmen.com.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/****************************
 * Created by Burak EKMEN   |
 * 14.08.2018               |
 * ekmen.burak@hotmail.com  |
 ***************************/
public class GenreModel {

    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
