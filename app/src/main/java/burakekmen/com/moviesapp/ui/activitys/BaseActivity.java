package burakekmen.com.moviesapp.ui.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;

import burakekmen.com.moviesapp.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        acilisAyarlariniYap();
    }

    private void acilisAyarlariniYap(){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment listFragment = new ListFragment();
        fragmentTransaction.add(R.id.activity_base_fragment, listFragment);
        fragmentTransaction.commit();
    }
}
