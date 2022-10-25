# SGGame

这是一个纯文字版的三国回合制战旗战略游戏，目前只支持人机对战 4方混战<br>
你可以招揽三国武将，占领城池 ，发展经济 发展军事力量<br>
多个主公任你选择，每个主公都有强力的技能助你一统天下<br>
刘备：每回合有几率直接获取一个新武将，被俘虏的武将直接加入阵营；<br>
曹操：野战王者，庞大的武将群，陆战无敌，综合实力最强；<br>
孙权：水战无敌，每回合可以进行两次建设 孙坚存活前20回合 孙策存活前40回合<br>
董卓：初始最为强大的军队，可以迅速占领大片城市，武将质量较低，需要多跑酒馆，吕布、华雄单挑独步天下<br>
袁绍：四世三公，初始声望最高，文武双全，初始兵种均衡，河北四柱庭<br>
刘表：初始拥有黄忠、魏延、甘宁，水战仅次于孙权，赌博赢率提升10%<br>


每个武将都有技能，合理利用武将的技能才能让你获得胜利<br>
目前已经有了100余名武将<br>

战役剧本如下：每个战役结束会奖励主公 金钱 兵力 或者武器
刘备的战役地图：黄巾起义、讨伐董卓、徐州会战、赤壁之战、荆州之战、益州之战、夷陵之战、一统天下
曹操的战役地图：黄巾起义、讨伐董卓、中原之战、官渡之战、赤壁之战、益州之战、合肥之战、一统天下
孙权的战役地图：黄巾起义、讨伐董卓、平定江东、赤壁之战、荆州之战、合肥之战、夷陵之战、一统天下
董卓的战役地图：黄巾起义、讨伐董卓、中原之战、官渡之战、荆州之战、赤壁之战、益州之战、一统天下
袁绍的战役地图：黄巾起义、讨伐董卓、平定北境、官渡之战、荆州之战、赤壁之战、益州之战、一统天下

13个战役地图(部分未开发)
黄巾起义、讨伐董卓、平定江东、平定河北、徐州会战、荆州之战、赤壁之战、夷陵之战、官渡之战、
汉中攻防战、合肥之战、一统天下


bug记录
1、守城副将单挑后没有返回城市 已经解决
2、攻城后兵种数量变成负数  已经解决
3、火神技能有问题 已经解决
4、导出后城市武将有问题  正在解决
5、城市兵会成为负数  待解决
6、城市要有兵力上限  10000/3000/3000/3000  每升一级 增加  3000/1000/1000/1000
7、故事模式更换场景时没有初始化 已经解决
8、建造了马厩或者兵器厂后剑兵归零 已经解决

优化记录
1、重建主公和武将的好恶关系，方便扩展 - 暂缓
2、增加攻城的损失（根据兵的数量几何增加攻城的难度），增加攻城器械的购买和使用
3、增加锦囊妙计
4、单挑和野战结束后生命值要降低，不能一个武将用到底
5、故事模式的胜利方式变得多样，初始城池内可以有武将和士兵（NPC）

经验系统  主公等级（未开发）
初始  
     1级   100   1000士兵   
     2级   300   开放兵种训练系统
     3级   1000  开放锦囊系统,赠送一个锦囊
     4级   3000  赠送三个锦囊
     5级   开放器械系统
     6级   赠送一个器械
     7级   开放武器阁，可以购买武器
     8级   赠送武器一把
     9级   最高级 开放高级兵种
      