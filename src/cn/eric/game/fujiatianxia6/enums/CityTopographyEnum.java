package cn.eric.game.fujiatianxia6.enums;

/**
 * @author eric
 * @date 2020/06/08
 * @description 城市类型
 */
public enum CityTopographyEnum {

    /**
     * 陆地
     */
    LAND(1, "陆地", "骑>枪>弓>剑"),
    /**
     * 山地
     */
    HILE(2, "山地", "枪>弓>骑>剑"),
    /**
     * 水域
     */
    RIVER(3, "水域", "弓>枪>骑>剑"),
    ;
    private final Integer code;
    private final String text;
    private final String memo;

    CityTopographyEnum(Integer code, String text, String memo) {
        this.text = text;
        this.code = code;
        this.memo = memo;
    }

    public static String getText(Integer code) {
        for (CityTopographyEnum c : CityTopographyEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.text;
            }
        }
        return null;
    }

    public static String getMemo(Integer code) {
        for (CityTopographyEnum c : CityTopographyEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.memo;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public Integer getCode() {
        return code;
    }
}
