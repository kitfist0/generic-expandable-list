package app.sample.adapters;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    public void navigateTo(NavDirections navDirections) {
        mNavController.navigate(navDirections);
    }

    public void popBackStack() {
        mNavController.popBackStack();
    }
}
