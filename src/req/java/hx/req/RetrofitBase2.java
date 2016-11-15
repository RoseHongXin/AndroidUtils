package hx.req;

import android.app.Activity;

import com.orhanobut.dialogplus.DialogPlus;

import hx.req.bean.RespBase;
import rx.Observable;

/**
 * Created by Rose on 11/14/2016.
 */
public abstract class RetrofitBase2 {

    protected <T> Observable<T> request(Observable<RespBase<T>> observable){
        return request(observable, null);
    }
    protected <T> Observable<T> request(Observable<RespBase<T>> observable, DialogPlus dialog){
        return request(observable, dialog, null);
    }
    protected abstract <T> Observable<T> request(Observable<RespBase<T>> observable, DialogPlus dialog, Callback cb);

    protected interface Callback {
        void onResp(int code);
    }
}
