package app.sample.adapters.di;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import app.sample.adapters.Constants;
import app.sample.adapters.api.ApiService;
import app.sample.adapters.db.UserDao;
import app.sample.adapters.db.UserDatabase;
import app.sample.adapters.utils.LiveDataCallAdapterFactory;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    UserDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, UserDatabase.class, "user_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(UserDatabase database) {
        return database.userDao();
    }

    @Singleton
    @Provides
    ApiService provideApiService() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(new OkHttpClient().newBuilder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(ApiService.class);
    }
}

