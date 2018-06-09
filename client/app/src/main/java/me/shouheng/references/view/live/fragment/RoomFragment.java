package me.shouheng.references.view.live.fragment;

import android.os.Bundle;

import javax.inject.Inject;

import me.shouheng.references.R;
import me.shouheng.references.databinding.FragmentRoomBinding;
import me.shouheng.references.view.CommonDaggerFragment;
import me.shouheng.references.viewmodel.MainViewModel;

/**
 * @author shouh
 * @version $Id: RoomFragment, v 0.1 2018/6/9 10:59 shouh Exp$
 */
public class RoomFragment extends CommonDaggerFragment<FragmentRoomBinding> {

    @Inject
    MainViewModel mainViewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_room;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {

    }
}
