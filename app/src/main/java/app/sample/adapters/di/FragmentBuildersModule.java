package app.sample.adapters.di;

import app.sample.adapters.ui.about.AboutFragment;
import app.sample.adapters.ui.users.UsersFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract UsersFragment contributeUsersFragment();

    @ContributesAndroidInjector
    abstract AboutFragment contributeAboutFragment();
}
