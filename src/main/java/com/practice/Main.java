package com.practice;

import com.practice.Game;
import com.practice.GameRepo;
import com.practice.SteamAPI;
import com.practice.WorkerBee;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String steamId = "76561198145570899";
        WorkerBee bee = new WorkerBee(new SteamAPI());
        GameRepo gameRepo = new GameRepo();

        bee.savePlayer(steamId);
        // Строим индекс игр
        bee.GameIDbyName(steamId);

        // Ищем игру
        List<Game> results = bee.searchGameByName("satisfactory");

        if (results.isEmpty()) {
            System.out.println("Игра не найдена");
            return;
        }

        Game game = results.get(0);
        System.out.println("Нашли: " + game.getGameName());

        // Сохраняем
        gameRepo.save(steamId, game);
        System.out.println("Сохранена: " + game.getGameName());

        // Проверяем
        System.out.println("Отслеживается: " + gameRepo.isTracked(steamId, game.getGameID()));

        // Список отслеживаемых
        List<Game> tracked = gameRepo.getTrackedGames(steamId);
        System.out.println("Всего отслеживается: " + tracked.size());
        for (Game g : tracked) {
            System.out.println(" - " + g.getGameName());
        }

        AchievementRepo ar = new AchievementRepo();
        int appID = results.get(0).getGameID();
        GameAchievements ach = bee.getAchievements(steamId,appID);
        ar.saveAll(steamId,appID,ach);
        System.out.println("Достижений - "+ach.getAchList().size());
        System.out.println("Получено - "+ ar.countAchieved(steamId,appID));

    }
}