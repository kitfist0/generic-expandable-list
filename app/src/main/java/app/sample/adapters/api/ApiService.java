package app.sample.adapters.api;

import androidx.lifecycle.LiveData;

import java.util.List;

import app.sample.adapters.db.User;
import retrofit2.http.GET;

/** REST API access points */
public interface ApiService {

    @GET("users")
    LiveData<ApiResponse<List<User>>> getUsers();
}
