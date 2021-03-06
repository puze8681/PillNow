package hoosasack.pillnow.Util.Server.NetWork

import hoosasack.pillnow.Util.Server.Data.*
import hoosasack.pillnow.Util.Server.Schema.MedicineSchema
import hoosasack.pillnow.Util.Server.Schema.MedicineUserSchema
import hoosasack.pillnow.Util.Server.Schema.UserSchema
import retrofit2.http.*

/**
 * Created by parktaejun on 2017. 8. 20..
 */
interface RetrofitService {

    @FormUrlEncoded
    @POST("/auth/login")
    fun login(@Field("id") id: String, @Field("password") password: String): retrofit2.Call<Login>

    @FormUrlEncoded
    @POST("/auth/signup")
    fun signup(@Field("id") id: String, @Field("password") password: String, @Field("age") age: String, @Field("sex") sex: String, @Field("name") name: String): retrofit2.Call<SignUp>

    @GET("/location?")
    fun location(@Path("latitude") latitude: String, @Field("longitude") longitude: String): retrofit2.Call<Location>

    @GET("/medicine/getData?")
    fun medicine(@Path("medicNum") medicNum: String, @Field("token") token: String): retrofit2.Call<Medicine>

    @GET("/medicine/userList?")
    fun medicineUserList(@Field("token") token: String): retrofit2.Call<List<MedicineUserList>>

    @FormUrlEncoded
    @POST("/medicine/delete")
    fun medicineDelete(@Field("token") token: String, @Field("number") number: String): retrofit2.Call<List<MedicineDelete>>

    @GET("/alarm/setting?")
    fun alarm(@Path("token") token: String, @Field("number") name: String, @Field("time") title: String): retrofit2.Call<Alarm>

    @GET("/alarm/setting?")
    fun loadAlarm(@Path("token") token: String, @Field("number") name: String): retrofit2.Call<List<LoadAlarm>>

    @GET("/push")
    fun push(@Path("token") token: String, @Path("fcm") fcm: String): retrofit2.Call<Push>
}