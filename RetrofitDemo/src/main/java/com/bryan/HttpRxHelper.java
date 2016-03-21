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
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author：Cxb on 2016/3/18 15:18
 */
public class HttpRxHelper {

    private static final String TAG = "HttpRxHelper";
    public static final String BASE_URL = "http://192.168.6.59:8080/";

    private Retrofit retrofit;
    private MyRxService myService;

    private HttpRxHelper(){


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
               .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
               .client(httpClient.build())
               .build();
        myService=retrofit.create(MyRxService.class);

    }

    public static HttpRxHelper getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static HttpRxHelper instance = new HttpRxHelper();
    }

    public void getUser(){

        myService.getUser(1,"abc")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e(TAG,"onStart");
                    }

                    @Override
                    public void onCompleted() {
                        Log.e(TAG,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        Log.e(TAG,user.toString());
                    }
                });
    }

    public void getUserPost(){
        myService.getUserPost(1, "abcd")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e(TAG, "onStart");
                    }

                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                        try {
                            //输出原始类型
                            byte[] bytes=response.bytes();
                            Log.e(TAG,new String(bytes,"UTF-8"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void getUserPostBody(){
        User param=new User();
        param.setId(100);
        param.setAge(15);
        param.setName("user");

        myService.getUserPostBody(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e(TAG, "onStart");
                    }

                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<User> users) {
                       Log.e(TAG,users.toString());
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

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("formfile", file.getName(), requestFile);

        myService.upload(filename,filedes,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e(TAG, "onStart");
                    }


                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        Log.e(TAG, body.toString());
                    }
                });


    }



    public void downFile(final String fname){

        myService.downFile(fname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e(TAG, "onStart");
                    }


                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        Log.e(TAG, "success");
                        try {

                            String fileName = Environment.getExternalStorageDirectory() + "/" + fname;
                            FileOutputStream fos = new FileOutputStream(fileName);
                            InputStream is = body.byteStream();

                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                            }
                            is.close();
                            fos.close();

                        } catch (Exception ex) {
                            Log.e(TAG, ex.getMessage());
                        }
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
