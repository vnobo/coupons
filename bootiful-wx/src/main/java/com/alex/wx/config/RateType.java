package com.alex.wx.config;

/**
 * rebate-alliance Commission
 * Created by 2019-02-27
 * 全局分成比率
 *
 * @author Alex bob(https://github.com/vnobo)
 */
public enum RateType {

    /**
     * 用户返利比率
     */
    USER_COMMISSION_RATE(0.7),
    /**
     * 推广返利比率
     */
    BROKERAGE_COMMISSION_RATE(0.1);

    private final double _value;

    RateType(double value) {
        this._value = value;
    }

    public static RateType from(int val) {
        for (RateType type : RateType.values()) {
            if (type.value() == val) {
                return type;
            }
        }
        return null;
    }

    public double value() {
        return _value;
    }
}
