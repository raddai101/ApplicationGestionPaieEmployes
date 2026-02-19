package com.gestionpaie.utils;

import com.gestionpaie.model.Employe;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Gestionnaire de cache pour optimiser les performances
 * Réduit les appels à la base de données
 */
public class CacheManager {

    private static class CacheEntry<T> {
        T value;
        LocalDateTime timestamp;
        long ttlMinutes;

        CacheEntry(T value, long ttlMinutes) {
            this.value = value;
            this.timestamp = LocalDateTime.now();
            this.ttlMinutes = ttlMinutes;
        }

        boolean isExpired() {
            return ChronoUnit.MINUTES.between(timestamp, LocalDateTime.now()) > ttlMinutes;
        }
    }

    private static final Map<String, CacheEntry<?>> cache = new HashMap<>();
    private static final long DEFAULT_TTL_MINUTES = 15; // 15 minutes par défaut

    /**
     * Met en cache une valeur
     */
    public static <T> void put(String key, T value) {
        cache.put(key, new CacheEntry<>(value, DEFAULT_TTL_MINUTES));
    }

    /**
     * Met en cache une valeur avec TTL personnalisé
     */
    public static <T> void put(String key, T value, long ttlMinutes) {
        cache.put(key, new CacheEntry<>(value, ttlMinutes));
    }

    /**
     * Récupère une valeur du cache
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> type) {
        CacheEntry<?> entry = cache.get(key);
        if (entry != null && !entry.isExpired()) {
            return (T) entry.value;
        }
        if (entry != null) {
            cache.remove(key);
        }
        return null;
    }

    /**
     * Vérifie si une clé existe et est valide
     */
    public static boolean contains(String key) {
        CacheEntry<?> entry = cache.get(key);
        if (entry == null) return false;
        if (entry.isExpired()) {
            cache.remove(key);
            return false;
        }
        return true;
    }

    /**
     * Vide le cache
     */
    public static void clear() {
        cache.clear();
    }

    /**
     * Supprime la cache pour une clé
     */
    public static void remove(String key) {
        cache.remove(key);
    }

    /**
     * Retourne les statistiques du cache
     */
    public static Map<String, String> getStats() {
        Map<String, String> stats = new HashMap<>();
        stats.put("entries", String.valueOf(cache.size()));
        long validEntries = cache.values().stream().filter(e -> !e.isExpired()).count();
        stats.put("valid_entries", String.valueOf(validEntries));
        stats.put("expired_entries", String.valueOf(cache.size() - validEntries));
        return stats;
    }
}
