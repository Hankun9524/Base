package com.cictec.ibd.base.app;

import com.cictec.ibd.base.cache.ActiveCache;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/3/9
 */
public class HostApplication extends BaseApplication {


    @Override
    protected void initLogic() {
        registerApplication();
    }

    private void registerApplication() {
        for (String path : ActiveCache.getApplicationCache()) {
            try {
                Class cls = Class.forName(path);
                registerApplicationLogic(cls);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
