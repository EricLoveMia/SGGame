package cn.eric.game.fujiatianxia6.test;

import java.util.Random;

public class Test {

	/**
	 * 测试Map类
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println(new Random().nextInt(3) + 1);
//		Map map = new Map();  // ♖ ♗ ♔ ♕
//		map.createMap();
//		map.showMap(0,0);
		System.out.println((int) (1000 * ((Float.parseFloat("89")) / 50
				+ (Float.parseFloat("90")) / 50) / 100));

		System.out.println("1" + new Random().nextInt());

		int i = new Random().nextInt(20);
		System.out.println(i);

		int attLost = (int) (1000 * 1 * (3 * (float) ((int) (0.85 * (Math.max(90
				, 85)))) / 2000));

		System.out.println(attLost);

//		System.out.println((int)(5000 - 2000 * (0.1 + (float)((int)(Math.random() * 99))/1000)));
//		System.out.println((int)(5000 - 2000 * (0.1 + (float)((int)(Math.random() * 99))/1000 )));
//		System.out.println((int) (5000 * ((0.2 * 99/100) + (0.1 * 99/100))));

//		System.out.println(Math.ceil(1/2) + 1);
//		System.out.println( (int)100 * 400/ 1000 );
//		
//		System.out.println( 83 * (100 - 10)/100);
		
//		System.out.println((float)((int)(Integer.parseInt("89")))/1000);
//		
//		System.out.println((int) (50 * (Float.parseFloat("90"))/1000));
//		
//		int addLost = (int) (300 * (95)/150 + 90/50);
//		System.out.println(addLost);
		
		// 对于2个String类型对象，它们的Class对象相同
//		Class c1 = "Carson".getClass();
//		Class c2 =  Class.forName("java.lang.String");
//		// 用==运算符实现两个类对象地址的比较
//		System.out.println(c1 ==c2);
		// 输出结果：true
		
//		Boolean bo = true;
//		Class<?> classType = bo.getClass();
//		System.out.println(classType);
//
//		Class<?> classType2 = Boolean.class;
//		System.out.println(classType2);
//
//		Class<?> forName = Class.forName("java.lang.Boolean");
//		System.out.println(forName);
//
//	    Class<?> classType3 = Boolean.TYPE;
//	    System.out.println(classType3);
//
//	    System.out.println(bo instanceof Boolean);
	}
}
