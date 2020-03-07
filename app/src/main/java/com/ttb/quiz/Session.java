package com.ttb.quiz;

import android.app.Application;

import com.ttb.quiz.services.Foreground;

public class Session extends Application {
    
     String id,name,email,country,role,profile_pic,loginby,ranking,user_level,country_flag,total_points;
     String sub_topic_id,topic_id;
     String c_id,c_name,c_image,c_country_flag,c_country,c_profile_pic,game_id;
     String game_player_type;
     String coins ="0";
     boolean mydata=true;


    @Override
    public void onCreate() {
        super.onCreate();
        Foreground.init(this);
    }

    public boolean isMydata() {
        return mydata;
    }

    public void setMydata(boolean mydata) {
        this.mydata = mydata;
    }

    public String getC_country() {
        return c_country;
    }

    public void setC_country(String c_country) {
        this.c_country = c_country;
    }

    public String getGamae_result() {
        return gamae_result;
    }

    public void setGamae_result(String gamae_result) {
        this.gamae_result = gamae_result;
    }

    String gamae_result;


    public String getGame_player_type() {
        return game_player_type;
    }

    public void setGame_player_type(String game_player_type) {
        this.game_player_type = game_player_type;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getTotal_points() {
        return total_points;
    }

    public void setTotal_points(String total_points) {
        this.total_points = total_points;
    }

    public String getC_profile_pic() {
        return c_profile_pic;
    }

    public void setC_profile_pic(String c_profile_pic) {
        this.c_profile_pic = c_profile_pic;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_image() {
        return c_image;
    }

    public void setC_image(String c_image) {
        this.c_image = c_image;
    }

    public String getC_country_flag() {
        return c_country_flag;
    }

    public void setC_country_flag(String c_country_flag) {
        this.c_country_flag = c_country_flag;
    }

    public String getSub_topic_id() {
        return sub_topic_id;
    }

    public void setSub_topic_id(String sub_topic_id) {
        this.sub_topic_id = sub_topic_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getLoginby() {
        return loginby;
    }

    public void setLoginby(String loginby) {
        this.loginby = loginby;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public String getCountry_flag() {
        return country_flag;
    }

    public void setCountry_flag(String country_flag) {
        this.country_flag = country_flag;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }
}
