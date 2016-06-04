package com.zerovoid.whmshoppintcart;

import android.view.View;
import android.view.ViewGroup;

import com.zerovoid.common.view.grouplist.ChildData;
import com.zerovoid.common.view.grouplist.GroupData;
import com.zerovoid.common.view.grouplist.GroupListAdapter;
import com.zerovoid.shoppingcart.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wanghaiming on 2016/6/4.
 */
public class WhmShoppintCartAdapter extends GroupListAdapter<Merchant,Product> {

    public WhmShoppintCartAdapter(List<? extends GroupData<Merchant, Product>> groupDataList) {
        super(groupDataList);
    }

    @Override
    protected View onCreateGroupView(ViewGroup parent, int groupViewType) {
        return parent.inflate(parent.getContext(), R.layout.item_elv_group_test,null);
    }

    @Override
    protected void onBindGroupView(ViewGroup parent, int groupViewType, View groupView, GroupData<Merchant,Product> group) {

    }

    @Override
    protected View onCreateChildView(ViewGroup parent, int childViewType, boolean isLasChild) {
        return parent.inflate(parent.getContext(), R.layout.item_elv_child_test,null);
    }

    @Override
    protected View onBindChildView(ViewGroup parent, int childViewType, boolean isLastChild, View childView, ChildData<Product> child) {
        return null;
    }


    public static List<GroupData<Merchant,Product>> createTestData(){
        List<GroupData<Merchant,Product>> groupDataList = new LinkedList<GroupData<Merchant,Product>>();
        for(int i = 0; i < 5; i++){
            List<ChildData<Product>> childList = new LinkedList<>();
            for(int j = 0 ; j < 3; j++){
                childList.add(new ChildData<Product>(new Product()));
            }
            GroupData<Merchant,Product> groupData = new GroupData<Merchant,Product>(new Merchant(),childList);

            groupDataList.add(groupData);
        }
        return groupDataList;
    }
    private static class ViewHolderChild {

    }

    private static class ViewHolderGroup {

    }
}
