package app.sample.adapters.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import app.sample.adapters.ui.about.AboutViewModel;
import app.sample.adapters.ui.users.UserViewModel;
import app.sample.adapters.utils.ViewModelFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel.class)
    abstract ViewModel bindAboutViewModel(AboutViewModel aboutViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
