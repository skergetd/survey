/**
 * Copyright (C) futuretek AG 2016
 * All Rights Reserved
 *
 * @author Artan Veliju
 */
package survey.android.futuretek.ch.ft_survey;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class AboutDusanActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
    }

    protected void onResume() {
        super.onResume();

        ((ViewGroup)findViewById(R.id.textLayout)).removeAllViews();
        findViewById(R.id.nextBtn).setVisibility(View.GONE);
        List<String> textArray;

        textArray = new ArrayList<>();
        textArray.add("Hi! I am Dušan...and I am passionate about blockchain and always on search of how to produce best quality code!");
        textArray.add("In past, I picked up quite some languages (verbal as well as programming ones), which all opened new opportunities.");
        textArray.add("Every bit of knowledge that I gather, just stacks up...in a similar sense as blockchain is getting stronger with each block.");
        textArray.add("The more I know, the better I get and armed with all this I am also able to support others with what I know.");
        textArray.add("Lets find the best in us and we will reap sweet fruit in the future!");

        animateText(textArray, new AnimationListDone() {
            public void done() {
                Button nextBtn = ((Button) findViewById(R.id.nextBtn));
                nextBtn.setTextColor(Color.GREEN);
                nextBtn.setEnabled(true);
            }
        });
    }
}