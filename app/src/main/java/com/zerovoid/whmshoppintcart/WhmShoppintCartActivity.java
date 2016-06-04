package com.zerovoid.whmshoppintcart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.zerovoid.shoppingcart.R;

/**
 * Created by wanghaiming on 2016/6/4.
 */
public class WhmShoppintCartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whm_shopping_cart);

        ExpandableListView listView = (ExpandableListView)findViewById(R.id.expandable_list);
        WhmShoppintCartAdapter adapter = new WhmShoppintCartAdapter(WhmShoppintCartAdapter.createTestData());
        listView.setAdapter(adapter);

        for(int i = 0 ; i < adapter.getGroupCount(); i++){
            listView.expandGroup(i);
        }
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

    }

}
