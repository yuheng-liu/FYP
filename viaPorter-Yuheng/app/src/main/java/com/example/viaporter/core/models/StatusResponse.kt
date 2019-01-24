package com.example.viaporter.core.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Lim Zhiming on 10/1/19.
 * Adapting from Auto
 */

class StatusResponse {

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("error_code")
    @Expose
    var statusCode: StatusCode? = null

    @SerializedName("reason")
    @Expose
    // TODO: need to add new getReasonText(Context context) instead for language support
    var reason: String? = null

}
