package app.sample.adapters.repo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import app.sample.adapters.AppExecutors;
import app.sample.adapters.api.ApiResponse;
import app.sample.adapters.api.ApiService;
import app.sample.adapters.vo.Resource;
import app.sample.adapters.db.User;
import app.sample.adapters.db.UserDao;
import app.sample.adapters.utils.RateLimiter;

@Singleton
public class UserRepository {

    private final ApiService apiService;
    private final UserDao userDao;
    private final AppExecutors appExecutors;

    private RateLimiter<String> usersRateLimit = new RateLimiter<>(5, TimeUnit.MINUTES);

    @Inject
    public UserRepository(AppExecutors appExecutors, UserDao userDao, ApiService apiService) {
        this.appExecutors = appExecutors;
        this.userDao = userDao;
        this.apiService = apiService;
    }

    public LiveData<Resource<List<User>>> getUsers() {
        return new NetworkBoundResource<List<User>,List<User>>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull List<User> users) {
                userDao.insert(users);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<User> data) {
                return data == null || usersRateLimit.shouldFetch("users_list");
            }

            @NonNull
            @Override
            protected LiveData<List<User>> loadFromDb() {
                return userDao.getUsers();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<User>>> createCall() {
                return apiService.getUsers();
            }
        }.asLiveData();
    }
}
