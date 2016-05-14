package com.bryan;

import java.util.List;
import java.util.Map;

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
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Author：Cxb on 2016/3/18 15:08
 *
 */
public interface MyService {


    @GET("rest/findUserForGet")
    //@Query注解不能丢
    Call<User> findUserForGet(@Query("id") int id, @Query("username") String username,@Query("address") String address);

    @FormUrlEncoded
    @POST("rest/findUserForPost")
     //@Field注解不能丢
    Call<ResponseBody> findUserForPost(@Field("id") int id, @Field("username") String username,@Field("address") String address);


    @POST("rest/findUserList")
    Call<List<User>> findUserList();

    //body的参数一定要是对象，String不可以，会把""也一起传过去 ,如服务器收到"dddd"
    //Content-Type: application/json; charset=UTF-8为自动加上
    @POST("rest/postBodyJson")
    Call<User> postBodyJson(@Body User user);


    //上传单个文件
    @Multipart
    @POST("rest/upload")
    Call<ResponseBody> upload(@Part("username") RequestBody username,@Part("address") RequestBody address,
                              @Part MultipartBody.Part file);

    //上传多个文件
    @Multipart
    @POST("rest/upload")
    Call<ResponseBody> uploads(@PartMap Map<String, RequestBody> params);


    @Streaming
    @GET("image/{filename}")
    Call<ResponseBody> downFile(@Path("filename") String fileName);
}
