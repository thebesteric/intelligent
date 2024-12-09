package io.github.thebesteric.project.intelligent.core.constant.security;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author guidy
 * @Date 2024-10-18 10:46
 */
@Getter
@AllArgsConstructor
public enum DeviceType {
    // PC、APP、MP (小程序)
    PC("PC", "WEB 端"),
    APP("APP", "APP"),
    H5("H5", "H5"),
    MP("MP", "小程序");

    private final String device;
    private final String desc;

    @JsonCreator
    public static DeviceType ofDevice(String device) {
        return Arrays.stream(DeviceType.values()).filter(i -> ObjectUtil.equals(i.getDevice(), device)).findFirst().orElse(null);
    }

}
