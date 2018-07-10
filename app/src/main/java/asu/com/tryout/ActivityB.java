package asu.com.tryout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ActivityB extends AppCompatActivity {

    public static final String KEY_FRAG = "frag"; // tells activity which fragment gets the args
    public static final String KEY_ARGS = "args";
    public static final String KEY_MY_PROPERTY = "myProperty";

    Button goBack,send;
    EditText ed;
    String saved;
    act2fragInterface finterface;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        args = new Bundle();

        ed = findViewById(R.id.textEntered);
        saved = ed.getText().toString();

        send = findViewById(R.id.send2frag);
        send.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {

                    saved = ed.getText().toString();

                    //finterface.sendThis(saved);
                    args.putString(KEY_FRAG, "frag1Tag"); // which fragment gets the data
                    args.putCharSequence(KEY_MY_PROPERTY, saved); // the data to send
                }
            });




        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {

                    android.content.Intent intent = new android.content.Intent();
                intent.setClass(ActivityB.this, MainActivity.class);
                intent.putExtra(KEY_ARGS,args);
                startActivity(intent);
                ActivityB.this.finish();

                }
            });


    }

    public interface act2fragInterface {
        // TODO: Update argument type and name
        void sendThis(String a);
    }


}
