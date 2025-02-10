package com.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaxiServiceQualityStatus {
    AWAITING_REVIEW("Ожидает оценки"),   // Ожидает оценки качества
    APPROVED("Одобрено"),                // Оценка качества одобрена
    NEEDS_REVISION("Требует доработки"); // Требует доработки (например, плохая оценка)

    private final String name;
}
