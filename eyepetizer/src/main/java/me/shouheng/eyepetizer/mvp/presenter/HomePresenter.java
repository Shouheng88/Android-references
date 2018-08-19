package me.shouheng.eyepetizer.mvp.presenter;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.eyepetizer.mvp.contract.HomeContract;
import me.shouheng.eyepetizer.mvp.model.HomeModel;
import me.shouheng.eyepetizer.mvp.model.bean.HomeBean;

/**
 * @author shouh
 * @version $Id: HomePresenter, v 0.1 2018/8/19 17:39 shouh Exp$
 */
public class HomePresenter implements HomeContract.IPresenter {

    private HomeContract.IView view;

    private HomeModel homeModel;

    private String nextPageUrl;

    public HomePresenter(HomeContract.IView view) {
        this.view = view;
        homeModel = new HomeModel();
    }

    @Override
    public void requestFirstPage() {
        Disposable disposable = homeModel.getFirstHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(homeBean -> {
                    List<HomeBean.IssueList> issueLists = homeBean.getIssueList();
                    nextPageUrl = homeBean.getNextPageUrl();
                    return issueLists.get(0).getItemList();
                })
                .subscribe(itemLists -> {
                            List<HomeBean.IssueList.ItemList> ret = new LinkedList<>();
                            Observable.fromIterable(itemLists)
                                    .filter(itemList -> "video".equals(itemList.getType()))
                                    .forEach(ret::add);
                            view.setFirstPage(ret);
                        },
                        throwable -> {
                            view.onError(throwable.toString());
                            LogUtils.d(throwable);
                        });
    }

    @Override
    public void requestNextPage() {
        Disposable disposable = homeModel.getMoreHomeData(nextPageUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(homeBean -> {
                    nextPageUrl = homeBean.getNextPageUrl();
                    return homeBean.getIssueList();
                })
                .concatMap((Function<List<HomeBean.IssueList>, ObservableSource<List<HomeBean.IssueList.ItemList>>>) issueLists ->
                        Observable.fromIterable(issueLists).concatMap((Function<HomeBean.IssueList, ObservableSource<List<HomeBean.IssueList.ItemList>>>) issueList ->
                                Observable.just(issueList.getItemList())))
                .subscribe(itemLists -> {
                            List<HomeBean.IssueList.ItemList> ret = new LinkedList<>();
                            Observable.fromIterable(itemLists)
                                    .filter(itemList -> "video".equals(itemList.getType()))
                                    .forEach(ret::add);
                            view.setFirstPage(ret);
                        },
                        throwable -> {
                            view.onError(throwable.toString());
                            LogUtils.d(throwable);
                        });
    }
}
