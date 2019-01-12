package burakekmen.com.moviesapp.ui.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import burakekmen.com.moviesapp.R;
import burakekmen.com.moviesapp.ui.fragments.ListFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @BindView(R.id.activity_base_layout)
    LinearLayout baseLayout;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        ButterKnife.bind(this);
        acilisAyarlariniYap();
    }

    private void acilisAyarlariniYap(){

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment listFragment = new ListFragment();
        fragmentTransaction.add(R.id.activity_base_container, listFragment);
        fragmentTransaction.commit();

    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            View view = getSupportActionBar().getCustomView();
            ImageView backButton = view.findViewById(R.id.custom_action_bar_back);
            backButton.setEnabled(false);
            backButton.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;

            Snackbar snackbar = Snackbar.make(baseLayout, "Çıkış için tekrar Geri tuşuna basınız!", Snackbar.LENGTH_SHORT);
            snackbar.show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }
}
