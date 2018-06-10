package me.shouheng.references.model.guokr;

import me.shouheng.references.model.guokr.data.GuokrNews;
import me.shouheng.references.model.guokr.data.GuokrNewsContent;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author shouh
 * @version $Id: GuokrService, v 0.1 2018/6/10 11:11 shouh Exp$
 */
public interface GuokrService {

    @GET("article.json?retrieve_type=by_minisite")
    Observable<GuokrNews> getNews(@Query("offset") int offset, @Query("limit") int limit);

    @GET("article/{id}.json")
    Observable<GuokrNewsContent> getGuokrContent(@Path("id") int id);
}
