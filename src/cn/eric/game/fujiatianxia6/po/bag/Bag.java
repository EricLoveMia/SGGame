package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.General;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @description: 锦囊
 * @author: eric
 * @date: 2022-10-17 11:33
 **/
public interface Bag extends Serializable {

    void execute(General origin);

    String name();
}
