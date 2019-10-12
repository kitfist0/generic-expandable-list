package app.sample.adapters.ui.about;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import app.sample.adapters.BuildConfig;

public class AboutViewModel extends ViewModel {

    public ObservableField<String> appVersion;

    @Inject
    AboutViewModel() {
        appVersion = new ObservableField<>(BuildConfig.VERSION_NAME);
    }
}