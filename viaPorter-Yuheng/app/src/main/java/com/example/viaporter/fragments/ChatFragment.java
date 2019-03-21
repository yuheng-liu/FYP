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
import android.widget.TextView;

import com.example.viaporter.MainActivity;
import com.example.viaporter.R;
import com.example.viaporter.models.ChatMessage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ChatFragment extends Fragment {
    private static final String TAG = "viaPatron.ChatFragment";

    private static final int TYPE_MY_MSG = 1;
    private static final int TYPE_THEIR_MSG = 2;

    private MainActivity mActivity;
    private RecyclerView mChatBoxView;
    private FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder> mChatBoxAdapter;
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
        setupFirebaseRecyclerAdapter();
    }

    private void setupViews() {
        mChatBoxView = mActivity.findViewById(R.id.chat_view_table);
        mChatBoxView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChatBoxView.setHasFixedSize(true);

        messageBox = mActivity.findViewById(R.id.chat_message_box);

        sendButton = mActivity.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mActivity.mDatabase
                        .child("chats")
                        .push()
                        .setValue(new ChatMessage(messageBox.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );
                messageBox.setText("");
            }
        });
    }

    private void setupFirebaseRecyclerAdapter() {
        DatabaseReference curDatabase = mActivity.mDatabase.child("chats");
        curDatabase.keepSynced(true);

        Query query = curDatabase.limitToLast(50);

        FirebaseRecyclerOptions<ChatMessage> options =
                new FirebaseRecyclerOptions.Builder<ChatMessage>()
                        .setQuery(query, ChatMessage.class)
                        .build();

        mChatBoxAdapter = new FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder>(options) {
            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                int layout = 0;

                if (viewType == TYPE_MY_MSG){
                    layout = R.layout.my_chat_bubble;
                } else if (viewType == TYPE_THEIR_MSG){
                    layout = R.layout.their_chat_bubble;
                }

                 return new ChatViewHolder(LayoutInflater.from(viewGroup.getContext())
                            .inflate(layout, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull ChatMessage model) {
                holder.setName(model.getMessageUser());
                holder.setMessage(model.getMessageText());
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage message = getItem(position);
                if (message.getMessageUser().equals("Yuheng")){
                    return TYPE_MY_MSG;
                } else {
                    return TYPE_THEIR_MSG;
                }
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                mChatBoxView.scrollToPosition(mChatBoxAdapter.getItemCount() - 1);
            }
        };
        mChatBoxView.setAdapter(mChatBoxAdapter);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public ChatViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setName(String name){
            TextView nameField = (TextView)mView.findViewById(R.id.name);
            nameField.setText(name);
        }
        public void setMessage(String message){
            TextView messageField = (TextView)mView.findViewById(R.id.message_body);
            messageField.setText(message);
        }
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
