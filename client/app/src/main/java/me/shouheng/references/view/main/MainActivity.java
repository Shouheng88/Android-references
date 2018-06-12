package me.shouheng.references.view.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableBadgeDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityMainBinding;
import me.shouheng.references.view.CommonDaggerActivity;
import me.shouheng.references.view.guokr.GuokrNewsActivity;
import me.shouheng.references.view.intro.AppIntroActivity;
import me.shouheng.references.view.layout.bottomsheet.BottomSheetActivity;
import me.shouheng.references.view.layout.collapse.CollapseBarStructure;
import me.shouheng.references.view.layout.navigation.NavigationActivity;
import me.shouheng.references.view.layout.scrolling.ScrollingActivity;
import me.shouheng.references.view.layout.tabbed.TabbedActivity;
import me.shouheng.references.view.live.activity.LiveActivity;

public class MainActivity extends CommonDaggerActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        startActivity(new Intent(this, AppIntroActivity.class));

        getBinding().barLayout.toolbar.setTitle(R.string.menu_item_desc_0);
        getBinding().barLayout.toolbar.setTitleTextColor(Color.BLACK);

        setupDrawer(savedInstanceState);
    }

    private void setupDrawer(Bundle savedInstanceState) {
        IProfile profile = new ProfileDrawerItem()
                .withName(R.string.developer_name)
                .withEmail(R.string.developer_email)
                .withIcon(R.drawable.ic_account)
                .withIdentifier(100);
        ProfileSettingDrawerItem item1 = new ProfileSettingDrawerItem()
                .withName(R.string.add_account)
                .withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBar().paddingDp(5).colorRes(R.color.material_drawer_primary_text))
                .withIdentifier(100000);
        ProfileSettingDrawerItem item2 = new ProfileSettingDrawerItem()
                .withName(R.string.manage_account)
                .withIcon(GoogleMaterial.Icon.gmd_settings)
                .withIdentifier(100001);

        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_bg)
                .addProfiles(profile, item1, item2)
                .build();

        PrimaryDrawerItem drawerItem0 = new PrimaryDrawerItem()
                .withName(R.string.menu_item_title_0)
                .withDescription(R.string.menu_item_desc_0)
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
        ExpandableBadgeDrawerItem drawerItem3 = new ExpandableBadgeDrawerItem()
                .withName(R.string.menu_item_title_3)
                .withDescription(R.string.menu_item_desc_3)
                .withIcon(FontAwesome.Icon.faw_paper_plane)
                .withSelectable(false)
                .withSubItems(
                        new SecondaryDrawerItem().withName(R.string.menu_item_sub_title_1)
                                .withSelectable(false)
                                .withDescription(R.string.menu_item_sub_desc_1)
                                .withLevel(2)
                                .withIcon(GoogleMaterial.Icon.gmd_navigation)
                                .withIdentifier(2000),
                        new SecondaryDrawerItem().withName(R.string.menu_item_sub_title_2)
                                .withSelectable(false)
                                .withDescription(R.string.menu_item_sub_desc_2)
                                .withLevel(2)
                                .withIcon(GoogleMaterial.Icon.gmd_format_bold)
                                .withIdentifier(2001),
                        new SecondaryDrawerItem().withName(R.string.menu_item_sub_title_3)
                                .withSelectable(false)
                                .withDescription(R.string.menu_item_sub_desc_3)
                                .withLevel(2)
                                .withIcon(GoogleMaterial.Icon.gmd_format_bold)
                                .withIdentifier(2002),
                        new SecondaryDrawerItem().withName(R.string.menu_item_sub_title_4)
                                .withSelectable(false)
                                .withDescription(R.string.menu_item_sub_desc_4)
                                .withLevel(2)
                                .withIcon(GoogleMaterial.Icon.gmd_format_bold)
                                .withIdentifier(2003),
                        new SecondaryDrawerItem().withName(R.string.menu_item_sub_title_5)
                                .withSelectable(false)
                                .withDescription(R.string.menu_item_sub_desc_5)
                                .withLevel(2)
                                .withIcon(GoogleMaterial.Icon.gmd_format_bold)
                                .withIdentifier(2004)
                );

        new DrawerBuilder()
                .withActivity(this)
                .withHasStableIds(true)
                .addDrawerItems(drawerItem0, drawerItem1, drawerItem2, drawerItem3)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (drawerItem == null) return false;
                    switch ((int) drawerItem.getIdentifier()) {
                        case 0: break;
                        case 1: startActivity(LiveActivity.class);break;
                        case 2: startActivity(GuokrNewsActivity.class);break;
                        case 2000: startActivity(NavigationActivity.class);break;
                        case 2001: startActivity(TabbedActivity.class);break;
                        case 2002: startActivity(BottomSheetActivity.class);break;
                        case 2003: startActivity(ScrollingActivity.class);break;
                        case 2004: startActivity(CollapseBarStructure.class);break;
                    }
                    return false;
                })
                .withAccountHeader(header)
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();
    }
}
