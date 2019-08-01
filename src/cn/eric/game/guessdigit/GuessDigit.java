package cn.eric.game.guessdigit;

import javax.swing.JOptionPane;

public class GuessDigit {

	
	public static void main(String[] args) {
		int result = (int)(Math.random() * 10); 
		int input = 0;
		
		while(true){
			
			input = Integer.parseInt(JOptionPane.showInputDialog("insert the number"));
			if(input>result){
				System.out.println("大了");
			}else if (input<result) {
				System.out.println("小了");
			}else{
				break;
			}
		}
		System.out.println("成功");
	}
}
