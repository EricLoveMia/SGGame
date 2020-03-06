package cn.eric.game.fujiatianxia6.po;

import cn.eric.game.fujiatianxia6.factory.GeneralFactory;

import java.util.List;

/**
 * 
* <p>Title: Tavern<／p>
* <p>Description: 酒馆<／p>
* <p>Company: want-want<／p> 
* @author 00322027
* @date 2017年8月3日 下午3:34:11
 */
public class Tavern {
	
	//返回武将
	public static List<General> getGenerals(String id){
		return GeneralFactory.getWineGeneral(Integer.parseInt(id));
	}
	
	
	public static void getGeneral(General leader,General general){
		//leader
		leader.getGenerals().add(general);
        		
		general.setBelongTo(leader.getId());
	}


	public static void getGeneralFromTavern(General player, int i) {
		for (int j = 0; j < i; j++) {
	 		//从酒馆中获得武将
	 		List<General> wineGenerals = Tavern.getGenerals(player.getId());
	 		if(wineGenerals!= null && wineGenerals.size() > 0) {
				General general = wineGenerals.get((int) (Math.random() * (wineGenerals.size())));
				player.getGenerals().add(general);
				general.setBelongTo(player.getId());
				System.out.println(general.getName() + "拜入" + player.getName() + "账下");
			}else{
				System.out.println("暂无在野武将");
			}
		}
	}

}
