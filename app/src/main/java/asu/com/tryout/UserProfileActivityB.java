package asu.com.tryout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class UserProfileActivityB extends AppCompatActivity implements Observer {
    private Observable mUserDataRepositoryObservable;
    private TextView mTextViewUserFullName;
    private TextView mTextViewUserAge;
    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mTextViewUserAge = findViewById(R.id.tv_age);
        mTextViewUserFullName = findViewById(R.id.tv_fullname);

        mUserDataRepositoryObservable = UserDataRepositoryB.getInstance();
        mUserDataRepositoryObservable.addObserver(this);

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                android.content.Intent intent = new android.content.Intent();
                intent.setClass(UserProfileActivityB.this, MainActivity.class);
                startActivity(intent);
                UserProfileActivityB.this.finish();

            }
        });

    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof UserDataRepositoryB) {
            UserDataRepositoryB userDataRepositoryB = (UserDataRepositoryB)observable;
            mTextViewUserAge.setText(String.valueOf(userDataRepositoryB.getAge()));
            mTextViewUserFullName.setText(userDataRepositoryB.getFullName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserDataRepositoryObservable.deleteObserver(this);
    }
}