package com.moreflow.android;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.moreflow.android.flow.Dispatcher;
import com.moreflow.android.flow.InitialState;
import com.moreflow.android.flow.KeyParceler;
import com.moreflow.android.platform.AndroidPlatform;
import com.moreflow.android.platform.AndroidPreferences;
import com.moreflow.core.MoreFlow;
import com.moreflow.core.controller.Controller;
import com.moreflow.core.resolver.IControllerViewResolver;
import com.moreflow.core.resolver.IDependencyResolver;
import com.moreflow.core.view.IView;

import flow.Flow;

public abstract class MoreFlowActivity extends AppCompatActivity {

    public static final String SAVE_LOADED_EXTRA = "com.moreflow.android.SAVE_LOADED_EXTRA";
    public static final String EXTRA_CONTROLLER_CLASS = "com.moreflow.android.EXTRA_CONTROLLER_CLASS";
    public static final String EXTRA_STATE = "com.moreflow.android.EXTRA_STATE";

    private Dispatcher dispatcher;
    private MoreFlow moreFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean loading = tryLoadBundledState();
        if (!loading && Flow.get(this).getHistory().top() == InitialState.class) {
            Flow.get(this).set(defaultKey());
        }
    }

    private boolean tryLoadBundledState() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return false;
        }
        if (extras.containsKey(EXTRA_CONTROLLER_CLASS)) {
            Flow.get(this).set((Class<? extends Controller>)extras.getSerializable(EXTRA_CONTROLLER_CLASS));
        } else if (extras.containsKey(EXTRA_STATE)) {
            Flow.get(this).set(extras.<KeyParceler.KeyParcelable>getParcelable(EXTRA_STATE).getKey());
        } else {
            return false;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //we put true for save loaded extra so when the app comes back in we dont overwrite the flow state
        //TODO i dont think this is needed.
        //outState.putBoolean(SAVE_LOADED_EXTRA, true);
    }

    @Override
    protected void attachBaseContext(Context baseContext) {
        baseContext = initBaseContext(baseContext);
        super.attachBaseContext(baseContext);
    }

    private Context initBaseContext(Context baseContext) {
        dispatcher = new Dispatcher(this);
        return Flow.configure(baseContext, this)
                .dispatcher(dispatcher)
                .defaultKey(InitialState.class)
                .keyParceler(new KeyParceler())
                .install();
    }

    @Override
    public void onBackPressed() {
        if (!Flow.get(this).goBack()) {
            super.onBackPressed();
        }
    }

    public MoreFlow getMoreFlow() {
        if (getApplication() instanceof MoreFlowApplication) {
            return ((MoreFlowApplication) getApplication()).getMoreFlow();
        } else if (moreFlow == null) {
            moreFlow = new MoreFlow(new AndroidPreferences(this.getApplication()), new AndroidPlatform(this.getApplication()));
        }
        return moreFlow;
    }

    @NonNull
    public abstract IDependencyResolver<Controller> controllerResolver();
    @NonNull
    public abstract IDependencyResolver<IView> viewResolver();
    @NonNull
    public abstract IControllerViewResolver controllerViewResolver();

    protected abstract Object defaultKey();

}
