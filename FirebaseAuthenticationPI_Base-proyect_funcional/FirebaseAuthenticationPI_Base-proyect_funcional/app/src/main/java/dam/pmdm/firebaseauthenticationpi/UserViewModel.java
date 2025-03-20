package dam.pmdm.firebaseauthenticationpi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public void setUser (User user) {
        userLiveData.setValue(user);
    }

    public LiveData<User> getUser () {
        return userLiveData;
    }
}