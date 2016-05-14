package com.bryan;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public static final String BASE_URL = "http://192.168.1.104:8080/mobile/";

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

    public void findUserForGet(){

        myService.findUserForGet(12,"张明明","北京海淀区")
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
                        Log.e(TAG,Thread.currentThread().getName());
                        Log.e(TAG,user.toString());
                    }
                });
    }

    public void findUserForPost(){
        myService.findUserForPost(9,"陈玄功","恶人谷")
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

                        Log.e(TAG,getResponsString(response));

                    }
                });
    }



    public void findUserList(){

       myService.findUserList()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Subscriber<List<User>>() {

                   @Override
                   public void onStart() {
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

    public void postBodyJson(){
        User param=new User();
        param.setId(2);
        param.setUsername("李明");
        param.setBirthday("1995-09-06 09-09-08");
        param.setSex("1");

        myService.postBodyJson(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {

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
                    public void onNext(User user) {
                       Log.e(TAG,user.toString());
                    }
                });
    }

    public void upload(){
        File file=new File(Environment.getExternalStorageDirectory(),"测试01.jpg");


        RequestBody username =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "jim");

        RequestBody address =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "天津市");

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
                MultipartBody.Part.createFormData("file", file.getName(), countingRequestBody);


        myService.upload(username, address, body)
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
                        Log.e(TAG, getResponsString(body));
                    }
                });


    }


    public void uploads(){
        Map<String,RequestBody> params=new LinkedHashMap<>();
        File file1=new File(Environment.getExternalStorageDirectory(),"测试01.jpg");
        RequestBody filebody1 =RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        //记录文件上传进度
        CountingRequestBody countingRequestBody1=new CountingRequestBody(filebody1, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                Log.e(TAG,"file1:"+contentLength+":"+bytesWritten);
            }
        });
        //file代表服务器接收到的key,file1.getName()代表文件名
        params.put("file\";filename=\""+file1.getName(),countingRequestBody1);



        File file2=new File(Environment.getExternalStorageDirectory(),"girl.jpg");
        RequestBody filebody2 =RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        CountingRequestBody countingRequestBody2=new CountingRequestBody(filebody2, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                Log.e(TAG,"file2:"+contentLength+":"+bytesWritten);
            }
        });
        params.put("file\";filename=\""+file2.getName(),countingRequestBody2);


        File file3=new File(Environment.getExternalStorageDirectory(),"测试02.jpg");
        RequestBody filebody3 =RequestBody.create(MediaType.parse("multipart/form-data"), file3);
        CountingRequestBody countingRequestBody3=new CountingRequestBody(filebody3, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                Log.e(TAG,"file3:"+contentLength+":"+bytesWritten);
            }
        });
        params.put("file\";filename=\""+file3.getName(),countingRequestBody3);



        params.put("username",   RequestBody.create(
                MediaType.parse("multipart/form-data"), "jim"));
        params.put("address", RequestBody.create(
                MediaType.parse("multipart/form-data"), "天津市"));


        myService.uploads(params)
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
                        Log.e(TAG, getResponsString(body));
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
                        Log.e(TAG, "success");
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


    private String getResponsString(ResponseBody body){
        try {
            byte[] bytes=body.bytes();
            return new String(bytes,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
