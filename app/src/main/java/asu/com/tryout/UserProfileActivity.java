package asu.com.tryout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity implements RepositoryObserver {
    private Subject mUserDataRepository;
    private TextView mTextViewUserFullName;
    private TextView mTextViewUserAge;
    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mTextViewUserAge = findViewById(R.id.tv_age);
        mTextViewUserFullName = findViewById(R.id.tv_fullname);

        mUserDataRepository = UserDataRepository.getInstance();
        mUserDataRepository.registerObserver(this);

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                android.content.Intent intent = new android.content.Intent();
                intent.setClass(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
                UserProfileActivity.this.finish();

            }
        });
    }

    @Override
    public void onUserDataChanged(String fullname, int age) {
        mTextViewUserFullName.setText(fullname);
        mTextViewUserAge.setText(age);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserDataRepository.removeObserver(this);
    }
}