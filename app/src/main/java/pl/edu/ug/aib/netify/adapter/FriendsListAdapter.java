package pl.edu.ug.aib.netify.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import pl.edu.ug.aib.netify.data.FriendData;
import pl.edu.ug.aib.netify.data.FriendsDataList;
import pl.edu.ug.aib.netify.itemView.FriendListItemView;
import pl.edu.ug.aib.netify.itemView.FriendListItemView_;

@EBean
public class FriendsListAdapter extends BaseAdapter {

    @RootContext
    Context context;
    List<FriendData> friendDatas = new ArrayList<FriendData>();

    public FriendsListAdapter(){}

    public void update(FriendsDataList friendsDataList){
        friendDatas.clear();
        friendDatas.addAll(friendsDataList.records);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return friendDatas.size();
    }

    @Override
    public FriendData getItem(int position) {
        return friendDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FriendListItemView friendListItemView;
        if (convertView == null) {
            friendListItemView = FriendListItemView_.build(context);
        } else {
            friendListItemView = (FriendListItemView) convertView;
        }
        friendListItemView.bind(getItem(position));
        return friendListItemView;
    }
}
