package app.sample.adapters.ui.users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import app.sample.adapters.vo.Resource;
import app.sample.adapters.db.User;
import app.sample.adapters.repo.UserRepository;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;

    @Inject
    UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    LiveData<Resource<List<User>>> getUsers() {
        return userRepository.getUsers();
    }
}