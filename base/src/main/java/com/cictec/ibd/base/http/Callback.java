package com.cictec.ibd.base.http;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/3/18
 */
public interface Callback<T> {


    /**
     * 回调成功的
     *
     * @param t
     */
    public void onCallback(T t);


}
