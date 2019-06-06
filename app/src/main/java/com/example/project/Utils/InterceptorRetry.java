package com.example.project.Utils;

import com.example.project.activity_fragments_class.StartActivity;

import java.io.IOException;
import java.util.logging.Logger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class InterceptorRetry implements Interceptor {
    private RequestBody rBody = null;

    public InterceptorRetry(RequestBody requestBody) {
        rBody = requestBody;
    }


    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Logger logger = Logger.getLogger("eC-logger");

        Request request;
        Request rreq;

        request = new Request.Builder()
                .url(chain.request().url()+"?repeat=false")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(rBody)
                .build();

        rreq = new Request.Builder()
                .url(chain.request().url()+"?repeat=true")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(rBody)
                .build();

        // try the request
        Response response = null;
        int tryCount = 1;
        boolean retry = false;

        while (tryCount <= StartActivity.MAX_TRY_COUNT) {
            try {
                if(retry) {
                    response = chain.proceed(rreq);
                } else {
                    response = chain.proceed(request);
                }
                break;
            } catch (Exception e) {
                try {
                    // sleep delay * try count (e.g. 1st retry after 3000ms, 2nd after 6000ms, etc.)
                    Thread.sleep(StartActivity.RETRY_BACKOFF_DELAY * tryCount);
                    logger.info("Coś poszło nie tak idę spać, spróbuję potem, to była próba: "+tryCount + " czas spania: "+StartActivity.RETRY_BACKOFF_DELAY*tryCount);
                } catch (InterruptedException e1) {
                    logger.info("InterruptedException: "+e1);
                }

                if (tryCount >= StartActivity.MAX_TRY_COUNT) {
                    // max retry count reached, giving up
                    logger.info("przekroczyłem maksymalny czas spania: "+e);
                    response = chain.proceed(rreq);
                }

                retry = true;
                tryCount++;
            }
        }

        // otherwise just pass the original response on
        return response;
    }
}