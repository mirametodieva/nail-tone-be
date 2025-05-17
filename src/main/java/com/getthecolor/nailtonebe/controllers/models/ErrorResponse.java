package com.getthecolor.nailtonebe.controllers.models;

import java.time.LocalDateTime;

public record ErrorResponse(String message, LocalDateTime timestamp) {}