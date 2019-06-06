package com.example.project.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.project.activity_fragments_class.StartActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connection_API {
    private PackageInfo packageInfo;
    private String versionName = null;
    private int versionCode;


    public Connection_API(Context context){
        try{
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        }catch(PackageManager.NameNotFoundException e){
            Log.d("App", "Err: " + e.getMessage());
        }
    }

    public String checkingValidCard_API(String fID, String uID, String kod) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fID", String.valueOf(fID))
                .addFormDataPart("uID", uID)
                .addFormDataPart("kod", kod)
                .build();
        return connect(requestBody);
    }

    public String checkingEmail_API(String fID, String credit) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fID", String.valueOf(fID))
                .addFormDataPart("credit", credit)
                .build();
        return connect(requestBody);
    }

    public String checkingPhone_API(String fID, String credit) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fID", String.valueOf(fID))
                .addFormDataPart("credit", credit)
                .build();
        return connect(requestBody);
    }

    public String login_API(String fID, String dID, String login, String password) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("dID", dID)
                .addFormDataPart("fID", String.valueOf(fID))
                .addFormDataPart("login", login)
                .addFormDataPart("haslo", password)
                .addFormDataPart("ver", versionName)
                .addFormDataPart("rev", String.valueOf(versionCode))
                .build();
        return connect(requestBody);
    }

    public String checkingOwnedCards_API(String fID, String dID, String login, String password) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("dID", dID)
                .addFormDataPart("fID", String.valueOf(fID))
                .addFormDataPart("login", login)
                .addFormDataPart("haslo", password)
                .addFormDataPart("ver", versionName)
                .addFormDataPart("rev", String.valueOf(versionCode))
                .build();
        return connect(requestBody);
    }

    public String checkingTransactions_API(String fID, String dID, String login, String password) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fID", fID)
                .addFormDataPart("dID", dID)
                .addFormDataPart("login", login)
                .addFormDataPart("haslo", password)
                .build();
        return connect(requestBody);
    }

    public String checkingPartners_API(String fID, String dID, String login, String password) {
        Log.v("wynik" , "checkingPartners_API");
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fID", fID)
                .addFormDataPart("dID", dID)
                .addFormDataPart("login", login)
                .addFormDataPart("haslo", password)
                .build();
        return connect(requestBody);
    }

    public String registration_API(String fID, String uID, String gender, String date, String zipCode, String phoneNumber, String email, String name) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fID", fID)
                .addFormDataPart("uID", uID)
                .addFormDataPart("p1", gender)
                .addFormDataPart("p2", date)
                .addFormDataPart("p3", zipCode)
                .addFormDataPart("p4", phoneNumber)
                .addFormDataPart("p5", email)
                .addFormDataPart("p6", name)
                .build();
        return connect(requestBody);
    }



    protected String connect(RequestBody requestBody) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new InterceptorRetry(requestBody))
                .addInterceptor(new InterceptorLogging())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        Request request = new Request.Builder()
                .url(StartActivity.scr_url)
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        Response response = null;

        String ret = "";

        try {
            response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                ret = response.body().string().replace("\n", "");
            }
            //response.body().close();
        } catch (IOException e) {
            Log.v("eC-okhttp", "err: "+e);
        }

        return ret;
    }

}
