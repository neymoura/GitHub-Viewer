package com.ghost.githubviewer.view.activity;

import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ghost.githubviewer.event.BaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseAct";

    @Override
    public void onStart() {
        super.onStart();
        try{
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }catch (EventBusException e){
            Log.e(TAG, "onStart: ", e);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        try{
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        }catch (EventBusException e) {
            Log.e(TAG, "onStart: ", e);
        }
    }

    @Subscribe
    public void onEvent(BaseEvent event){}

    protected AlertDialog showAlert(@StringRes int resource){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(resource));
        builder.setPositiveButton(android.R.string.ok, null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return alertDialog;

    }

}
