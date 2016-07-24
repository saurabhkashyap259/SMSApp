package com.mate.smsapp.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mate.smsapp.R;

/**
 * Created by sasuke on 22/7/16.
 */
public class ConversationViewHolder extends RecyclerView.ViewHolder {

    public ImageView contactImage;
    public TextView contactName;
    public TextView messageDate;
    public TextView messageBody;
    public TextView threadID;
    public RelativeLayout conversationLayout;

    public ConversationViewHolder(View itemView) {
        super(itemView);

        contactImage = (ImageView) itemView.findViewById(R.id.profile_image);
        contactName = (TextView) itemView.findViewById(R.id.name);
        messageDate = (TextView) itemView.findViewById(R.id.date);
        messageBody = (TextView) itemView.findViewById(R.id.message);
        threadID = (TextView) itemView.findViewById(R.id.thread_id);
        conversationLayout = (RelativeLayout) itemView.findViewById(R.id.conversation_layout);
    }
}
