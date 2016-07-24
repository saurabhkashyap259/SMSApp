package com.mate.smsapp.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mate.smsapp.R;

/**
 * Created by sasuke on 23/7/16.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {

    public TextView receiverMessageID;
    public TextView senderMessageID;
    public ImageView receiverProfileImage;
    public ImageView senderProfileImage;
    public TextView receiverMessageBody;
    public TextView senderMessageBody;
    public TextView receiverMessageDate;
    public TextView senderMessageDate;
    public RelativeLayout receiverMessageLayout;
    public RelativeLayout senderMessageLayout;

    public MessageViewHolder(View itemView) {
        super(itemView);

        receiverMessageID = (TextView) itemView.findViewById(R.id.receiver_message_id);
        receiverProfileImage = (ImageView) itemView.findViewById(R.id.receiver_profile_image);
        receiverMessageBody = (TextView) itemView.findViewById(R.id.receiver_message_body);
        receiverMessageDate = (TextView) itemView.findViewById(R.id.receiver_message_date);
        receiverMessageLayout = (RelativeLayout) itemView.findViewById(R.id.receiver_layout);
        senderMessageID = (TextView) itemView.findViewById(R.id.sender_message_id);
        senderProfileImage = (ImageView) itemView.findViewById(R.id.sender_profile_image);
        senderMessageBody = (TextView) itemView.findViewById(R.id.sender_message_body);
        senderMessageDate = (TextView) itemView.findViewById(R.id.sender_message_date);
        senderMessageLayout = (RelativeLayout) itemView.findViewById(R.id.sender_layout);

    }
}
