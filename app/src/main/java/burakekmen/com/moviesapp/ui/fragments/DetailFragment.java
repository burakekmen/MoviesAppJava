package burakekmen.com.moviesapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import burakekmen.com.moviesapp.R;
import burakekmen.com.moviesapp.models.Genre;
import burakekmen.com.moviesapp.models.MovieDetailModel;
import burakekmen.com.moviesapp.models.ResultModel;
import burakekmen.com.moviesapp.network.ApiClient;
import burakekmen.com.moviesapp.network.ApiInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {

    private ResultModel movie=null;
    private View fLayout=null;
    private MovieDetailModel movieDetail = null;
    private ApiInterface apiInterface = null;
    private TextView title;
    private ImageButton backButton;
    private KProgressHUD requestDialog = null;

    @BindView(R.id.fragment_detail_imgPoster)
    ImageView moviePoster;
    @BindView(R.id.fragment_detail_movie_release_date)
    TextView movieReleaseDate;
    @BindView(R.id.fragment_detail_movie_overview)
    TextView movieOverview;
    @BindView(R.id.fragment_detail_horizontal_layout)
    LinearLayout movieGenresLayout;
    @BindView(R.id.fragment_detail_movie_budget)
    TextView movieBudget;
    @BindView(R.id.fragment_detail_movie_revenue)
    TextView movieRevenue;
    @BindView(R.id.fragment_detail_movie_overrate_button)
    Button movieOverrate;


    public DetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(fLayout == null){
            fLayout = inflater.inflate(R.layout.fragment_detail, container, false);
            ButterKnife.bind(this, fLayout);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null)
            movie = bundle.getParcelable("MovieDetail");


        init();
        getMovieDetail();

        return fLayout;
    }



    private void init(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        setActionBarTitle(movie.getTitle());
    }

    private void setActionBarTitle(String movieTitle){
        View view =((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView();
        title = view.findViewById(R.id.custom_action_bar_title);
        backButton = view.findViewById(R.id.custom_action_bar_back);

        title.setText(movieTitle);
        backButton.setVisibility(View.VISIBLE);
        backButton.setEnabled(true);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backButton.setVisibility(View.INVISIBLE);
                backButton.setEnabled(false);
                getFragmentManager().popBackStack();
            }
        });
    }


    private void getMovieDetail(){

        dialogGoster();

        apiInterface.getMovieDetail(movie.getId(), getString(R.string.api_key)).enqueue(new Callback<MovieDetailModel>() {
            @Override
            public void onResponse(Call<MovieDetailModel> call, Response<MovieDetailModel> response) {

                if (response.isSuccessful()) {

                    movieDetail = new MovieDetailModel();
                    movieDetail = response.body();

                    bilgileriDoldur();
                    dialogGosterme();
                } else {
                    Toast.makeText(getContext(), "Bir şeyler Ters Gitti", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetailModel> call, Throwable t) {
                Toast.makeText(getContext(), "Bir şeyler Ters Gitti", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void bilgileriDoldur(){
        Picasso.get().load(getString(R.string.poster_base_url) + movie.getPosterPath()).into(moviePoster);
        movieReleaseDate.setText(movie.getReleaseDate());
        movieOverview.setText(movie.getOverview());

        genreCreate(movieDetail.getGenres());

        movieBudget.setText(convertMoney(movieDetail.getBudget()));
        movieRevenue.setText(convertMoney(movieDetail.getRevenue()));
        movieOverrate.setText(movieDetail.getVoteAverage().toString() + "%");
    }


    private String convertMoney(int money){
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
        return n.format(money / 100.0);
    }

    private void genreCreate(List<Genre> genreList){

        for(int i=0; i<genreList.size(); i++){
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMarginEnd(25);

            TextView textView = new TextView(getContext());
            textView.setLayoutParams(llp);
            textView.setPadding(10,5,10,5);
            textView.setBackgroundResource(R.drawable.textview_style_with_border);
            textView.setText(genreList.get(i).getName());

            movieGenresLayout.addView(textView);

        }

    }



    private void dialogGoster(){

        if(requestDialog == null) {
            requestDialog = KProgressHUD.create(getContext())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(getString(R.string.dialog_label))
                    .setDetailsLabel(getString(R.string.dialog_detail_label))
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        }
    }

    private void dialogGosterme(){
        if(requestDialog != null) {
            if (requestDialog.isShowing()) {
                requestDialog.dismiss();
                requestDialog = null;
            }
        }
    }


}
