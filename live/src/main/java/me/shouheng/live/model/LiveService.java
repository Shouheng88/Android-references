package me.shouheng.live.model;

import me.shouheng.live.model.data.AppStart;
import me.shouheng.live.model.data.Recommend;
import me.shouheng.live.model.data.Room;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author shouh
 * @version $Id: LiveService, v 0.1 2018/6/8 21:49 shouh Exp$
 */
public interface LiveService {

    @GET("json/page/app-data/info.json?v=3.0.1&os=1&ver=4")
    Observable<AppStart> getAppStartInfo();

    @GET("json/app/index/recommend/list-android.json?v=3.0.1&os=1&ver=4")
    Observable<Recommend> getRecommend();

    @GET("json/rooms/{uid}/info.json?v=3.0.1&os=1&ver=4")
    Observable<Room> enterRoom(@Path("uid") String uid);
}
