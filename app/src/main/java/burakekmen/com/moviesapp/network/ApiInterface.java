package burakekmen.com.moviesapp.network;

import burakekmen.com.moviesapp.models.DiscoverModel;
import burakekmen.com.moviesapp.models.MovieDetailModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/****************************
 * Created by Burak EKMEN   |
 * 14.08.2018               |
 * ekmen.burak@hotmail.com  |
 ***************************/
public interface ApiInterface {

    @GET("discover/movie?&language=tr-TR&sort_by=popularity.desc&include_adult=false&include_video=false")
    Call<DiscoverModel> getDiscoverLists(@Query("api_key") String apiKey, @Query("page") Integer page);

//    @GET("genre/movie/list?api_key={api_key}&language=tr-TR")
//    Call<GenreModel> getGenresDetail(@Path("api_key") String apiKey);

    @GET("movie/{movie_id}?&language=tr-TR")
    Call<MovieDetailModel> getMovieDetail(@Path("movie_id") Integer moviId, @Query("api_key") String apiKey);

}
