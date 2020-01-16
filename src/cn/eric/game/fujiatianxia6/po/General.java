package cn.eric.game.fujiatianxia6.po;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
/**
 * 
* <p>Title: General<／p>
* <p>Description: 武将<／p>
* <p>Company: want-want<／p> 
* @author 00322027
* @date 2017年8月3日 下午3:43:23
 */
@XStreamAlias("general")
public class General implements Cloneable{
	@XStreamAsAttribute
	private String id; 
	
	private List<General> generals; //属下武将
	
	@XStreamAlias("name")
	private String name;
	
	@XStreamAlias("king")
	private String king;        // 是否是君主
	
	@XStreamAlias("attack")
	private String attack;       // 攻击力
	
	@XStreamAlias("command")
	private String command;      // 统帅力
	
	@XStreamAlias("intelligence")
	private String intelligence; // 智力
	
	@XStreamAlias("charm")
	private String charm;        // 魅力
	
	@XStreamAlias("politics")
	private String politics;     // 政治
	
	@XStreamAlias("vitality")
	private String vitality;     // 生命力
	
	@XStreamAlias("arms")
	private String arms;        // 兵种
	
	@XStreamAlias("landfc")
	private String landfc;      // 平原战斗力
	
	@XStreamAlias("mountainfc")
	private String mountainfc;  // 山地战斗力 fighting capacity
	
	@XStreamAlias("riverfc")
	private String riverfc;     // 水中战斗力 fighting capacity
	
	@XStreamAlias("skill")
	private String skill;       // 技能
	
	@XStreamAlias("relations")
	private String relations;  //与4个君主的相亲性 最大99 最小0 相性越大被招募的金钱越小且酒馆出现的几率越大，同时献城的可能性越大，同时野战或攻城中倒戈的可能性越大
	
	@XStreamAlias("belongTo")
	private String belongTo;    // 状态 0表示在野 1表示刘备 2表示曹操 3表示孙权 4表示董卓
	
	@XStreamAlias("status")
	private String status;     // 武将状态 0表示正常 1表示劳累 2表示生病 3表示死亡 4表示被俘虏中
	
	private Integer money;
	
	private Integer army;
	
	private String Cityid = "";
	
	private Weapon weapon;
	
	private Integer cavalrys; // 骑兵数量
	private Integer infantry; // 枪兵数量
	private Integer archers;  // 弓箭手数量
	
	private List<Arms> armsTotal = new ArrayList<>(); //兵种的等级及属性

	private Rsearch research; // 剩余研究时间

	private boolean reboot; // 是否为机器人

	private Integer reputation; // 声望

	public General(){
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		General general = (General) super.clone();
		return general;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKing() {
		return king;
	}

	public void setKing(String king) {
		this.king = king;
	}

	public String getAttack() {
		return attack;
	}

	public void setAttack(String attack) {
		this.attack = attack;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(String intelligence) {
		this.intelligence = intelligence;
	}

	public String getCharm() {
		return charm;
	}

	public void setCharm(String charm) {
		this.charm = charm;
	}

	public String getPolitics() {
		return politics;
	}

	public void setPolitics(String politics) {
		this.politics = politics;
	}

	public String getVitality() {
		return vitality;
	}

	public void setVitality(String vitality) {
		this.vitality = vitality;
	}

	public String getArms() {
		return arms;
	}

	public void setArms(String arms) {
		this.arms = arms;
	}

	public String getLandfc() {
		return landfc;
	}

	public void setLandfc(String landfc) {
		this.landfc = landfc;
	}

	public String getMountainfc() {
		return mountainfc;
	}

	public void setMountainfc(String mountainfc) {
		this.mountainfc = mountainfc;
	}

	public String getRiverfc() {
		return riverfc;
	}

	public void setRiverfc(String riverfc) {
		this.riverfc = riverfc;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getRelations() {
		return relations;
	}

	public void setRelations(String relations) {
		this.relations = relations;
	}

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<General> getGenerals() {
		return generals;
	}

	public void setGenerals(List<General> generals) {
		this.generals = generals;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getArmy() {
		return army;
	}

	public void setArmy(Integer army) {
		this.army = army;
	}

	public String getCityid() {
		return Cityid;
	}

	public void setCityid(String cityid) {
		Cityid = cityid;
	}

	public int computeAttack(){
		return Integer.parseInt(attack);
	}
	
	public int computeDefence(){
		return Integer.parseInt(command);
	}
	
	public int computeVitality(){
		return Integer.parseInt(vitality);
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		if(weapon != null) {
			this.weapon = weapon;
			this.attack = Integer.parseInt(this.attack) + weapon.getAttack() + "";
			this.command = Integer.parseInt(this.command) + weapon.getCommand() + "";
			this.charm = Integer.parseInt(this.charm) + weapon.getCharm() + "";
			this.intelligence = Integer.parseInt(this.intelligence) + weapon.getIntelligence() + "";
			this.politics = Integer.parseInt(this.politics) + weapon.getPolitics() + "";
		}
	}

	public Integer getCavalrys() {
		return cavalrys;
	}

	public void setCavalrys(Integer cavalrys) {
		this.cavalrys = cavalrys;
	}

	public Integer getInfantry() {
		return infantry;
	}

	public void setInfantry(Integer infantry) {
		this.infantry = infantry;
	}

	public Integer getArchers() {
		return archers;
	}

	public void setArchers(Integer archers) {
		this.archers = archers;
	}

	public List<Arms> getArmsTotal() {
		return armsTotal;
	}

	public void setArmsTotal(List<Arms> armsTotal) {
		this.armsTotal = armsTotal;
	}

	public Rsearch getResearch() {
		return research;
	}

	public void setResearch(Rsearch research) {
		this.research = research;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
        if (obj instanceof General) {
        	General general = (General) obj;
            return (id.equals(general.id));
        }
        return super.equals(obj);
	}

	@Override
	public String toString() {
		return "General [姓名 =" + name + ", 攻击 =" + attack + ", 统帅 =" + command + ", 智力 ="
				+ intelligence + ", 魅力 =" + charm + ", 政治 =" + politics + ", vitality=" + vitality + ", landfc="
				+ landfc + ", mountainfc=" + mountainfc + ", riverfc=" + riverfc + "]";
	}

	public boolean isReboot() {
		return reboot;
	}

	public void setReboot(boolean reboot) {
		this.reboot = reboot;
	}

	public Integer getReputation() {
		return reputation;
	}

	public void setReputation(Integer reputation) {
		this.reputation = reputation;
	}
}
