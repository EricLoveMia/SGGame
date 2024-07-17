package cn.eric.game.fujiatianxia6.po;

import java.io.Serializable;

/**
 * @author Eric
 * @version 1.0
 * @ClassName: Weapon
 * @Description: TODO
 * @date 2019/2/28 9:39
 **/
public class Weapon implements Serializable {

    private Integer id;
    private String name;
    private Integer price = 0;
    // 技能增加值
    private Integer data = 0;
    private String generalId;
    private Integer attack = 0;
    // 统帅力+
    private Integer command = 0;
    // 智力+
    private Integer intelligence = 0;
    // 魅力+
    private Integer charm = 0;
    // 政治+
    private Integer politics = 0;
    // 新技能（替换老的技能）
    private String newSkillId;

    private String memo;

    private boolean hasGet = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public String getGeneralId() {
        return generalId;
    }

    public void setGeneralId(String generalId) {
        this.generalId = generalId;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getCharm() {
        return charm;
    }

    public void setCharm(Integer charm) {
        this.charm = charm;
    }

    public Integer getPolitics() {
        return politics;
    }

    public void setPolitics(Integer politics) {
        this.politics = politics;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isHasGet() {
        return hasGet;
    }

    public void setHasGet(boolean hasGet) {
        this.hasGet = hasGet;
    }

    public String getNewSkillId() {
        return newSkillId;
    }

    public void setNewSkillId(String newSkillId) {
        this.newSkillId = newSkillId;
    }

    @Override
    public String toString() {
        return "装备 {" +
                "名字 ='" + name + '\'' +
                ", 攻击 =" + attack +
                ", 统帅 =" + command +
                ", 智力 =" + intelligence +
                ", 魅力 =" + charm +
                ", 政治 =" + politics +
                ", 说明 ='" + memo + '\'' +
                '}';
    }
}
