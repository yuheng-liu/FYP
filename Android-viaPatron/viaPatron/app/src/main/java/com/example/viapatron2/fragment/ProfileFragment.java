package com.example.viapatron2.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.viapatron2.R;
import com.example.viapatron2.activity.MainActivity;
import com.example.viapatron2.app.constants.AppConstants;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private static final int PERMISSIONS_REQUEST_MEDIA_ACCESS = AppConstants.PERMISSION_MEDIA_ACCESS;

    // App
    private MainActivity mActivity;

    // Views
    private ImageButton uploadPicButton;
    private TextView uploadPicText;
    private FrameLayout logoutButton;
    private MyProfileFragmentListener profileListener;


    // Define the events that the fragment will use to communicate
    public interface MyProfileFragmentListener {
        public void onLogoutButtonSelected();
    }

    public ProfileFragment () {
        // Empty Constructor
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyProfileFragmentListener) {
            profileListener = (MyProfileFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ProfileFragment.MyProfileFragmentListener");
        }
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mActivity = (MainActivity) requireActivity();
        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        return v;
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutButton = (FrameLayout) mActivity.findViewById(R.id.logout_button);
        uploadPicButton = (ImageButton) mActivity.findViewById(R.id.upload_pic_button);
        uploadPicText = (TextView) mActivity.findViewById(R.id.upload_pic_text);

        setUpButtons();
    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //Include logout button testing
    private void setUpButtons() {

        if (logoutButton != null) {
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        new AlertDialog.Builder(mActivity)
                                .setTitle(R.string.sign_out)
                                .setMessage(R.string.profile_sign_out_msg)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Log.d(TAG, "attempting to log out.");
                                        profileListener.onLogoutButtonSelected();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                })
                                .create()
                                .show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "error on log out.");
                    }
                }
            });
        }

        if (uploadPicButton != null) {
            uploadPicButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkMediaPermission();
                }
            });
        }
    }

    private void checkMediaPermission() {
        if (mActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            getPicture();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_MEDIA_ACCESS);
        }
    }

    // Todo: allow for user to retrieve image from gallery
    private void getPicture() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);//zero can be replaced with any action code
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_MEDIA_ACCESS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "media permission granted");
                    getPicture();
                } else {
                    Log.d(TAG, "media permission denied");
                    // Do nothing.
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Log.d(TAG, "case 0");
                    Uri selectedImage = imageReturnedIntent.getData();
                    uploadPicText.setVisibility(View.GONE);
                    uploadPicButton.setImageURI(selectedImage);
                    // todo: save profile photo to account permanently
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Log.d(TAG, "case 1");
                    Uri selectedImage = imageReturnedIntent.getData();
                    uploadPicText.setVisibility(View.GONE);
                    uploadPicButton.setImageURI(selectedImage);
                    // todo: save profile photo to account permanently
                }
                break;
        }
    }
}
