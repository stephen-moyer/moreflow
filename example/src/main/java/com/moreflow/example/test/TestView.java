package com.moreflow.example.test;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moreflow.android.view.IAndroidView;
import com.moreflow.android.view.ViewMetadata;
import com.moreflow.autoviewcontroller.ViewController;
import com.moreflow.example.R;

@ViewMetadata(layoutId = R.layout.test1)
@ViewController(controller = TestController.class)
public class TestView implements IAndroidView<TestController>, ITestView {

    private TextView textView;
    private Button button;

    @Override
    public void viewLoaded(View view, final TestController controller) {
        this.textView = (TextView) view.findViewById(R.id.test_textView);
        this.button = (Button) view.findViewById(R.id.test_button);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigateToTwo();
            }
        });
    }

    @Override
    public void viewDestroyed() {

    }

    @Override
    public void setTestText(String testText) {
        this.textView.setText(testText);
    }

}
