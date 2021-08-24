package com.krad.origin.hoframe.net

import io.reactivex.Observable
import retrofit2.http.*
import java.util.*

interface Api {
    @FormUrlEncoded
    @POST
    fun post(
        @Url url: String?,
        @Field("json") data: String?,
    ): Observable<String>

    @FormUrlEncoded
    @POST
    fun postWithToken(
        @Url url: String?,
        @Header("token") token: String?,
        @Field("json") data: String?,
    ): Observable<String>

    @FormUrlEncoded
    @POST
    fun postWithKey(
        @Url url: String?,
        @Field("key") key: String?,
        @FieldMap params: WeakHashMap<String, Any>?,
    ): Observable<String>

}