package com.example.viaporter.app.managers

/**
 * Created by Lim Zhiming on 10/1/19.
 */
class APIManager(dataManager: DataManager)//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(APIEndpoints.API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        mDataManager = dataManager;
//        riderAPI = retrofit.create(RiderAPI.class);
//        gson = new Gson();
//        verificationId = "";
//        createSessionObservable = PublishRelay.create();
{

    // private properties
    private val mDataManager: DataManager? = null

    companion object {

        private val TAG = "APIManager"
    }


}
