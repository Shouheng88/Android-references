package me.shouheng.references.view;

import android.os.Bundle;

import me.shouheng.references.R;
import me.shouheng.references.base.CommonDaggerActivity;

public class MainActivity extends CommonDaggerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
