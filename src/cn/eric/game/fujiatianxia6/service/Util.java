package cn.eric.game.fujiatianxia6.service;

import java.io.*;

/**
 * @ClassName:  工具类
 * @Description: 文件操作
 * @Param:
 * @Return:
 * @Author: YCKJ2725
 * @Date: 2020/1/15 17:31
**/
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

	public static String readFileContentAsLine(String filePath){
		File file = new File(filePath);

		BufferedReader reader = null;
		StringBuffer stringBuffer = new StringBuffer();

		try {
			reader = new BufferedReader(new FileReader(file));
			String tempStr;
			while((tempStr = reader.readLine()) != null){
				stringBuffer.append(tempStr);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return stringBuffer.toString();
	}

	public static String readFileContentAsBuffer(String filePath){
		File file = new File(filePath);

		BufferedReader reader = null;
		StringBuffer stringBuffer = new StringBuffer();
        int num;
		try {
			reader = new BufferedReader(new FileReader(file));
			char[] buf=new char[1024];
			while((num = reader.read(buf)) != -1){
				stringBuffer.append(new String(buf,0,num));
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return stringBuffer.toString();
	}

	public static void writeIntoFile(String content,String filePath){
		try {
			OutputStream os = new FileOutputStream(filePath);
			PrintWriter pw=new PrintWriter(os);
			pw.print(content); //不会自动换行，必要时可以自己添加分隔符
			pw.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
