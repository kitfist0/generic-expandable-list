package app.sample.adapters.di;

import android.app.Application;

import javax.inject.Singleton;

import app.sample.adapters.SampleApp;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules={AndroidSupportInjectionModule.class, ActivityBuildersModule.class, AppModule.class})
public interface AppComponent extends AndroidInjector<SampleApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);
        AppComponent build();
    }
}
