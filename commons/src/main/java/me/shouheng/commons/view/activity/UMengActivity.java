package me.shouheng.commons.view.activity;

import android.os.Bundle;

import com.umeng.message.PushAgent;

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version $Id: UMengActivity, v 0.1 2018/12/26 23:47 shouh Exp$
 */
public class UMengActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
    }
}
