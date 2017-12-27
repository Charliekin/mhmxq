package com.mhm.xq.net.http.rest;

import com.mhm.xq.entity.New;
import com.mhm.xq.entity.News;
import com.mhm.xq.entity.NewsColumns;
import com.mhm.xq.entity.base.BaseEntity;
import com.mhm.xq.entity.greendao.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

public class MyApi {

    //<!--  editor-fold  desc="user validation"  -->

    public static Observable<User> registration(String mobile, String password) {
        return MyRetrofit.getInstance().getApi().registration(mobile, password);
    }

    public static Observable<User> login(String mobile, String password) {
        return MyRetrofit.getInstance().getApi().login(mobile, password);
    }

    public static Observable<User> forgetPassword(String mobile, String password) {
        return MyRetrofit.getInstance().getApi().forgetPassword(mobile, password);
    }

    public static Observable<BaseEntity> uploadUserIcon(MultipartBody.Part icon) {
        return MyRetrofit.getInstance().getApi().uploadUserIcon(icon);
    }

    public static Observable<ResponseBody> downloadUserIcon() {
        return MyRetrofit.getInstance().getApi().downloadUserIcon();
    }

    //<!--  editor-fold  -->

    public static Observable<NewsColumns> getNewsColumn() {
        return MyRetrofit.getInstance().getApi().getNewsColumn();
    }

    public static Observable<ArrayList<New>> getNews(String newsColumnId, int pageIndex, int pageSize) {
        return MyRetrofit.getInstance().getApi().getNews(newsColumnId, pageIndex, pageSize)
                .flatMap(new Function<News, ObservableSource<ArrayList<New>>>() {
                    @Override
                    public ObservableSource<ArrayList<New>> apply(News news) throws Exception {
                        return Observable.just(news.getNews());
                    }
                });
    }
}
