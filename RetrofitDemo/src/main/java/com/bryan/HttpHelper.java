package com.bryan;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：Cxb on 2016/3/18 15:18
 */
public class HttpHelper {

    private static final String TAG = "HttpHelper";
    public static final String BASE_URL = "http://192.168.6.59:8080/";

    private Retrofit retrofit;
    private MyService myService;

    private HttpHelper(){


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
       // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);



        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

       // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!


       retrofit=new Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .client(httpClient.build())
               .build();
        myService=retrofit.create(MyService.class);

    }

    public static HttpHelper getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static HttpHelper instance = new HttpHelper();
    }

    public void getUser(){
        Call<User> userCall=myService.getUser(1,"abc");
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //主线程
                Log.e(TAG,Thread.currentThread().getName());
                Log.e(TAG,response.body().toString());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

    public void getUserPost(){
        Call<ResponseBody> userCall=myService.getUserPost(1, "abcd");
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG,response.body().toString());
                try {
                    //输出原始类型
                    byte[] bytes=response.body().bytes();
                    Log.e(TAG,new String(bytes,"UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

    public void getUserPostBody(){
        User param=new User();
        param.setId(100);
        param.setAge(15);
        param.setName("user");
        Call<List<User>> userCall=myService.getUserPostBody(param);
        userCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.e(TAG,response.body().toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

    public void upload(){
        File file=new File(Environment.getExternalStorageDirectory(),"qa.docx");


        RequestBody filename =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "fname");

        RequestBody filedes =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "f我");

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        CountingRequestBody countingRequestBody=new CountingRequestBody(requestFile, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                Log.e(TAG,contentLength+":"+bytesWritten);
            }
        });

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("formfile", file.getName(), countingRequestBody);


        Call<ResponseBody> userCall=myService.upload(filename, filedes, body);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG, response.body().toString());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }



    public void downFile(final String fname){
        Call<ResponseBody> userCall=myService.downFile(fname);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                 Log.e(TAG,"success");
                try {

                    String fileName=Environment.getExternalStorageDirectory()+"/"+fname;
                    FileOutputStream fos=new FileOutputStream(fileName);
                    InputStream is=response.body().byteStream();

                    byte[] buf=new byte[1024];
                    int len;
                    while ((len=is.read(buf))!=-1){
                        fos.write(buf,0,len);
                    }
                    is.close();
                    fos.close();

                }catch (Exception ex){
                    Log.e(TAG,ex.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }


    private String guessMimeType(String fileName)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(fileName);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
