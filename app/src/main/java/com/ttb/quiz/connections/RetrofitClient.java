package com.ttb.quiz.connections;

import retrofit2.Retrofit;
public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Service getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Utils.base_url)
                    .build();
        }
        final Service service = retrofit.create(Service.class);
        return service;
    }

}
