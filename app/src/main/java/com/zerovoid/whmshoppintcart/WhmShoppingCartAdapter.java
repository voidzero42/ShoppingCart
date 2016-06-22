package com.zerovoid.whmshoppintcart;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zerovoid.common.view.grouplist.ChildData;
import com.zerovoid.common.view.grouplist.GroupData;
import com.zerovoid.common.view.grouplist.AbstractGroupListAdapter;
import com.zerovoid.shoppingcart.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wanghaiming on 2016/6/4.
 */
public class WhmShoppingCartAdapter extends AbstractGroupListAdapter<Merchant,Product> {

    public WhmShoppingCartAdapter(List<? extends GroupData<Merchant, Product>> groupDataList) {
        super(groupDataList);
    }

    @Override
    protected View onCreateGroupView(ViewGroup parent, int groupViewType) {
        View view = parent.inflate(parent.getContext(), R.layout.item_elv_group_whm,null);
        ViewHolderGroup holder = new ViewHolderGroup();
        holder.mSelected = (CheckBox) view.findViewById(R.id.check_group);
        view.setTag(holder);
        return view;
    }

    @Override
    protected void onBindGroupView(ViewGroup parent, int groupViewType, final View groupView, final GroupData<Merchant,Product> group) {
        ViewHolderGroup holderGroup = (ViewHolderGroup)groupView.getTag();

        holderGroup.mSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGroupSelectedChanged(group,((CheckBox)v).isChecked());
            }
        });
        holderGroup.mSelected.setChecked(group.isSelected());
    }

    @Override
    protected View onCreateChildView(ViewGroup parent, int childViewType, boolean isLasChild) {
        View view = parent.inflate(parent.getContext(), R.layout.item_elv_child_whm,null);
        ViewHolderChild holder = new ViewHolderChild();
        holder.mSelected = (CheckBox) view.findViewById(R.id.check_child);
        holder.mGroupTailInfo = (TextView)view.findViewById(R.id.group_tail_info);
        view.setTag(holder);
        return view;
    }

    @Override
    protected void onBindChildView(ViewGroup parent, int childViewType, boolean isLastChild, View childView, final ChildData<Product> child) {
        ViewHolderChild holder = (ViewHolderChild)childView.getTag();
        holder.mSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onChildSelectedChanged(child,isChecked);
            }
        });
        holder.mSelected.setChecked(child.isSelected());

        holder.mGroupTailInfo.setVisibility(isLastChild? View.VISIBLE:View.GONE);
        if(isLastChild){
            GroupData<?,Product> groupData = child.getGroupData();
            int selectedNum = 0 ;
            for(ChildData<Product> childData : groupData.getChildList()){
                if(childData.isSelected()) selectedNum++;
            }
            holder.mGroupTailInfo.setText(String.format("选择了%d个商品",selectedNum));
        }
    }


    public static List<GroupData<Merchant,Product>> createTestData(){
        List<GroupData<Merchant,Product>> groupDataList = new LinkedList<GroupData<Merchant,Product>>();
        for(int i = 0; i < 5; i++){
            List<CustomChildData<Product>> childList = new LinkedList<>();
            for(int j = 0 ; j < 3; j++){
                childList.add(new CustomChildData<Product>(new Product()));
            }
            GroupData<Merchant,Product> groupData = new GroupData<Merchant,Product>(new Merchant(),childList);

            groupDataList.add(groupData);
        }
        return groupDataList;
    }
    private static class ViewHolderGroup {
        public CheckBox mSelected;
    }

    private static class ViewHolderChild {
        public CheckBox mSelected;
        public TextView mGroupTailInfo;
    }
}
