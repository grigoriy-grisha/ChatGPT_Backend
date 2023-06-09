package com.chatgpt.entity;

import java.util.List;

public class LeetCodeProblemResult {
    String user_name;
    int num_solved;
    int num_total;
    int ac_easy;
    int ac_medium;
    int ac_hard;

    int frequency_high;
    int frequency_mid;
    String category_slug;


    List<LeetCodeProblem> stat_status_pairs;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getNum_solved() {
        return num_solved;
    }

    public void setNum_solved(int num_solved) {
        this.num_solved = num_solved;
    }

    public int getNum_total() {
        return num_total;
    }

    public void setNum_total(int num_total) {
        this.num_total = num_total;
    }

    public int getAc_easy() {
        return ac_easy;
    }

    public void setAc_easy(int ac_easy) {
        this.ac_easy = ac_easy;
    }

    public int getAc_medium() {
        return ac_medium;
    }

    public void setAc_medium(int ac_medium) {
        this.ac_medium = ac_medium;
    }

    public int getAc_hard() {
        return ac_hard;
    }

    public void setAc_hard(int ac_hard) {
        this.ac_hard = ac_hard;
    }

    public int getFrequency_high() {
        return frequency_high;
    }

    public void setFrequency_high(int frequency_high) {
        this.frequency_high = frequency_high;
    }

    public int getFrequency_mid() {
        return frequency_mid;
    }

    public void setFrequency_mid(int frequency_mid) {
        this.frequency_mid = frequency_mid;
    }

    public String getCategory_slug() {
        return category_slug;
    }

    public void setCategory_slug(String category_slug) {
        this.category_slug = category_slug;
    }

    public List<LeetCodeProblem> getStat_status_pairs() {
        return stat_status_pairs;
    }

    public void setStat_status_pairs(List<LeetCodeProblem> stat_status_pairs) {
        this.stat_status_pairs = stat_status_pairs;
    }
}
