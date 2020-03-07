package com.ttb.quiz.Models;

public class Result_model {

    String user_id,title,name,country,ranking,profile_pic,country_flag,result,level;
    String game_id,topicid,subtopicid;
    public Result_model(String user_id,String game_id,String topicid,String subtopicid,String title, String name, String country, String ranking, String profile_pic, String country_flag, String result, String level) {
        this.user_id = user_id;
        this.game_id = game_id;
        this.topicid = topicid;
        this.subtopicid = subtopicid;
        this.title = title;
        this.name = name;
        this.country = country;
        this.ranking = ranking;
        this.profile_pic = profile_pic;
        this.country_flag = country_flag;
        this.result = result;
        this.level = level;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTopicid() {
        return topicid;
    }

    public void setTopicid(String topicid) {
        this.topicid = topicid;
    }

    public String getSubtopicid() {
        return subtopicid;
    }

    public void setSubtopicid(String subtopicid) {
        this.subtopicid = subtopicid;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getCountry_flag() {
        return country_flag;
    }

    public void setCountry_flag(String country_flag) {
        this.country_flag = country_flag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
