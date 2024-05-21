package com.ruoyi.common.enums;

public enum StorageStatus {


    QUALIFIED(0, "合格"), DEFECTIVE(1, "残次"),

    SPECIAL(2, "特采");

    private final int code;

    private final String info;

    StorageStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static String getInfo(int code) {
        for (StorageStatus status : StorageStatus.values()) {
            if (status.getCode() == code) {
                return status.getInfo();
            }
        }
        return null;
    }

    public static StorageStatus getStorageStatus(int code) {
        for (StorageStatus status : StorageStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }


}
