package burakekmen.com.moviesapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import burakekmen.com.moviesapp.R;
import burakekmen.com.moviesapp.adapters.RcListAdapter;
import burakekmen.com.moviesapp.models.DiscoverModel;
import burakekmen.com.moviesapp.network.ApiClient;
import burakekmen.com.moviesapp.network.ApiInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment {

    private View fLayout;
    private DiscoverModel discoverList = null;
    private ApiInterface apiInterface = null;

    @BindView(R.id.fragment_list_rcList) RecyclerView rcList;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(fLayout == null) {
            fLayout = inflater.inflate(R.layout.fragment_list, container, false);
            ButterKnife.bind(this, fLayout);
        }

        init();
        getDiscoverMoview();

        return fLayout;
    }


    private void init(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcList.setLayoutManager(linearLayoutManager);
        rcList.setItemAnimator(new DefaultItemAnimator());
        rcList.setHasFixedSize(true);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }


    private void listeyeGonder() {
        RcListAdapter adapter = new RcListAdapter(getContext(), discoverList.getResults());
        rcList.setAdapter(adapter);
    }


    private void getDiscoverMoview(){

        apiInterface.getDiscoverLists(getString(R.string.api_key), 1).enqueue(new Callback<DiscoverModel>() {
            @Override
            public void onResponse(Call<DiscoverModel> call, Response<DiscoverModel> response) {

                if(response.isSuccessful()) {
                    discoverList = new DiscoverModel();
                    discoverList = response.body();

                    listeyeGonder();
                }else {
                    Toast.makeText(getContext(), "Bir şeyler Ters Gitti", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DiscoverModel> call, Throwable t) {
                Toast.makeText(getContext(), "Bir şeyler Ters Gitti", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
