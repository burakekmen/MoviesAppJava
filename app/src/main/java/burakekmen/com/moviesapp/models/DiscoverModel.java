package burakekmen.com.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/****************************
 * Created by Burak EKMEN   |
 * 14.08.2018               |
 * ekmen.burak@hotmail.com  |
 ***************************/
public class DiscoverModel {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private ArrayList<ResultModel> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<ResultModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultModel> results) {
        this.results = results;
    }

    public DiscoverModel(){
        this.results = new ArrayList<ResultModel>();
    }

}
