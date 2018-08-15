package burakekmen.com.moviesapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import burakekmen.com.moviesapp.R;
import burakekmen.com.moviesapp.models.ResultModel;

/****************************
 * Created by Burak EKMEN   |
 * 14.08.2018               |
 * ekmen.burak@hotmail.com  |
 ***************************/
public class RcListAdapter extends RecyclerView.Adapter<RcListViewHolder> {

    private ArrayList<ResultModel> filmListesi=null;
    private Context context=null;

    public RcListAdapter(Context context, ArrayList<ResultModel> filmListesi){
        this.context = context;
        this.filmListesi = filmListesi;
    }

    @NonNull
    @Override
    public RcListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rclist_item, parent, false);
        return new RcListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RcListViewHolder holder, int position) {

        final ResultModel film = getItem(position);

        Picasso.get().load(context.getString(R.string.poster_base_url) + film.getPosterPath()).into(holder.moviePoster);
        holder.txtMovieTitle.setText(film.getTitle());
        holder.txtMoviewReleaseDate.setText(film.getReleaseDate());
        holder.txtMovieOverview.setText(film.getOverview());

        holder.txtMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, film.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return filmListesi.size();
    }


    private ResultModel getItem(int position){
        return filmListesi.get(position);
    }

}
