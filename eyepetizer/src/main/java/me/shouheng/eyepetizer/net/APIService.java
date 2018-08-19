package me.shouheng.eyepetizer.net;

import io.reactivex.Observable;
import me.shouheng.eyepetizer.mvp.model.bean.HomeBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author shouh
 * @version $Id: APIService, v 0.1 2018/8/19 17:30 shouh Exp$
 */
public interface APIService {

    @GET("v2/feed?&num=1")
    Observable<HomeBean> getFirstHomeData(@Query("date") Long date);

    @GET
    Observable<HomeBean> getMoreHomeData(@Url String url);
}
