package cn.eric.game.fujiatianxia6.service;

public class Util {

	public static float getMaxFloatNum(int num) {
		float result = 0;
		
		for (int i=0;i<num;i++){
			float temp = (float) Math.random();
			if(temp > result){
				result = temp;
			}
		}
		return result;
	}

}
