package cn.eric.game.fujiatianxia6.po;

/**
 * 
* <p>Title: Skill<／p>
* <p>Description: 技能接口<／p>
* <p>Company: want-want<／p> 
* @author 00322027
* @date 2017年8月3日 下午3:54:12
 */
public class Skill {
	private int id;
	private String name;
	private String memo;
	private int data;
	private int time; //时机
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	
}
