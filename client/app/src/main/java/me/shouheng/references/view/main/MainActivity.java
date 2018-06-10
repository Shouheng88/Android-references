package me.shouheng.references.view.main;

import android.content.Intent;
import android.os.Bundle;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityMainBinding;
import me.shouheng.references.view.CommonDaggerActivity;
import me.shouheng.references.view.guokr.GuokrNewsActivity;
import me.shouheng.references.view.intro.AppIntroActivity;
import me.shouheng.references.view.live.activity.LiveActivity;

public class MainActivity extends CommonDaggerActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        startActivity(new Intent(this, AppIntroActivity.class));

        setupDrawer(savedInstanceState);
    }

    private void setupDrawer(Bundle savedInstanceState) {
        IProfile profile = new ProfileDrawerItem()
                .withName(R.string.developer_name)
                .withEmail(R.string.developer_email)
                .withIcon(R.drawable.ic_account)
                .withIdentifier(100);
        ProfileSettingDrawerItem item1 = new ProfileSettingDrawerItem()
                .withName("Add Account")
                .withDescription("Add new GitHub Account")
                .withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBar().paddingDp(5).colorRes(R.color.material_drawer_primary_text))
                .withIdentifier(100000);
        ProfileSettingDrawerItem item2 = new ProfileSettingDrawerItem()
                .withName("Manage Account")
                .withIcon(GoogleMaterial.Icon.gmd_settings)
                .withIdentifier(100001);

        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_bg)
                .addProfiles(profile, item1, item2)
                .build();

        PrimaryDrawerItem drawerItem0 = new PrimaryDrawerItem()
                .withName("Drawer item 1")
                .withDescription("Drawer description 1")
                .withIcon(FontAwesome.Icon.faw_home)
                .withIdentifier(0) // the id of menu item, used when setting click event
                .withSelectable(true); // it the item has selected state
        PrimaryDrawerItem drawerItem1 = new PrimaryDrawerItem()
                .withName(R.string.menu_item_title_1)
                .withDescription(R.string.menu_item_desc_1)
                .withIcon(GoogleMaterial.Icon.gmd_featured_video)
                .withIdentifier(1)
                .withSelectable(false);
        PrimaryDrawerItem drawerItem2 = new PrimaryDrawerItem()
                .withName(R.string.menu_item_title_2)
                .withDescription(R.string.menu_item_desc_2)
                .withIcon(FontAwesome.Icon.faw_gamepad)
                .withIdentifier(2)
                .withSelectable(false);

        new DrawerBuilder()
                .withActivity(this)
                .withHasStableIds(true)
                .addDrawerItems(drawerItem0, drawerItem1, drawerItem2)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (drawerItem == null) return false;
                    switch ((int) drawerItem.getIdentifier()) {
                        case 0: break;
                        case 1: startActivity(LiveActivity.class);break;
                        case 2: startActivity(GuokrNewsActivity.class);break;
                    }
                    return false;
                })
                .withAccountHeader(header)
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();
    }
}
