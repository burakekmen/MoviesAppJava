package burakekmen.com.moviesapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import burakekmen.com.moviesapp.R;

/****************************
 * Created by Burak EKMEN   |
 * 14.08.2018               |
 * ekmen.burak@hotmail.com  |
 ***************************/
public class RcListViewHolder extends RecyclerView.ViewHolder{

    public ImageView moviePoster=null;
    public TextView txtMovieTitle=null, txtMoviewReleaseDate=null, txtMovieOverview=null, txtMoreInfo=null;


    public RcListViewHolder(View itemView) {
        super(itemView);

        moviePoster = itemView.findViewById(R.id.rcList_item_Movie_Poster);
        txtMovieTitle = itemView.findViewById(R.id.rcList_item_Movie_Title);
        txtMoviewReleaseDate = itemView.findViewById(R.id.rcList_item_Movie_Release_Date);
        txtMovieOverview = itemView.findViewById(R.id.rcList_item_Movie_Overview);
        txtMoreInfo = itemView.findViewById(R.id.rcList_item_more_info);
    }
}
