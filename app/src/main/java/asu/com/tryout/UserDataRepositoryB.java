package asu.com.tryout;

import android.os.Handler;
import java.util.Observable;

public class UserDataRepositoryB extends Observable {
    private String mFullName;
    private int mAge;
    private static UserDataRepositoryB INSTANCE = null;

    private UserDataRepositoryB() {
        getNewDataFromRemote();
    }

    // Returns a single instance of this class, creating it if necessary.
    public static UserDataRepositoryB getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserDataRepositoryB();
        }
        return INSTANCE;
    }

    // Simulate network
    private void getNewDataFromRemote() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setUserData("Mgbemena Chike", 102);
            }
        }, 10000);
    }

    public void setUserData(String fullName, int age) {
        mFullName = fullName;
        mAge = age;
        setChanged();
        notifyObservers();
    }

    public String getFullName() {
        return mFullName;
    }

    public int getAge() {
        return mAge;
    }
}
