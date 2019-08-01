package cn.eric.game.fujiatianxia6.po;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("generals")
public class Generals {
	
	@XStreamAsAttribute
	private String name;
	
	@XStreamImplicit(itemFieldName = "general")
	private List<General> generalList = new ArrayList<General>();
}
