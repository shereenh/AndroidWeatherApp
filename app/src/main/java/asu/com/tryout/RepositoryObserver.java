package asu.com.tryout;

public interface RepositoryObserver {
    void onUserDataChanged(String fullname, int age);
}
