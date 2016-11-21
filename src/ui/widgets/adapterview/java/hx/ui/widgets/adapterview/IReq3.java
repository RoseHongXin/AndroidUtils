package hx.ui.widgets.adapterview;

import hx.req.bean.Pager;
import rx.Observable;

/**
 * Created by Rose on 11/17/2016.
 *
 * get the request sequence, data loading has been handled within operator flatMap..
 *
 */

public interface IReq3<T> {
    Observable getReqTrigger(boolean refresh);
}
