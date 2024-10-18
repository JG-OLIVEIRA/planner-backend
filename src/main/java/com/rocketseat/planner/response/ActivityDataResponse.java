package com.rocketseat.planner.response;

import java.time.LocalDateTime;

public record ActivityDataResponse(String id, String title, LocalDateTime occurs_at) {
}
