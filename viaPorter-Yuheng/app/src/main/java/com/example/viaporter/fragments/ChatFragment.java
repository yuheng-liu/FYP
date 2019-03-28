package com.example.viaporter.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.viaporter.MainActivity;
import com.example.viaporter.R;
import com.example.viaporter.managers.FirebaseAdaptersManager;
import com.example.viaporter.models.ChatMessage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class ChatFragment extends Fragment {
    private static final String TAG = "viaPatron.ChatFragment";

    private MainActivity mActivity;
    private RecyclerView mChatBoxView;
    private FirebaseRecyclerAdapter<ChatMessage, FirebaseAdaptersManager.ChatViewHolder> mChatBoxAdapter;
    private EditText messageBox;
    private ImageButton sendButton;

    public ChatFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (MainActivity) requireActivity();

        setupViews();
    }

    private void setupViews() {
        mChatBoxView = mActivity.findViewById(R.id.chat_view_table);
        mChatBoxView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChatBoxView.setHasFixedSize(true);

        messageBox = mActivity.findViewById(R.id.chat_message_box);

        sendButton = mActivity.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mActivity.getFirebaseDatabaseManager().sendChatMessage(messageBox.getText().toString());
                messageBox.setText("");
            }
        });
        mChatBoxAdapter = mActivity.getFirebaseAdaptersManager().getChatBoxAdapter(mChatBoxView);
        mChatBoxView.setAdapter(mChatBoxAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mChatBoxAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mChatBoxAdapter.stopListening();
    }
}
