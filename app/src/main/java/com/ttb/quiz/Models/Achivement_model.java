package com.ttb.quiz.Models;

public class Achivement_model {

    String title,image,games_to_play,coins;
    int progress;

    public Achivement_model(String title, String image, String games_to_play, String coins,int progress) {
        this.title = title;
        this.image = image;
        this.games_to_play = games_to_play;
        this.coins = coins;
        this.progress=progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGames_to_play() {
        return games_to_play;
    }

    public void setGames_to_play(String games_to_play) {
        this.games_to_play = games_to_play;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }
}
