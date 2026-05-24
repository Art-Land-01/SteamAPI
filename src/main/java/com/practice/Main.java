package com.practice;

import static com.practice.Database.getJDBI;

public class Main {
    public static void main(String[] args) {
        try {
            getJDBI().useHandle(handle -> handle.execute("Select 1"));
            System.out.println("ok");
        }
        catch (Exception e){
            System.out.println("not ok");
        }
        WorkerBee bee = new WorkerBee(new SteamAPI());
        UserRepository userRepo = new UserRepository(Database.getJDBI());

        String steamId = "76561198145570899";
        PlayerStat player = bee.getPlayer(steamId);

        userRepo.save(player, steamId);
        System.out.println("Сохранён: " + userRepo.exists(steamId));
        userRepo.updateLastSync(steamId);
    }


        //AllGamesStat s = new AllGamesStat("76561198145570899");
       // AllGamesStat.getGamesInfo("76561198145570899");


}