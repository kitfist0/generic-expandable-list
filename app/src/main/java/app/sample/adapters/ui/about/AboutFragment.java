package app.sample.adapters.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import app.sample.adapters.Constants;
import app.sample.adapters.MainActivity;
import app.sample.adapters.R;
import app.sample.adapters.databinding.AboutFragmentBinding;
import dagger.android.support.DaggerFragment;

public class AboutFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    MainActivity mActivity;

    private AboutFragmentBinding mBinding;
    private AboutViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.about_fragment, container, false);
        mBinding.setFragment(this);
        setupToolbar();
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureViewModel();
    }

    private void configureViewModel() {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AboutViewModel.class);
        mBinding.setModel(mViewModel);
    }

    private void setupToolbar() {
        mBinding.toolbar.setNavigationIcon(R.drawable.vd_arrow_back);
        mBinding.toolbar.setNavigationOnClickListener(arrowBack -> mActivity.popBackStack());
    }

    public void onButtonClick(int buttonId) {
        switch (buttonId) {
            case R.id.button_my_server:
                openInBrowser(Constants.BASE_URL);
                break;
            case R.id.button_github:
                openInBrowser(Constants.GITHUB_LINK);
                break;
            case R.id.button_share:
                ShareCompat.IntentBuilder.from(mActivity)
                        .setType("text/plain")
                        .setChooserTitle(getString(R.string.share))
                        .setText(Constants.GITHUB_LINK)
                        .startChooser();
                break;
        }
    }

    private void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
