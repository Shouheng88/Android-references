package me.shouheng.references.view.main;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutInfo.Builder;
import android.content.pm.ShortcutManager;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
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

import java.util.Arrays;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.tools.PalmUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityMainBinding;

public class MainActivity extends CommonActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        ARouter.getInstance().build(BaseConstants.INTRO).navigation();

        getBinding().barLayout.toolbar.setTitle(R.string.menu_item_desc_0);
        getBinding().barLayout.toolbar.setTitleTextColor(Color.BLACK);

        setupDrawer(savedInstanceState);

        createShortcut(this);

        createPinnedShortcut(this);

        getBinding().btnOpenQq.setOnClickListener(v -> {
            boolean succeed = joinQQGroup("J7Yn-X7oWh-kHljVnxc87WhoyaXns5BY");
            LogUtils.d(succeed);
        });
    }

    /****************
     *
     * 发起添加群流程。群号：天天记账用户交流群(881808089) 的 key 为： J7Yn-X7oWh-kHljVnxc87WhoyaXns5BY
     * 调用 joinQQGroup(J7Yn-X7oWh-kHljVnxc87WhoyaXns5BY) 即可发起手Q客户端申请加群 天天记账用户交流群(881808089)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
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
                .withSelectedTextColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectedIconColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectable(true); // it the item has selected state
        PrimaryDrawerItem drawerItem1 = new PrimaryDrawerItem()
                .withName(R.string.menu_item_title_1)
                .withDescription(R.string.menu_item_desc_1)
                .withIcon(GoogleMaterial.Icon.gmd_featured_video)
                .withIdentifier(1)
                .withSelectedTextColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectedIconColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectable(false);
        PrimaryDrawerItem drawerItem2 = new PrimaryDrawerItem()
                .withName(R.string.menu_item_title_2)
                .withDescription(R.string.menu_item_desc_2)
                .withIcon(FontAwesome.Icon.faw_gamepad)
                .withIdentifier(2)
                .withSelectedTextColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectedIconColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectable(false);
        PrimaryDrawerItem drawerItem3 = new PrimaryDrawerItem()
                .withName(R.string.menu_item_title_3)
                .withDescription(R.string.menu_item_desc_3)
                .withIcon(FontAwesome.Icon.faw_paper_plane)
                .withIdentifier(3)
                .withSelectedTextColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectedIconColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectable(false);
        PrimaryDrawerItem drawerItem4 = new PrimaryDrawerItem()
                .withName(R.string.menu_item_title_4)
                .withDescription(R.string.menu_item_desc_4)
                .withIcon(FontAwesome.Icon.faw_app_store_ios)
                .withIdentifier(4)
                .withSelectedTextColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectedIconColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectable(false);
        PrimaryDrawerItem drawerItem5 = new PrimaryDrawerItem()
                .withName(R.string.menu_item_title_5)
                .withDescription(R.string.menu_item_desc_5)
                .withIcon(FontAwesome.Icon.faw_accusoft)
                .withIdentifier(5)
                .withSelectedTextColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectedIconColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectable(false);
        PrimaryDrawerItem drawerItem6 = new PrimaryDrawerItem()
                .withName(R.string.menu_item_title_6)
                .withDescription(R.string.menu_item_desc_6)
                .withIcon(FontAwesome.Icon.faw_ban)
                .withIdentifier(6)
                .withSelectedTextColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectedIconColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectable(false);
        PrimaryDrawerItem drawerItem7 = new PrimaryDrawerItem()
                .withName(R.string.menu_item_title_7)
                .withDescription(R.string.menu_item_desc_7)
                .withIcon(FontAwesome.Icon.faw_address_book)
                .withIdentifier(7)
                .withSelectedTextColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectedIconColor(PalmUtils.getColorCompact(R.color.accent))
                .withSelectable(false);
//        ExpandableBadgeDrawerItem drawerItem3 = new ExpandableBadgeDrawerItem()
//                .withName(R.string.menu_item_title_3)
//                .withDescription(R.string.menu_item_desc_3)
//                .withIcon(FontAwesome.Icon.faw_paper_plane)
//                .withSelectable(false)
//                .withSubItems(
//                        new SecondaryDrawerItem().withName(R.string.menu_item_sub_title_1)
//                                .withSelectable(false)
//                                .withDescription(R.string.menu_item_sub_desc_1)
//                                .withLevel(2)
//                                .withIcon(GoogleMaterial.Icon.gmd_navigation)
//                                .withIdentifier(2000),
//                        new SecondaryDrawerItem().withName(R.string.menu_item_sub_title_2)
//                                .withSelectable(false)
//                                .withDescription(R.string.menu_item_sub_desc_2)
//                                .withLevel(2)
//                                .withIcon(GoogleMaterial.Icon.gmd_format_bold)
//                                .withIdentifier(2001),
//                        new SecondaryDrawerItem().withName(R.string.menu_item_sub_title_3)
//                                .withSelectable(false)
//                                .withDescription(R.string.menu_item_sub_desc_3)
//                                .withLevel(2)
//                                .withIcon(GoogleMaterial.Icon.gmd_format_bold)
//                                .withIdentifier(2002),
//                        new SecondaryDrawerItem().withName(R.string.menu_item_sub_title_4)
//                                .withSelectable(false)
//                                .withDescription(R.string.menu_item_sub_desc_4)
//                                .withLevel(2)
//                                .withIcon(GoogleMaterial.Icon.gmd_format_bold)
//                                .withIdentifier(2003),
//                        new SecondaryDrawerItem().withName(R.string.menu_item_sub_title_5)
//                                .withSelectable(false)
//                                .withDescription(R.string.menu_item_sub_desc_5)
//                                .withLevel(2)
//                                .withIcon(GoogleMaterial.Icon.gmd_format_bold)
//                                .withIdentifier(2004)
//                );

        new DrawerBuilder()
                .withActivity(this)
                .withHasStableIds(true)
                .addDrawerItems(drawerItem0, drawerItem1, drawerItem2, drawerItem3, drawerItem4,
                        drawerItem5, drawerItem6, drawerItem7)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (drawerItem == null) return false;
                    switch ((int) drawerItem.getIdentifier()) {
                        case 0: break;
                        case 1:
                            ARouter.getInstance()
                                    .build(BaseConstants.LIVE_HOME)
                                    .navigation();
                            break;
                        case 2:
                            ARouter.getInstance()
                                    .build(BaseConstants.GUOKR_NEWS)
                                    .navigation();
                            break;
                        case 3:
                            ARouter.getInstance()
                                    .build(BaseConstants.LAYOUT_MENU)
                                    .navigation();
                            break;
                        case 4:
                            ARouter.getInstance()
                                    .build(BaseConstants.LIBRARY_MENU)
                                    .navigation();
                            break;
                        case 5:
                            ARouter.getInstance()
                                    .build(BaseConstants.ANIMATIONS_MENU)
                                    .navigation();
                            break;
                        case 6:
                            ARouter.getInstance()
                                    .build(BaseConstants.EYEPETIZER_MENU)
                                    .navigation();
                            break;
                        case 7:
                            ARouter.getInstance()
                                    .build(BaseConstants.ADVANCED_MENU)
                                    .navigation();
                            break;
                    }
                    return false;
                })
                .withAccountHeader(header)
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();
    }

    private void createShortcut(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
            ShortcutInfo shortcut = new ShortcutInfo.Builder(context, "id1")
                    .setShortLabel("Website")
                    .setLongLabel("Open the website")
                    .setIcon(Icon.createWithResource(context, R.drawable.ic_account_circle_black_24dp))
                    .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mysite.example.com/")))
                    .build();

            shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
        }
    }

    private void createPinnedShortcut(Context context) {
        if (VERSION.SDK_INT >= VERSION_CODES.N_MR1) {
            ShortcutManager mShortcutManager = context.getSystemService(ShortcutManager.class);

            if (VERSION.SDK_INT >= VERSION_CODES.O) {
                if (mShortcutManager.isRequestPinShortcutSupported()) {
                    // Assumes there's already a shortcut with the ID "my-shortcut".
                    // The shortcut must be enabled.
                    ShortcutInfo pinShortcutInfo = new Builder(context, "my-shortcut")
                            .setShortLabel("Pined shortcut")
                            .setLongLabel("Pined shortcut Pined shortcut")
                            .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mysite.example.com/")))
                            .setIcon(Icon.createWithResource(context, R.drawable.ic_account_circle_black_24dp))
                            .build();

                    // Create the PendingIntent object only if your app needs to be notified
                    // that the user allowed the shortcut to be pinned. Note that, if the
                    // pinning operation fails, your app isn't notified. We assume here that the
                    // app has implemented a method called createShortcutResultIntent() that
                    // returns a broadcast intent.
                    Intent pinnedShortcutCallbackIntent = mShortcutManager.createShortcutResultIntent(pinShortcutInfo);

                    // Configure the intent so that your app's broadcast receiver gets
                    // the callback successfully.For details, see PendingIntent.getBroadcast().
                    PendingIntent successCallback = PendingIntent.getBroadcast(context, /* request code */ 0,
                            pinnedShortcutCallbackIntent, /* flags */ 0);

                    mShortcutManager.requestPinShortcut(pinShortcutInfo, successCallback.getIntentSender());
                }
            }
        }
    }
}
