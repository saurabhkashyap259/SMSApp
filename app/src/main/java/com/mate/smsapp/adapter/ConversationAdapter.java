package com.mate.smsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mate.smsapp.R;
import com.mate.smsapp.holders.ConversationViewHolder;
import com.mate.smsapp.model.Conversation;
import com.mate.smsapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasuke on 22/7/16.
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationViewHolder> {

    Activity activity;
    List<Conversation> conversationList;
    LayoutInflater layoutInflater;
    ConversationAdapterInterface conversationAdapterInterface;

    public ConversationAdapter(Activity activity, List<Conversation> conversationList) {

        this.activity = activity;
        this.conversationList = conversationList;
    }

    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row;

        if(layoutInflater == null){

            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        row = layoutInflater.inflate(R.layout.card_view_conversation, parent, false);

        ConversationViewHolder viewHolder = new ConversationViewHolder(row);

        row.setTag(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder viewHolder, int position) {

        final Conversation c = conversationList.get(viewHolder.getAdapterPosition());

        if(c != null){

            if(c.getPhotoUri() != null) {

                viewHolder.contactImage.setImageURI(c.getPhotoUri());
            }else {

                viewHolder.contactImage.setImageResource(R.drawable.default_profile);
            }

            if(Utils.getContactName(activity, c.getCaontactName()) == null) {

                viewHolder.contactName.setText(c.getCaontactName());
            }else {

                viewHolder.contactName.setText(Utils.getContactName(activity, c.getCaontactName()));
            }

            viewHolder.messageDate.setText(c.getMessageDate());
            viewHolder.messageBody.setText(c.getMessageBody());
            viewHolder.threadID.setText(c.getThreadID());

            viewHolder.conversationLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    conversationAdapterInterface.getMessages(c.getThreadID(), c.getCaontactName());
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    public interface ConversationAdapterInterface {

        void getMessages(String threadID, String address);
    }

    public void setConversationAdapterInterface(ConversationAdapterInterface listener) {

        this.conversationAdapterInterface = listener;
    }

    public void setFilter(List<Conversation> searchingData) {
        conversationList = new ArrayList<>();
        conversationList.addAll(searchingData);
        notifyDataSetChanged();
    }
}
