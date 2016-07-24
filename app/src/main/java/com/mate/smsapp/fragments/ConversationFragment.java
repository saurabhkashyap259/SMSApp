package com.mate.smsapp.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mate.smsapp.MainActivity;
import com.mate.smsapp.R;
import com.mate.smsapp.adapter.ConversationAdapter;
import com.mate.smsapp.model.Conversation;
import com.mate.smsapp.utils.Constants;
import com.mate.smsapp.utils.GlobalVariables;
import com.mate.smsapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasuke on 22/7/16.
 */
public class ConversationFragment extends Fragment implements ConversationAdapter.ConversationAdapterInterface, SearchView.OnQueryTextListener{

    private static final String TAG = ConversationFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    public ConversationAdapter adapter;
    private List<Conversation> conversationList = new ArrayList<>();

    public ConversationFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_conversation, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        //Fragment Number
        GlobalVariables.fragmentNumber = 0;

        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        setHasOptionsMenu(true);

        //Local variables
        RecyclerView.LayoutManager layoutManager;

        //Layout manager
        layoutManager = new LinearLayoutManager(getActivity());

        //set layout manager
        recyclerView.setLayoutManager(layoutManager);

        //adapter
        adapter = new ConversationAdapter(getActivity(), conversationList);

        //Set interface
        adapter.setConversationAdapterInterface(ConversationFragment.this);

        //set adapter
        recyclerView.setAdapter(adapter);

        ContentResolver contentResolver = getActivity().getContentResolver();

        Cursor cursor = contentResolver.query(Uri.parse("content://mms-sms/conversations/"), null, null, null, "DATE DESC");

        assert cursor != null;
        if(cursor.moveToFirst()){

            do{

                String address = cursor.getString(cursor.getColumnIndex("address"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String body = cursor.getString(cursor.getColumnIndex("body"));
                String threadID = cursor.getString(cursor.getColumnIndex("thread_id"));

                //Log.e(TAG, "Id: " + cursor.getString(cursor.getColumnIndex("thread_id")) + " Address: " + cursor.getString(cursor.getColumnIndex("address")));

                java.sql.Date smsDate = new java.sql.Date(Long.valueOf(date));

                Conversation conversation = new Conversation();

                conversation.setPhotoUri(Utils.getContactPhotoUri(getContext(), address));
                conversation.setCaontactName(address);
                conversation.setMessageDate(smsDate.toString());
                conversation.setMessageBody(body);
                conversation.setThreadID(threadID);

                conversationList.add(conversation);

                adapter.notifyDataSetChanged();
            }while(cursor.moveToNext());
        }else{

            Toast.makeText(getActivity(), "No Message in Inbox", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    @Override
    public void getMessages(String threadID, String address) {

        GlobalVariables.threadID = threadID;
        GlobalVariables.address = address;

        MainActivity.openFragment(Constants.MessageFragmentCount);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setBackgroundColor(getResources().getColor(R.color.white));

        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        List<Conversation> filteredModelList = filter(conversationList, newText);
        adapter.setFilter(filteredModelList);
        recyclerView.scrollToPosition(0);
        return true;
    }

    private List<Conversation> filter (List<Conversation> messageList, String query) {

        query = query.toLowerCase();
        String text = "";
        String message = "";

        List<Conversation> filteredChildrenList = new ArrayList<>();

        for (Conversation c : messageList) {

            if(Utils.getContactName(getContext(), c.getCaontactName()) == null && c.getCaontactName() != null) {

                text = c.getCaontactName().toLowerCase();

                Log.e(TAG, "Text: " + text);
            }else {

                if(c.getCaontactName() != null) {

                    text = Utils.getContactName(getContext(), c.getCaontactName()).toLowerCase();
                }
            }

            if(c.getMessageBody() != null) {

                message = c.getMessageBody().toLowerCase();
            }

            if (text.contains(query) || message.contains(query)) {

                filteredChildrenList.add(c);
            }
        }

        return filteredChildrenList;
    }
}
