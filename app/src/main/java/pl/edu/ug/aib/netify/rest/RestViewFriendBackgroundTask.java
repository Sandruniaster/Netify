package pl.edu.ug.aib.netify.rest;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;


import pl.edu.ug.aib.netify.FriendActivity;

import pl.edu.ug.aib.netify.data.FriendsDataList;

@EBean
public class RestViewFriendBackgroundTask {

    @RootContext
    FriendActivity activity;
    @RestService
    NetifyRestClient restClient;




    @UiThread
    void publishResult(FriendsDataList friendsDataList){
        activity.setFriendsDataList(friendsDataList);

    }

    @UiThread
    void publishError(Exception e) {
        activity.showError(e);
    }
}
