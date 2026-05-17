package com.practice;

import java.util.List;

public class GameAchievements{
    private int gameID;
    private String gameName;
    private List<Achievement> achList;

    public GameAchievements(List<Achievement> achList) {
        this.achList = achList;
    }


    public static class Achievement {
        private String name,description;
        private int achID;
        private boolean isAchieved;

        public Achievement(int achID, String name, String description, boolean isAchieved) {
            this.name = name;
            this.description = description;
            this.achID = achID;
            this.isAchieved = isAchieved;
        }
    }


}
