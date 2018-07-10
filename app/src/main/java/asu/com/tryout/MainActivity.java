package asu.com.tryout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.net.Uri;

public class MainActivity extends AppCompatActivity implements FragmentA.OnFragmentInteractionListener, FragmentB.OnFragmentInteractionListener,
RecyclerFragment.OnFragmentInteractionListener, RecFragment.OnFragmentInteractionListener, FragmentA.InterfaceMessageIncoming
 {

    Button changeAct;
    Button frag1,serviceCall;
    Button frag2;
    Button recyFrag;
    String sendThis,fragmentMessageHolder;
    Bundle bundle;
    Button observer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processIntent(getIntent());

        //bundle = new Bundle();

        changeAct = findViewById(R.id.changeAct);
        changeAct.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {

                    Intent intent = new Intent();
                intent.setClass(MainActivity.this, ActivityB.class);
                startActivity(intent);
                MainActivity.this.finish();

                }
            });

        serviceCall = findViewById(R.id.service);
        serviceCall.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ServiceActivity.class);
                startActivity(intent);
                MainActivity.this.finish();

            }
        });

        observer = findViewById(R.id.obs);
        observer.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, UserProfileActivityB.class);
                startActivity(intent);
                MainActivity.this.finish();

            }
        });


        frag1 = findViewById(R.id.frag1);
        frag1.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragContainer, new FragmentA(),"fragment1");
                    ft.commit();

                }
            });

        frag2 = findViewById(R.id.frag2);
        frag2.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {

                    FragmentB frag2 = new FragmentB();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragContainer, frag2,"fragment2");
                    ft.commit();
                    //FragmentB frag2 = (FragmentB) getSupportFragmentManager().findFragmentByTag("fragment2");
                    if(frag2 != null){
                        frag2.setThis(sendThis);
                        frag2.fromFrag(fragmentMessageHolder);
                        System.out.println("1: "+sendThis);
                    }else{
                        System.out.println("2: "+sendThis);
                    }


                }
            });


        recyFrag = findViewById(R.id.Rec);
        recyFrag.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragContainer, new RecFragment());
                    ft.commit();

                }
            });

    }


    private void processIntent(Intent intent) {
        Bundle args = intent.getBundleExtra(ActivityB.KEY_ARGS);
        if (args != null) { // check if ActivityB is started to pass data to fragments
            String info = args.getString(ActivityB.KEY_MY_PROPERTY);
            if (info != null) {
                //send info to Fragment 2
                //FragmentB frag2 = (FragmentB) getSupportFragmentManager().findFragmentByTag("fragment2");
                sendThis=info;
                //bundle.putString("key",info);
                //android.widget.Toast.makeText(this, "message: "+info, android.widget.Toast.LENGTH_LONG).show();
        }
    }
    }

      @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent); // this call is in case ActivityB was already running
    }

    public void onFragmentInteraction(Uri uri){

    }

     @Override
     public void sendText(String text){
        fragmentMessageHolder = text;
     }

}
