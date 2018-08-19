package me.shouheng.eyepetizer.mvp.model;

import io.reactivex.Observable;
import me.shouheng.eyepetizer.mvp.model.bean.HomeBean;
import me.shouheng.eyepetizer.net.APIRetrofit;

/**
 * @author shouh
 * @version $Id: HomeModel, v 0.1 2018/8/19 17:27 shouh Exp$
 */
public class HomeModel {

    public Observable<HomeBean> getFirstHomeData() {
        return APIRetrofit.getEyepetizerService().getFirstHomeData(System.currentTimeMillis());
    }

    public Observable<HomeBean> getMoreHomeData(String url) {
        return APIRetrofit.getEyepetizerService().getMoreHomeData(url);
    }
}
