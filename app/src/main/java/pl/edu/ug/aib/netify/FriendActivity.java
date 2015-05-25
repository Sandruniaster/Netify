package pl.edu.ug.aib.netify;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;

import pl.edu.ug.aib.netify.data.Edge;

import pl.edu.ug.aib.netify.data.FriendData;
import pl.edu.ug.aib.netify.data.FriendsDataList;
import pl.edu.ug.aib.netify.data.Node;

import pl.edu.ug.aib.netify.fragment.FriendsFragment;

import pl.edu.ug.aib.netify.rest.RestViewFriendBackgroundTask;


@EActivity(R.layout.activity_group)
@OptionsMenu(R.menu.menu_friend)
public class FriendActivity extends ActionBarActivity {

    public static final int INTENT_SONG_ADDED = 1;
    @OptionsMenuItem
    MenuItem action_sendrequest; //not visible by default
    @Extra
    Bundle bundle;
    @ViewById
    TextView name;
    @ViewById
    TextView surname;
    @ViewById
    TextView email;
    Gson gson;
    @Extra
    String friendId;
    @Extra
    FriendsDataList friendsDataList;
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;

    //prevents spamming join button
    boolean isProcessingSendRequest = false;
    @Bean
    @NonConfigurationInstance
    RestViewFriendBackgroundTask restViewFriendBackgroundTask;
    FriendData friendData;

    @Pref
    UserPreferences_ preferences;

    @AfterViews
    void init(){

       friendData = (FriendData) bundle.getSerializable(FriendsFragment.FRIEND);
        name.setText(friendData.firstName);
        surname.setText(friendData.lastName);
        email.setText(friendData.email);
    }

    //Check if current user is group member, returns false by default
    private boolean isCurrentFriend(){
        return friendId != null ? friendId.contains(Integer.toString(preferences.id().get())) : false;
    }
    public void setFriendsDataList(FriendsDataList friendsDataList) {
        this.friendsDataList = friendsDataList;
    }


    public void showError(Exception e){
        //display error message
        Log.d("gson", e.getMessage());
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        isProcessingSendRequest = false;
    }

/*

    //On clicking send request menuItem
    @OptionsItem(R.id.action_sendrequest)
    void actionSendRequestSelected(){
        //check if request is currently processed
        if(isProcessingSendRequest) return;
        restViewFriendBackgroundTask.sendRequest(groupId, Integer.toString(preferences.id().get()), preferences.sessionId().get());
        isProcessingSendRequest = true;
    }
*/
}
