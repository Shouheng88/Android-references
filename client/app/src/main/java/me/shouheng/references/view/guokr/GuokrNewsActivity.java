package me.shouheng.references.view.guokr;

import android.os.Bundle;

import javax.inject.Inject;

import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityGuokrBewsBinding;
import me.shouheng.references.view.CommonDaggerActivity;
import me.shouheng.references.viewmodel.GuokrViewModel;

public class GuokrNewsActivity extends CommonDaggerActivity<ActivityGuokrBewsBinding> {

    @Inject
    GuokrViewModel guokrViewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guokr_bews;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        guokrViewModel.getGuokrNews(3,3).observe(this, guokrNewsResource -> {
        });
    }
}
