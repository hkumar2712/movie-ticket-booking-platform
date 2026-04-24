package com.hkumar.movie_ticket_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatLockService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final long LOCK_TTL = 5; // minutes

    public boolean lockSeats(Long showId, List<Long> seatIds, Long userId) {
        List<String> lockedKeys = new ArrayList<>();

        try {
            for (Long seatId : seatIds) {

                String key = getKey(showId, seatId);

                Boolean success = redisTemplate.opsForValue()
                        .setIfAbsent(key, userId, Duration.ofMinutes(5));

                if (Boolean.FALSE.equals(success)) {
                    releaseSeats(lockedKeys);
                    return false;
                }

                lockedKeys.add(key);
            }

            return true;

        } catch (Exception ex) {
            releaseSeats(lockedKeys);
            throw ex;
        }
    }

    public void releaseSeats(List<String> keys) {
        redisTemplate.delete(keys);
    }

    public void releaseSeatsBySeatIds(Long showId, List<Long> seatIds) {

        List<String> keys = seatIds.stream()
                .map(seatId -> getKey(showId, seatId))
                .toList();

        redisTemplate.delete(keys);
    }

    private String getKey(Long showId, Long seatId) {
        return "seat_lock:" + showId + ":" + seatId;
    }
}