package com.bryan;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Author：Cxb on 2016/3/18 15:08
 *
 */
public interface MyService {

    /**
     * ps Call的泛型只能是对象,String不可以
     * @param uid
     * @param token
     * @return
     */

    @GET("user")
    Call<User> getUser(@Query("id") int uid, @Query("token") String token);

    @FormUrlEncoded
    @POST("user")
    Call<ResponseBody> getUserPost(@Field("id") int uid, @Field("token") String token);


    //body的参数一定要是对象，String不可以，会把""也一起传过去 ,如服务器收到"dddd"
    @POST("user")
    Call<List<User>> getUserPostBody(@Body User param);


    @Multipart
    @POST("web/UploadFileServlet")
    Call<ResponseBody> upload(@Part("filename") RequestBody filename,@Part("filedes") RequestBody filedes,
                              @Part MultipartBody.Part file);


    @Streaming
    @GET("web/{filename}")
    Call<ResponseBody> downFile(@Path("filename") String fileName);
}
