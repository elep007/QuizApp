package com.ttb.quiz.connections;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Service {


    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(
            @Field("email") String email,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("user_register")
    Call<ResponseBody> user_register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("country") String country);


    @GET("countries_list")
    Call<ResponseBody> countries_list();


    @GET("get_rankings ")
    Call<ResponseBody> get_rankings();

    @GET("get_rankings_by_subtitle ")
    Call<ResponseBody> get_rankings_by_subtitle();


    @FormUrlEncoded
    @POST("forgot_password ")
    Call<ResponseBody> forgot_password(
            @Field("email") String email);

    @FormUrlEncoded
    @POST("sub_topic_rank ")
    Call<ResponseBody> sub_topic_rank(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_topic_data ")
    Call<ResponseBody> get_topic_data(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_rankings_by_user_country")
    Call<ResponseBody> get_rankings_by_user_country(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_rankings_by_user")
    Call<ResponseBody> get_rankings_by_user(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("search_users")
    Call<ResponseBody> search_users(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_results")
    Call<ResponseBody> get_results(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_challenge")
    Call<ResponseBody> get_challenge(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_acheivements")
    Call<ResponseBody> get_acheivements(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_questions")
    Call<ResponseBody> get_questions(
            @Field("sub_topic_id") String sub_topic_id);

    @FormUrlEncoded
    @POST("add_challenge")
    Call<ResponseBody> add_challenge(
            @Field("user_id") String user_id,
            @Field("topic_id") String topic_id,
            @Field("sub_topic_id") String sub_topic_id,
            @Field("competitor_id") String competitor_id);


    @FormUrlEncoded
    @POST("add_results")
    Call<ResponseBody> add_results(
            @Field("user_id") String user_id,
            @Field("topic_id") String topic_id,
            @Field("sub_topic_id") String sub_topic_id,
            @Field("points") String points,
            @Field("result") String result,
            @Field("competitor_id") String competitor_id);


    @FormUrlEncoded
    @POST("accept_challenge")
    Call<ResponseBody> accept_challenge(
            @Field("user_id") String user_id,
            @Field("game_id") String competitor_id);

    @FormUrlEncoded
    @POST("add_fcm")
    Call<ResponseBody> add_fcm(
            @Field("user_id") String user_id,
            @Field("fcm_id") String fcm_id);

    @FormUrlEncoded
    @POST("deny_challenge")
    Call<ResponseBody> deny_challenge(
            @Field("user_id") String user_id,
            @Field("game_id") String competitor_id);


    @FormUrlEncoded
    @POST("calculate_result")
    Call<ResponseBody> calculate_result(
            @Field("game_id") String game_id);

    @FormUrlEncoded
    @POST("add_points")
    Call<ResponseBody> add_points(
            @Field("user_id") String user_id,
            @Field("game_id") String game_id,
            @Field("user_type") String user_type,
            @Field("coins") String coins,
            @Field("points") String points);


    @Multipart
    @POST("profile_image")
    Call<ResponseBody> profile_image(@Part("user_id") RequestBody user_id,
                                     @Part MultipartBody.Part photo);
}