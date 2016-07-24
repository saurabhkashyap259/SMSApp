package com.mate.smsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mate.smsapp.R;
import com.mate.smsapp.holders.ConversationViewHolder;
import com.mate.smsapp.holders.MessageViewHolder;
import com.mate.smsapp.model.Conversation;
import com.mate.smsapp.model.Message;
import com.mate.smsapp.utils.Utils;

import java.util.List;

/**
 * Created by sasuke on 23/7/16.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private static final String TAG = MessageAdapter.class.getSimpleName();

    Activity activity;
    List<Message> messageList;
    LayoutInflater layoutInflater;

    public MessageAdapter(Activity activity, List<Message> messageList) {

        this.activity = activity;
        this.messageList = messageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row;

        if(layoutInflater == null){

            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        row = layoutInflater.inflate(R.layout.card_view_message, parent, false);

        MessageViewHolder viewHolder = new MessageViewHolder(row);

        row.setTag(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder viewHolder, int position) {

        final Message m = messageList.get(viewHolder.getAdapterPosition());

        if(m != null){

            int type = Integer.parseInt(m.getMessageType());

            Log.e(TAG, "Type: " + type);

            if(type == Telephony.Sms.MESSAGE_TYPE_INBOX) {

                if(m.getProfileImage() != null) {

                    viewHolder.receiverProfileImage.setImageURI(m.getProfileImage());
                }else {

                    viewHolder.receiverProfileImage.setImageResource(R.drawable.default_profile);
                }

                viewHolder.receiverMessageBody.setText(m.getMessageBody());
                viewHolder.receiverMessageDate.setText(m.getMessageDate());
                viewHolder.receiverMessageLayout.setVisibility(View.VISIBLE);
                viewHolder.senderMessageLayout.setVisibility(View.GONE);
            } else {

                viewHolder.senderProfileImage.setImageResource(R.drawable.default_profile);
                viewHolder.senderMessageBody.setText(m.getMessageBody());
                viewHolder.senderMessageDate.setText(m.getMessageDate());
                viewHolder.senderMessageLayout.setVisibility(View.VISIBLE);
                viewHolder.receiverMessageLayout.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

}
