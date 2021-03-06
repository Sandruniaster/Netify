package pl.edu.ug.aib.netify.rest;

import android.text.TextUtils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import java.util.HashSet;

import pl.edu.ug.aib.netify.HomeActivity;
import pl.edu.ug.aib.netify.LoginActivity;
import pl.edu.ug.aib.netify.data.EmailAndPassword;
import pl.edu.ug.aib.netify.data.GroupData;
import pl.edu.ug.aib.netify.data.GroupDataList;
import pl.edu.ug.aib.netify.data.IdData;
import pl.edu.ug.aib.netify.data.MemberGroupData;
import pl.edu.ug.aib.netify.data.MemberGroupDataList;
import pl.edu.ug.aib.netify.data.MyFriendData;
import pl.edu.ug.aib.netify.data.MyFriendDataList;
import pl.edu.ug.aib.netify.data.FriendData;
import pl.edu.ug.aib.netify.data.FriendsDataList;
import pl.edu.ug.aib.netify.data.SongData;
import pl.edu.ug.aib.netify.data.User;
import pl.edu.ug.aib.netify.data.UserLogout;
import pl.edu.ug.aib.netify.data.UserRegistrationData;

@EBean
public class RestHomeBackgroundTask {

    @RootContext
    HomeActivity activity;
    @RestService
    NetifyRestClient restClient;


    @Background
    public void getUserGroups(String userId){
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "netify");
            MemberGroupDataList memberGroupDataList = restClient.getMemberGroupsByUserId("UserId=" + userId);
            GroupDataList groupDataList;
            //Check if user has any groups, if true, return empty object without sending request
            if(memberGroupDataList.records.isEmpty()) groupDataList = new GroupDataList();
            else {
                //used to provide unique ids
                HashSet<String> groupIds = new HashSet<String>();
                for (MemberGroupData item : memberGroupDataList.records) groupIds.add(item.groupId);
                groupDataList = restClient.getGroupsById(TextUtils.join(",", groupIds));
            }
            publishUserGroupsResult(groupDataList);
        }catch(Exception e){
            publishError(e);
        }
    }

    @Background
    public void getUserFriends(String userId){
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "netify");
            MyFriendDataList myFriendDataList = restClient.getFriendsByUserId("UserId=" + userId);
            FriendsDataList friendsDataList;
            //Check if user has any friends, if true, return empty object without sending request
            if(myFriendDataList.records.isEmpty()) friendsDataList = new FriendsDataList();
            else {
                //used to provide unique ids
                HashSet<String> friendIds = new HashSet<String>();
                for (MyFriendData item : myFriendDataList.records) friendIds.add(item.user2Id);
                friendsDataList = restClient.getFriendsById(TextUtils.join(",", friendIds));
            }
            publishUserFriendsResult(friendsDataList);
        }catch(Exception e){
            publishError(e);
        }
    }

    @Background
    public void addNewGroup(String userId, String sessionId, GroupData groupData, SongData firstSong){
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "netify");
            restClient.setHeader("X-Dreamfactory-Session-Token", sessionId);
            //send group data and receive group id
            IdData result = restClient.addGroup(groupData);
            groupData.id = result.id;
            firstSong.groupId = Integer.parseInt(result.id);
            //send data about user membership in the group and receive confirmation
            MemberGroupData memberGroupData = new MemberGroupData();
            memberGroupData.groupId = groupData.id; memberGroupData.userId = userId;
            result = restClient.addMemberGroupData(memberGroupData);
            //TODO send first song data
            result = restClient.addSongToGraph(firstSong);
            publishAddNewGroupResult(groupData);
        }catch(Exception e){
            publishError(e);
        }
    }

    @Background
    public void searchGroups(String query){
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "netify");
            //Sets search conditions: looks for query in name, description and genre in public groups
            GroupDataList groupDataList = restClient.getGroupsByQuery("(name like '%" + query + "%'||description like '%"+ query + "%'||genre like '%"+ query + "%')and isPrivate=false");
            publishSearchGroupsResult(groupDataList);
        }catch(Exception e){
            publishError(e);
        }
    }

    @Background
    public void searchFriends(String query){
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "netify");
            //Sets search conditions: looks for query in name, description and genre in public groups
            FriendsDataList friendsDataList = restClient.getFriendsByQuery("(firstName like '%" + query + "%'||lastName like '%"+ query + "%'||email like '%"+ query);
            publishSearchFriendsResult(friendsDataList);
        }catch(Exception e){
            publishError(e);
        }
    }
    //TODO

    @Background
    public void logout(String sessionId){
        try{
            restClient.setHeader("X-Dreamfactory-Application-Name", "netify");
            restClient.setHeader("X-Dreamfactory-Session-Token", sessionId);
            UserLogout userLogout = restClient.logout();
            publishLogoutResult(userLogout.success);
        }
        catch(Exception e){
            publishError(e);
        }
    }
    //Calls to activity and pushing objects to UiThread
    @UiThread
    void publishUserGroupsResult(GroupDataList groupDataList){
        activity.onUserGroupListDownloaded(groupDataList);
    }
    @UiThread
    void publishUserFriendsResult(FriendsDataList friendsDataList) {
        activity.onUserFriendsListDownloaded(friendsDataList);
    }
    @UiThread
    void publishAddNewGroupResult(GroupData groupData){
        activity.onNewGroupAdded(groupData);
    }
    @UiThread
    void publishSearchGroupsResult(GroupDataList groupDataList){
        activity.onSearchGroupCompleted(groupDataList);
    }
    @UiThread
    void publishSearchFriendsResult(FriendsDataList friendsDataList){
        activity.onSearchFriendCompleted(friendsDataList);
    }
    @UiThread
    void publishLogoutResult(Boolean success){
        activity.onLogout(success);
    }
    @UiThread
    void publishError(Exception e) {
        activity.showError(e);
    }
}
