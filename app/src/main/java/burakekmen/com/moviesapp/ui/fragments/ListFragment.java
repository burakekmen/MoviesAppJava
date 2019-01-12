package burakekmen.com.moviesapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import burakekmen.com.moviesapp.R;
import burakekmen.com.moviesapp.adapters.RcListAdapter;
import burakekmen.com.moviesapp.models.DiscoverModel;
import burakekmen.com.moviesapp.models.ResultModel;
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
    private GridLayoutManager layoutManager;
    private static final int COLUMN_NUM = 1;
    private boolean isLoading = false;
    private boolean dahaFazla = true;
    private RcListAdapter adapter;

    private KProgressHUD requestDialog = null;
    private boolean ilkYuklemeMi = true;

    @BindView(R.id.fragment_list_rcList)
    RecyclerView rcList;

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
        View view =((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView();
        TextView title = view.findViewById(R.id.custom_action_bar_title);
        title.setText(R.string.list_fragment_title);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        layoutManager = new GridLayoutManager(getActivity(), COLUMN_NUM);
        rcList.setLayoutManager(layoutManager);
        adapter = new RcListAdapter(getContext(), new ArrayList<ResultModel>());
        rcList.setAdapter(adapter);

        rcList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int toplamResim = layoutManager.getItemCount();
                int sonResimPosition = layoutManager.findLastVisibleItemPosition();
                if (dahaFazla && !isLoading && toplamResim - 1 != sonResimPosition) {
                    getDiscoverMoview();
                }
            }
        });
    }


    private void dialogGoster(){

        ilkYuklemeMi = false;

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


    private void listeyeGonder() {

        adapter.addAll(discoverList.getResults());
        adapter.notifyDataSetChanged();
        isLoading = false;
    }


    private void getDiscoverMoview(){

        if(ilkYuklemeMi)
            dialogGoster();

        isLoading = true;
        int toplamResim = layoutManager.getItemCount();
        final int page = toplamResim / 20 + 1;

        if(page < 5) {
            apiInterface.getDiscoverLists(getString(R.string.api_key), page).enqueue(new Callback<DiscoverModel>() {
                @Override
                public void onResponse(Call<DiscoverModel> call, Response<DiscoverModel> response) {

                    if (response.isSuccessful()) {
                        discoverList = new DiscoverModel();
                        discoverList.getResults().addAll(response.body().getResults());

                        listeyeGonder();
                        dialogGosterme();
                    } else {
                        Toast.makeText(getContext(), "Bir şeyler Ters Gitti", Toast.LENGTH_SHORT).show();
                        dialogGosterme();
                    }
                }

                @Override
                public void onFailure(Call<DiscoverModel> call, Throwable t) {
                    Toast.makeText(getContext(), "Bir şeyler Ters Gitti", Toast.LENGTH_SHORT).show();
                    dialogGosterme();
                }
            });
        }
    }

}
