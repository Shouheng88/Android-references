package me.shouheng.eyepetizer.mvp.contract;

import java.util.List;

import me.shouheng.eyepetizer.mvp.base.BasePresenter;
import me.shouheng.eyepetizer.mvp.base.BaseView;
import me.shouheng.eyepetizer.mvp.model.bean.HomeBean;

/**
 * @author shouh
 * @version $Id: HomeContract, v 0.1 2018/8/19 17:10 shouh Exp$
 */
public interface HomeContract {

    interface IView extends BaseView {
        void setFirstPage(List<HomeBean.IssueList.ItemList> itemLists);
        void setNextPage(List<HomeBean.IssueList.ItemList> itemLists);
        void onError(String msg);
    }

    interface IPresenter extends BasePresenter {
        void requestFirstPage();
        void requestNextPage();
    }
}
