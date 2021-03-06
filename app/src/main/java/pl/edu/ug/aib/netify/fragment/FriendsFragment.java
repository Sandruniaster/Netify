package pl.edu.ug.aib.netify.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;


import pl.edu.ug.aib.netify.FriendActivity;
import pl.edu.ug.aib.netify.FriendActivity_;
import pl.edu.ug.aib.netify.R;
import pl.edu.ug.aib.netify.adapter.FriendsListAdapter;
import pl.edu.ug.aib.netify.data.FriendData;
import pl.edu.ug.aib.netify.data.FriendsDataList;
import pl.edu.ug.aib.netify.data.User;

@EFragment(R.layout.fragment_friends)
public class FriendsFragment extends Fragment {

    public static final String USER = "user";
    public static final String FRIEND = "friend";


    @ViewById
    ProgressBar progressBarFriends;
    @ViewById
    ListView friendsList;
    @ViewById
    TextView noFriendsInfo;

    @ViewById
    TextView title;
    @Bean
    FriendsListAdapter adapter;
    FriendsDataList userFriends;

    @InstanceState
    User user;

    OnUserFriendsFragmentCommunicationListener listener;

    @AfterViews
    void init(){
        friendsList.setAdapter(adapter);
    }

    public void setUserFriends(FriendsDataList userFriends) {
        this.userFriends = userFriends;
        adapter.update(userFriends);
        progressBarFriends.setVisibility(View.GONE);
        if(adapter.getCount() == 0) noFriendsInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{

            listener = (OnUserFriendsFragmentCommunicationListener)activity;
            listener.getUserFriendsList();
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnUserFriendsFragmentCommunicationListener");
        }
    }

    public interface OnUserFriendsFragmentCommunicationListener {
        void getUserFriendsList();
    }

    @ItemClick
    void listItemClicked(FriendData item){
        if(item.id == null) return;
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER, user);
        bundle.putSerializable(FRIEND, item);
       FriendActivity_.intent(this).bundle(bundle).start();
    }


}
