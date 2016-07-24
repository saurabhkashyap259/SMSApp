package com.mate.smsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mate.smsapp.fragments.ConversationFragment;
import com.mate.smsapp.fragments.MessageFragment;
import com.mate.smsapp.utils.Constants;
import com.mate.smsapp.utils.GlobalVariables;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;
    private static Fragment previousFragment;
    private static ArrayList<Fragment> fragmentsOnStack = new ArrayList<>();
    private static int backCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, 2);
            return;
        }


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 4);
            return;
        }


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 3);
            return;
        }

        //Local variables
        Toolbar toolbar;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set attribute for app name in toolbar
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        //status bar background color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blue_grey_d900));
        }

        //Fragment Manager
        fragmentManager = getSupportFragmentManager();

        openFragment(Constants.ConversationFragmentCount);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = getIntent();
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(this, "No permission to read external storage.", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 3) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted  start reading
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(this, "No permission to write external storage.", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 4) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted  start reading
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(this, "No permission to write external storage.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void goToInbox(View view) {
        Intent intent = new Intent(MainActivity.this, ReceiveSmsActivity.class);
        startActivity(intent);
    }

    public void goToCompose(View view) {
        Intent intent = new Intent(MainActivity.this, SendSmsActivity.class);
        startActivity(intent);
    }

    public static void openFragment(int fragmentCount) {

        switch (fragmentCount) {

            case Constants.ConversationFragmentCount:
                openFragment(new ConversationFragment());
                break;

            case Constants.MessageFragmentCount:
                openFragment(new MessageFragment());
                break;

            default:
                break;
        }
    }

    public static void openFragment(Fragment frag) {

        //back pressed
        backCount = 1;

        // frame.removeAllViews();
        if (previousFragment != null) {

            Log.e(TAG, "Previous Fragment: " + previousFragment);
            destroyFragment(previousFragment);

        }

        if (fragmentsOnStack.size() > 0) {
            for (int i = 0; i < fragmentsOnStack.size(); i++) {
                destroyFragment(fragmentsOnStack.get(i));
            }

            fragmentsOnStack = new ArrayList<>();
        }

        fragmentTransaction = fragmentManager.beginTransaction();
        // /ft.setCustomAnimations(R.anim.slide_in_right,
        // R.anim.slide_out_left);
        fragmentTransaction.replace(R.id.container_body, frag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        fragmentTransaction.commit();
        previousFragment = frag;

    }

    public static void destroyFragment(Fragment frag) {
        // frame.removeAllViews();

        Log.e(TAG, "Destroy Fragment: " + frag);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(frag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        invalidateOptionsMenu();

        switch(GlobalVariables.fragmentNumber){

            case 0:

                if(backCount == 1){

                    Toast.makeText(this, "Press back again to exit the app", Toast.LENGTH_SHORT).show();
                    backCount++;
                }else{

                    MainActivity.this.finish();
                }
                break;

            case 1:
                openFragment(Constants.ConversationFragmentCount);
                break;

            default:
                break;
        }
    }
}
