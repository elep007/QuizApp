package com.ttb.quiz.Models;

public class View_pager_model {
    String id;
    String topic_id;
    String quiz_length;
    String title;
    String subtitle;
    String desc;
    String price;
    String image;
    String date;
    String status;

    String name;
    String country,country_flag;

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    String profile_pic;
    String sub_topic_id;

    public String getSubtopic_points() {
        return subtopic_points;
    }

    public void setSubtopic_points(String subtopic_points) {
        this.subtopic_points = subtopic_points;
    }

    String subtopic_points;

    public String getSub_topic_id() {
        return sub_topic_id;
    }

    public void setSub_topic_id(String sub_topic_id) {
        this.sub_topic_id = sub_topic_id;
    }

    public String getCountry_flag() {
        return country_flag;
    }

    public void setCountry_flag(String country_flag) {
        this.country_flag = country_flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getQuiz_length() {
        return quiz_length;
    }

    public void setQuiz_length(String quiz_length) {
        this.quiz_length = quiz_length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
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

    public View_pager_model(String id, String name, String country, String profile_pic,String sub_topic_id,String subtopic_points,String topic_id, String quiz_length, String title, String subtitle, String desc, String price, String image, String date, String status, String rank, String country_flag) {
        this.id = id;
        this.sub_topic_id=sub_topic_id;
        this.subtopic_points=subtopic_points;
        this.name = name;
        this.country=country;
        this.profile_pic=profile_pic;
        this.topic_id = topic_id;
        this.quiz_length = quiz_length;
        this.title = title;
        this.subtitle = subtitle;
        this.desc = desc;
        this.price = price;
        this.image = image;
        this.date = date;
        this.status = status;
        this.rank = rank;
        this.country_flag = country_flag;
    }

    String rank;
}
