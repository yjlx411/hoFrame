package com.krad.origin.bean

import com.google.gson.annotations.SerializedName

class OilPrice {
    @SerializedName("city")
    var city: String? = null

    @SerializedName("92h")
    var price_92h: String? = null

    @SerializedName("95h")
    var price_95h: String? = null

    @SerializedName("98h")
    var price_98h: String? = null

    @SerializedName("0h")
    var price_0h: String? = null
}