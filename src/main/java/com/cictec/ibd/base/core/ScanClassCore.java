package com.cictec.ibd.base.core;

import android.content.Context;
import android.text.TextUtils;

import com.cictec.ibd.base.cache.ActiveCache;
import com.cictec.ibd.base.utils.FileUtilsKt;
import com.cictec.ibd.base.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hankun.libnavannotation.NavInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 * <p>
 * 启动扫描器，启动时对整个apk进行扫描，对于需要进行登录和路由的活动类进行注册
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/3/12
 */
public class ScanClassCore {


    public static void init(Context context) {
        try {
            long start = System.currentTimeMillis();
            //启动加载Application模块的注册
            String[] strings = context.getAssets().list("");
            Gson gson = new Gson();
            if (strings != null) {
                for (String path : strings) {
                    if (path.contains("$applicationRouter.json")) {
                        //解析内容
                        String content = FileUtilsKt.getAssetsContent(context, path);
                        if (!TextUtils.isEmpty(content)) {
                            List<String> modelList = gson.fromJson(content, new TypeToken<ArrayList<String>>() {
                            }.getType());
                            ActiveCache.setAllApplicationPath(modelList);
                        }
                    } else if (path.contains("$pageRouter.json")) {
                        String content = FileUtilsKt.getAssetsContent(context, path);
                        if (!TextUtils.isEmpty(content)) {
                            Map<String, NavInfo> modelList = gson.fromJson(content, new TypeToken<HashMap<String, NavInfo>>() {
                            }.getType());
                            ActiveCache.setNavInfoMap(modelList);
                        }
                    }
                }
                LogUtil.i("scan time = " + (System.currentTimeMillis() - start) +"ms");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
