package com.practice;

import org.jdbi.v3.core.Jdbi;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/SteamAPI";
    private static final String USR = "postgres";
    private static final String PSWRD = "faku228322";
    private static Jdbi jdbi;

    public static Jdbi getJDBI(){
        if(jdbi==null)
            jdbi = Jdbi.create(URL,USR,PSWRD);
        return jdbi;
    }



}
