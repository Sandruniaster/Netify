package pl.edu.ug.aib.netify.itemView;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import pl.edu.ug.aib.netify.R;
import pl.edu.ug.aib.netify.data.FriendData;

@EViewGroup(R.layout.friend_list_item)
public class FriendListItemView extends RelativeLayout {


    @ViewById
    TextView friendName;
    @ViewById
    TextView friendSurname;

    Context context;

    public FriendListItemView(Context context) {
        super(context);
        this.context = context;
    }

    public void bind(FriendData friendData){

        friendName.setText(friendData.firstName);
        friendSurname.setText(friendData.lastName);
    }
}
