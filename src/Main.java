import javax.swing.JOptionPane;

import Frames.LogIn;
import exceptions.DataIOException;

public class Main {
	public static void main(String[] args) {		
		
		try {
			LogIn start = new LogIn();	
			start.showGUI();
		} catch (DataIOException cause) { //sql 예외 객체 참조를 가지고 있는 DataIOException 예외임. -> 포장된 DataIOException 를 해체하여 cause 를 알아내는것. 
			Throwable t = cause.getCause();
			JOptionPane.showMessageDialog(null, "시스템 장애가 발생했습니다.", "로그인 실패!", JOptionPane.WARNING_MESSAGE);
			System.out.println(t.getMessage());
			System.out.println("시스템 장애가 발생했습니다. 나중에 다시 시도해주세요.");
		}
	}
}
  