package com.cictec.ibd.base.adtapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.cictec.ibd.base.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * <p>
 * PageAdapter的适配器，当需要PageTitle时，需要重写   BaseFragment中的UseName
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-08-23
 */
public final class TabFragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> pageList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public List<BaseFragment> getPageList() {
        return pageList;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setNewData(List<BaseFragment> list) {
        pageList.clear();
        if (!list.isEmpty()) {
            pageList.addAll(list);
        }
        this.notifyDataSetChanged();
    }

    public void setNewData(List<BaseFragment> list, List<String> titles) {
        pageList.clear();
        titleList.clear();
        if (!list.isEmpty()) {
            pageList.addAll(list);
        }
        if (!titles.isEmpty()) {
            titleList.addAll(titles);
        }
        this.notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return pageList.isEmpty() ? null : pageList.get(position);
    }

    @Override
    public int getCount() {
        return pageList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList.size() > 0) {
            return titleList.get(position);
        } else if (!pageList.isEmpty()) {
            return pageList.get(position).getTabName();
        } else {
            return "";
        }
    }
}
