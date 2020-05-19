package com.cictec.ibd.base.utils;

/**
 * <p>
 * Title: BtnClickUtils
 * </p>
 * <p>
 * Description: 防止View重复点击
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author Hankun
 * @date 2015年8月14日
 */
public final class BtnClickUtils {

    private static long mLastClickTime = 0;

    private BtnClickUtils() {

    }

    /**
     * <p>
     * Title: isFastDoubleClick
     * </p>
     * <p>
     * Description: 是否重复点击
     * </p>
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - mLastClickTime;
        if (timeD > 0 && timeD < 300) {
            mLastClickTime = time;
            return true;
        }

        mLastClickTime = time;

        return false;
    }




}
