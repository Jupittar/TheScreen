package com.github.jupittar.thescreen.data.remote.interceptor;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

@SuppressWarnings("unused")
public class HttpLoggingInterceptor implements Interceptor {

    private String stringifyRequestBody(Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            if (copy.body() == null) {
                return "";
            }
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();

        if (request.method().equals("GET")) {
            Logger.i(String.format("%s: %s%n%s%n",
                    request.method(),
                    request.url(), request.headers()));
        } else {
            Logger.i(String.format("%s: %s%n%s%nBODY: %s",
                    request.method(),
                    request.url(), request.headers(),
                    stringifyRequestBody(request)
            ));
        }

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Logger.i(String.format(Locale.CHINA, "Received response from %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        ResponseBody responseBody = response.body();
        String responseBodyString = response.body().string();
        Logger.json(responseBodyString);

        return response.newBuilder()
                .body(ResponseBody.create(responseBody.contentType(), responseBodyString.getBytes()))
                .build();
    }

}
