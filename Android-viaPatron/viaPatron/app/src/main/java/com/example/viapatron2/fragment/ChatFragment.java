package com.example.viapatron2.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.viapatron2.R;
import com.example.viapatron2.activity.MainActivity;
import com.example.viapatron2.core.models.ChatMessage;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.core.models.UserTripRequestSession;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class ChatFragment extends Fragment {

    private static final String TAG = "viaPatron.ChatFragment";
    private static final int TYPE_MY_MSG = 1;
    private static final int TYPE_THEIR_MSG = 2;

    // app
    private MainActivity mActivity;
    private NavController navController;
    private MyViewModel model;
    private UserTripRequestSession userTripRequestSession;

    // view
    private RecyclerView mChatBoxView;
    private FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder> mChatBoxAdapter;
    private EditText messageBox;
    private ImageButton sendButton;


    public ChatFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");

        View v = inflater.inflate(R.layout.chat_fragment, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (MainActivity) requireActivity();
        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);
        setUpViewModel();

        setupViews();
        setupFirebaseRecyclerAdapter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setUpViewModel() {

        Log.d(TAG, "setUpViewModel");

        // Re-created activities receive the same MyViewModel instance created by the first activity.
        model = ViewModelProviders.of(mActivity).get(MyViewModel.class);

        if (!model.getRequestSession().hasObservers()) {
            Log.d(TAG, "no observers yet");

            model.getRequestSession().observe(this, users -> {
                // update UI
            });
        }
    }

    private void setupViews() {
        mChatBoxView = mActivity.findViewById(R.id.chat_view_table);
        mChatBoxView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChatBoxView.setHasFixedSize(true);

        messageBox = mActivity.findViewById(R.id.chat_message_box);

        sendButton = mActivity.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mActivity.getmDatabase()
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
        DatabaseReference curDatabase = mActivity.getmDatabase().child("chats");
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
                if (message.getMessageUser().equals("Max")){
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

        Log.d(TAG, "onStart");
        mChatBoxAdapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
        mChatBoxAdapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }
}
