package com.example.demo.entity;

public enum ProgressStatus {
    NOT_STARTED(0, "未着手"),
    IN_PROGRESS(1, "進行中"),
    COMPLETED(2, "完了");

    private final int value;
    private final String label;

    ProgressStatus(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static ProgressStatus fromValue(int value) {
        for (ProgressStatus status : values()) {
            if (status.value == value) return status;
        }
        throw new IllegalArgumentException("Invalid progress value: " + value);
    }
}
