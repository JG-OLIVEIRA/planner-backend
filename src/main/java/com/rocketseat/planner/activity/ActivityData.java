package com.rocketseat.planner.activity;

import java.time.LocalDateTime;

public record ActivityData(String id, String title, LocalDateTime occurs_at) {
}
