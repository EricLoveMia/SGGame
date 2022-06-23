package cn.eric.game.fujiatianxia6.po;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("generals")
public class Generals {
	
	@XStreamAsAttribute
	private String name;
	
	@XStreamImplicit(itemFieldName = "general")
	private final List<General> generalList = new ArrayList<General>();
}
