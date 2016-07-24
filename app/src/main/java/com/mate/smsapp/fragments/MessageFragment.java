package com.mate.smsapp.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mate.smsapp.MainActivity;
import com.mate.smsapp.R;
import com.mate.smsapp.adapter.MessageAdapter;
import com.mate.smsapp.model.Message;
import com.mate.smsapp.utils.Constants;
import com.mate.smsapp.utils.GlobalVariables;
import com.mate.smsapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasuke on 23/7/16.
 */
public class MessageFragment extends Fragment {

    private static final String TAG = ConversationFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messageList = new ArrayList<>();
    private EditText messageText;
    private String type, address, date, body, text;

    public MessageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);

        //Get the ids
        messageText = (EditText) rootView.findViewById(R.id.message_text);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        //Fragment Number
        GlobalVariables.fragmentNumber = 1;

        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        setHasOptionsMenu(true);

        //Local variables
        RecyclerView.LayoutManager layoutManager;
        ImageView sendButtonImage;

        //Get the ids
        sendButtonImage = (ImageView) rootView.findViewById(R.id.send_button_image);

        //Layout manager
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);

        //set layout manager
        recyclerView.setLayoutManager(layoutManager);

        //adapter
        adapter = new MessageAdapter(getActivity(), messageList);

        //set adapter
        recyclerView.setAdapter(adapter);

        ContentResolver contentResolver = getActivity().getContentResolver();

        Cursor cursor = contentResolver.query(Uri.parse("content://sms/conversations/" + GlobalVariables.threadID), null, null, null, "DATE DESC");

        assert cursor != null;
        if(cursor.moveToFirst()){

            do{

                type = cursor.getString(cursor.getColumnIndex("type"));
                address = cursor.getString(cursor.getColumnIndex("address"));
                date = cursor.getString(cursor.getColumnIndex("date"));
                body = cursor.getString(cursor.getColumnIndex("body"));

                Log.e(TAG, "Message: " + "Type: " + type + " Address: " + address + " Date: " + date + " Body: " + body);

                Message message = new Message();

                message.setMessageType(type);
                message.setProfileImage(Utils.getContactPhotoUri(getContext(), address));
                message.setMessageBody(body);
                message.setMessageDate(date);

                messageList.add(message);

                adapter.notifyDataSetChanged();

            }while(cursor.moveToNext());
        }else{

            Toast.makeText(getActivity(), "No Message in Inbox", Toast.LENGTH_SHORT).show();
        }

        cursor.close();

        recyclerView.scrollToPosition(0);

        class SendMessage extends AsyncTask<Void, Void, Void> {


            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(GlobalVariables.address, null, text, null, null);

                    Message message = new Message();

                    message.setMessageBody(text);
                    message.setMessageType("2");
                    message.setMessageDate(String.valueOf(System.currentTimeMillis()));

                    messageList.add(0, message);

                } catch (Exception e) {
                   // Toast.makeText(getActivity(), "Sending SMS failed.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

                messageText.setText("");
                //hideKeyboard();
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void onPreExecute() {
            }
        }

        sendButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text = messageText.getText().toString();

                Log.e(TAG, "Message: " + text);

                if(text.trim().length() > 0) {

                    hideKeyboard();
                    new SendMessage().execute();
                }
            }
        });

    }

    public void hideKeyboard() {

        InputMethodManager inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
