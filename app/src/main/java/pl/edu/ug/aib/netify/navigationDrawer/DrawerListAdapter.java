package pl.edu.ug.aib.netify.navigationDrawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import pl.edu.ug.aib.netify.R;
import pl.edu.ug.aib.netify.fragment.AddGroupFragment_;
import pl.edu.ug.aib.netify.fragment.LogoutFragment_;
import pl.edu.ug.aib.netify.fragment.SearchFriendsFragment_;
import pl.edu.ug.aib.netify.fragment.SearchGroupsFragment_;
import pl.edu.ug.aib.netify.fragment.UserGroupsFragment_;
import pl.edu.ug.aib.netify.fragment.FriendsFragment_;

@EBean
public class DrawerListAdapter extends BaseAdapter {
    @RootContext
    Context context;
    List<DrawerItem> items = new ArrayList<DrawerItem>();

    @AfterInject
    void init() {
        items.clear();
        items.add(new DrawerItem(R.string.title_mygroups, R.drawable.ic_action_group,
                UserGroupsFragment_.class));
        items.add(new DrawerItem(R.string.title_addgroup, R.drawable.ic_action_add_group,
                AddGroupFragment_.class));
        items.add(new DrawerItem(R.string.title_searchgroup, R.drawable.ic_action_search,
                SearchGroupsFragment_.class));
        items.add(new DrawerItem(R.string.logout, R.drawable.ic_action_logout,
                LogoutFragment_.class));
        items.add(new DrawerItem(R.string.title_friends, R.drawable.ic_action_group,
                FriendsFragment_.class));
        items.add(new DrawerItem(R.string.title_searchfriends, R.drawable.ic_action_group,
                SearchFriendsFragment_.class));

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerItemView drawerItemView;
        if (convertView == null) {
            drawerItemView = DrawerItemView_.build(context);
        } else {
            drawerItemView = (DrawerItemView) convertView;
        }
        drawerItemView.bind(getItem(position));
        return drawerItemView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public DrawerItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
