package com.cinntra.okana.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.cinntra.okana.R;


public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

       // fragment = new Dashboard();
       // loadFragment(OpportunitiesActivity.fragment);
    }

    private boolean loadFragment(Fragment fragment)
      {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag_Frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}