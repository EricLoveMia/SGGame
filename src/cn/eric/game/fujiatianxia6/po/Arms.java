package cn.eric.game.fujiatianxia6.po;

import java.io.Serializable;

/**
 * @author Eric
 * @version 1.0
 * @ClassName: Arms
 * @Description: TODO
 * @company lsj
 * @date 2019/2/28 9:33
 **/
public class Arms implements Serializable {

    String name;

    /**
     * 1 - 10级
     */
    Integer level;

    boolean lock = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Arms(String name, Integer level) {
        this.name = name;
        this.level = level;
    }

    public Arms() {}
}
