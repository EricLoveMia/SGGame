package cn.eric.game.fujiatianxia6.enums;

/**
 * @author YCKJ2106
 * @date 2020/06/08
 * @description 流程审批状态
 */
public enum FightSingleAttackTypeEnum {

    /**
     * 拒绝
     */
    DEFENCE(0, "一直防守"),
    /**
     * 批准
     */
    ATTACK(1, "一直进攻"),
    /**
     * 撤回
     */
    DEFAULT(9, "默认"),
    ;
    private final Integer code;
    private final String text;

    FightSingleAttackTypeEnum(Integer code, String text) {
        this.text = text;
        this.code = code;
    }

    public static String getText(Integer code) {
        for (FightSingleAttackTypeEnum c : FightSingleAttackTypeEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.text;
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
