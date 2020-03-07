package com.ttb.quiz.Models;

public class RankListModel {
    String tittle;
    String globalrank;
    String indiarank;

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getGlobalrank() {
        return globalrank;
    }

    public void setGlobalrank(String globalrank) {
        this.globalrank = globalrank;
    }

    public String getIndiarank() {
        return indiarank;
    }

    public void setIndiarank(String indiarank) {
        this.indiarank = indiarank;
    }

    public RankListModel(String tittle, String globalrank, String indiarank) {
        this.tittle = tittle;
        this.globalrank = globalrank;
        this.indiarank = indiarank;
    }
}
