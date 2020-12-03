package com.example.coronastat;

public class State {
    private String stateName;
    private int coronaCount;

    public int getCoronaCount() {
        return coronaCount;
    }

    public void setCoronaCount(int coronaCount) {
        this.coronaCount = coronaCount;
    }

    public State() {

    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public State(String stateName) {
        this.stateName = stateName;
    }
}
