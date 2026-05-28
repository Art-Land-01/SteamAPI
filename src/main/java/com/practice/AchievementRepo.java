package com.practice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AchievementRepo {

    // Сохранить все достижения
    public void saveAll(String steamId, int appId, GameAchievements achievements) {
        String sql = "INSERT INTO achievements (steam_id, app_id, ach_name, achieved, unlock_time) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (steam_id, app_id, ach_name) DO UPDATE " +
                "SET achieved = EXCLUDED.achieved, unlock_time = EXCLUDED.unlock_time";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (GameAchievements.Achievement ach : achievements.getAchList()) {
                stmt.setString(1, steamId);
                stmt.setInt(2, appId);
                stmt.setString(3, ach.getName());
                stmt.setBoolean(4, ach.isAchieved());
                stmt.setTimestamp(5, ach.isAchieved() ? new Timestamp(System.currentTimeMillis()) : null);
                stmt.addBatch(); // накапливаем запросы
            }
            stmt.executeBatch(); // выполняем все разом

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Получить достижения по игре
    public List<GameAchievements.Achievement> getByGame(String steamId, int appId) {
        String sql = "SELECT ach_name, achieved FROM achievements " +
                "WHERE steam_id = ? AND app_id = ?";
        List<GameAchievements.Achievement> list = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, steamId);
            stmt.setInt(2, appId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new GameAchievements.Achievement(
                        0,
                        rs.getString("ach_name"),
                        "",
                        rs.getBoolean("achieved")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Сколько достижений получено
    public int countAchieved(String steamId, int appId) {
        String sql = "SELECT COUNT(*) FROM achievements " +
                "WHERE steam_id = ? AND app_id = ? AND achieved = true";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, steamId);
            stmt.setInt(2, appId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}