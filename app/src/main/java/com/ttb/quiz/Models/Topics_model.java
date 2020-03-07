package com.ttb.quiz.Models;

public class Topics_model {

    String title,topic_id,sub_title,desc,level,image,sub_topic_id;
    int position;

    public Topics_model(String topic_id,String sub_topic_id,String title, String sub_title, String desc, String level, String image,int position) {
        this.topic_id = topic_id;
        this.sub_topic_id = sub_topic_id;
        this.title = title;
        this.sub_title = sub_title;
        this.desc = desc;
        this.level = level;
        this.image = image;
        this.position=position;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getSub_topic_id() {
        return sub_topic_id;
    }

    public void setSub_topic_id(String sub_topic_id) {
        this.sub_topic_id = sub_topic_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
