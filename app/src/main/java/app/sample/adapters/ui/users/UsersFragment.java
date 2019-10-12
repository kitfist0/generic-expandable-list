package app.sample.adapters.ui.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import app.sample.adapters.Constants;
import app.sample.adapters.MainActivity;
import app.sample.adapters.R;
import app.sample.adapters.databinding.UserItemBinding;
import app.sample.adapters.databinding.UsersFragmentBinding;
import app.sample.adapters.db.User;
import app.sample.adapters.utils.GenericExpandableAdapter;
import dagger.android.support.DaggerFragment;

public class UsersFragment extends DaggerFragment implements Toolbar.OnMenuItemClickListener {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    MainActivity mActivity;

    private UsersFragmentBinding mBinding;
    private GenericExpandableAdapter<User, UserItemBinding> mAdapter;
    private UserViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.users_fragment, container, false);
        setupToolbar();
        setupAdapter();
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureViewModel();
    }

    private void configureViewModel() {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);
        mViewModel.getUsers().observe(getViewLifecycleOwner(), resource -> {
            if (resource.data != null) {
                mAdapter.updateItems(resource.data);
            }
        });
    }

    private void setupToolbar() {
        mBinding.toolbar.setNavigationIcon(R.drawable.vd_arrow_back);
        mBinding.toolbar.setNavigationOnClickListener(arrowBack -> mActivity.onBackPressed());
        mBinding.toolbar.inflateMenu(R.menu.options_menu);
        mBinding.toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            mActivity.navigateTo(UsersFragmentDirections.goToAboutFragment());
            return true;
        }
        return false;
    }

    private void setupAdapter() {
        mAdapter = new GenericExpandableAdapter<User, UserItemBinding>(R.layout.user_item) {
            @Override
            public int getItemIdKey(User user) {
                return user.getId(); // Define item identifier here
            }
            @Override
            public boolean contentsAreSameWhen(User newUser, User oldUser) {
                return false;
            }
            @Override
            public void onBindData(User user, int pos, boolean expanded, UserItemBinding binding) {
                binding.setUser(user); // Provide model into item
            }
            @Override
            public void onItemExpandedOrCollapsed(User user, boolean expanded, UserItemBinding binding) {
                // Animate views when item expanded/collapsed
                binding.setExpanded(expanded);
                binding.imageExpand.setImageState(expanded ?
                        Constants.STATE_EXPAND : Constants.STATE_COLLAPSE, true);
            }
        };
        mBinding.recyclerView.setAdapter(mAdapter);
    }
}
