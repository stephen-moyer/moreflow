package com.moreflow.example.test2;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moreflow.android.view.IAndroidView;
import com.moreflow.android.view.ViewMetadata;
import com.moreflow.autoviewcontroller.ViewController;
import com.moreflow.example.R;
import com.moreflow.example.test.TestController;

@ViewMetadata(layoutId = R.layout.test2)
@ViewController(controller = TestController2.class)
public class TestView2 implements IAndroidView<TestController2>, ITestView2 {

    private TextView textView;
    private Button button;

    @Override
    public void viewLoaded(View view, final TestController2 controller) {
        this.textView = (TextView) view.findViewById(R.id.test_textView);
    }

    @Override
    public void viewDestroyed() {

    }

    @Override
    public void setTestText(String testText) {
        this.textView.setText(testText);
    }

}
