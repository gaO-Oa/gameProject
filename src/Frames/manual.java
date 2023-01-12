package Frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;

public class manual extends JFrame {  
	ClassLoader classLoader = getClass().getClassLoader();
	Toolkit kit = Toolkit.getDefaultToolkit();
	Image keyboard = kit.getImage(classLoader.getResource("keyboard.png"));
	Image pepper = kit.getImage(classLoader.getResource("pepper.png"));
	Image background = kit.getImage(classLoader.getResource("manual_background.jpg"));
	Image star = kit.getImage(classLoader.getResource("star.png"));
	private JPanel contentPane;

	public manual() {
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_Down = new JLabel(" : Down");
		lbl_Down.setFont(new Font("휴먼편지체", Font.BOLD, 15));
		lbl_Down.setBounds(126, 137, 65, 43);
		contentPane.add(lbl_Down);
		
		JLabel lbl_Star = new JLabel("을 획득하면 돈을 벌 수 있어요.");
		lbl_Star.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		lbl_Star.setBounds(98, 256, 224, 32);
		lbl_Star.setIcon(new ImageIcon(star));
		contentPane.add(lbl_Star);
		
		JLabel lbl_Tip = new JLabel("Tip!!");
		lbl_Tip.setForeground(Color.RED);
		lbl_Tip.setFont(new Font("휴먼편지체", Font.BOLD, 16));
		lbl_Tip.setBounds(48, 259, 38, 26);
		contentPane.add(lbl_Tip);
		
		JLabel lbl_Rule = new JLabel("Rule");
		lbl_Rule.setFont(new Font("휴먼편지체", Font.BOLD, 20));
		lbl_Rule.setBounds(12, 10, 49, 26);
		contentPane.add(lbl_Rule);
		
		JLabel lbl_keyPad = new JLabel("");
		lbl_keyPad.setBounds(55, 64, 136, 90);
		lbl_keyPad.setIcon(new ImageIcon(keyboard));
		
		contentPane.add(lbl_keyPad);
		
		JLabel lbl_Jump = new JLabel(" : Jump");
		lbl_Jump.setFont(new Font("휴먼편지체", Font.BOLD, 15));
		lbl_Jump.setBounds(164, 64, 65, 43);
		contentPane.add(lbl_Jump);
		
		JLabel lbl_bro = new JLabel("");
		lbl_bro.setBounds(346, 64, 55, 58);
		lbl_bro.setIcon(new ImageIcon(pepper));
		contentPane.add(lbl_bro);
		
		JLabel lbl_broName = new JLabel("방사능 브로콜리");
		lbl_broName.setFont(new Font("휴먼편지체", Font.BOLD, 15));
		lbl_broName.setBounds(326, 119, 98, 32);
		contentPane.add(lbl_broName);
		
		JLabel lbl_A1 = new JLabel("떡잎마을은 부리부리 마왕(키링)으로 부터 마을이 오염되었습니다.");
		lbl_A1.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		lbl_A1.setBounds(38, 179, 397, 26);
		contentPane.add(lbl_A1);
		
		JLabel lbl_A2 = new JLabel("생존법!");
		lbl_A2.setFont(new Font("휴먼편지체", Font.BOLD, 14));
		lbl_A2.setBounds(38, 204, 397, 26);
		contentPane.add(lbl_A2);
		
		JLabel lbl_A3 = new JLabel("- 다가오는 방사능 브로콜리를 Jump(↑), Down(↓)을 이용해 브로콜리 피하기!");
		lbl_A3.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		lbl_A3.setBounds(38, 230, 422, 26);
		contentPane.add(lbl_A3);
		
		JLabel lbl_A4 = new JLabel("떡잎마을 방범대 친구들이 방사능 브로콜리를 피해 무사히 집에 도착할 수 있도록");
		lbl_A4.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		lbl_A4.setBounds(38, 290, 422, 26);
		contentPane.add(lbl_A4);
		
		JLabel lbl_A5 = new JLabel("힘내주세요");
		lbl_A5.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		lbl_A5.setBounds(38, 314, 397, 26);
		contentPane.add(lbl_A5);
		
		JLabel lbl_background = new JLabel("");
		lbl_background.setBounds(0, 0, 489, 361);
		lbl_background.setIcon(new ImageIcon(background));
		contentPane.add(lbl_background);
	}

}
