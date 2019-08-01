package cn.eric.game.fujiatianxia6.po;

/**
 * @author Eric
 * @version 1.0
 * @ClassName: Search
 * @Description: TODO
 * @company lsj
 * @date 2019/7/11 18:22
 **/
public class Rsearch {

    String name;
    Integer level;
    Integer time;

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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Rsearch(String name, Integer level) {
        this.name = name;
        this.level = level;
        // 研究的时间是级别乘2
        this.time = (level-1)*2==0?1:(level-1)*2;
    }
}
