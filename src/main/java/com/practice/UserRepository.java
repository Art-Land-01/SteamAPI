package com.practice;

import org.jdbi.v3.core.Jdbi;

public class UserRepository {
    private final Jdbi jdbi;

    public UserRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    // Сохранить пользователя
    public void save(PlayerStat player, String steamId) {
        jdbi.useHandle(handle ->
                handle.createUpdate(
                                "INSERT INTO users (steam_id, username, avatar_url) " +
                                        "VALUES (:steamId, :username, :avatarUrl) " +
                                        "ON CONFLICT (steam_id) DO UPDATE " +
                                        "SET username = :username, avatar_url = :avatarUrl"
                        )
                        .bind("steamId", steamId)
                        .bind("username", player.getName())
                        .bind("avatarUrl", player.getAvatar_link())
                        .execute()
        );
    }

    // Проверить существует ли пользователь
    public boolean exists(String steamId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM users WHERE steam_id = :steamId")
                        .bind("steamId", steamId)
                        .mapTo(Integer.class)
                        .one() > 0
        );
    }

    // Обновить время синхронизации
    public void updateLastSync(String steamId) {
        jdbi.useHandle(handle ->
                handle.createUpdate(
                                "UPDATE users SET last_sync = NOW() WHERE steam_id = :steamId"
                        )
                        .bind("steamId", steamId)
                        .execute()
        );
    }
}