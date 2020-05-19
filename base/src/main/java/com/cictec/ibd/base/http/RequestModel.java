package com.cictec.ibd.base.http;

import java.lang.reflect.Method;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 *     实例的几个方法
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/3/9
 */
public class RequestModel {

    private Method startMethod;

    private Method finishMethod;

    private Method failedMethod;

    private Method successMethod;


    public Method getStartMethod() {
        return startMethod;
    }

    public void setStartMethod(Method startMethod) {
        this.startMethod = startMethod;
    }

    public Method getFinishMethod() {
        return finishMethod;
    }

    public void setFinishMethod(Method finishMethod) {
        this.finishMethod = finishMethod;
    }

    public Method getFailedMethod() {
        return failedMethod;
    }

    public void setFailedMethod(Method failedMethod) {
        this.failedMethod = failedMethod;
    }

    public Method getSuccessMethod() {
        return successMethod;
    }

    public void setSuccessMethod(Method successMethod) {
        this.successMethod = successMethod;
    }
}
