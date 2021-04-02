package org.greenspark404.kanbanboard.util;

import org.springframework.lang.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;

public class TaskUtil {

    public static String getLastModificationTimeText(@NonNull LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        if (dateTime.isAfter(now)) {
            throw new IllegalArgumentException("Modification time cannot be in future!");
        }
        Duration duration = Duration.between(dateTime, now);
        if (duration.toDays() != 0) {
            return duration.toDays() + " days ago";
        }
        if (duration.toHours() != 0) {
            return duration.toHours() + " hours ago";
        }
        if (duration.toMinutes() != 0) {
            return duration.toMinutes() + " minutes ago";
        }

        return "Just now";
    }
}
