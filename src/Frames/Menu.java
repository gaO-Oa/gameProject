package Frames;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import exceptions.DataIOException;
import info.UserinfoRepositoryImpl;

public class Menu extends JFrame {

	private JLabel contentPane;
	ClassLoader classLoader = getClass().getClassLoader();
	Toolkit kit = Toolkit.getDefaultToolkit();
	Image munuImage = kit.getImage(classLoader.getResource("menu.png"));
	
	public Menu(LogIn logIn) throws DataIOException {
		
		setBounds(100, 100, 500, 400);
		contentPane = new JLabel();
		contentPane.setBorder(null);
		contentPane.setIcon(new ImageIcon(munuImage));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//----------------------------------------------- 
		
		JButton btn_gameStart = new JButton("게임시작");
		btn_gameStart.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btn_gameStart.setBorder(new EmptyBorder(5, 5, 5, 5));
		btn_gameStart.setBackground(new Color(255, 255, 255));
		btn_gameStart.setBounds(100, 100, 100, 50);
		btn_gameStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) throws DataIOException {
				setVisible(false);
				new showGame(logIn).setVisible(true);				
			}
		});
		contentPane.add(btn_gameStart);
		
		UserinfoRepositoryImpl ur = new UserinfoRepositoryImpl();		
		
		
		JButton btn_Shop = new JButton("상점");
		btn_Shop.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btn_Shop.setBorder(new EmptyBorder(5, 5, 5, 5));
		btn_Shop.setBackground(new Color(255, 255, 255));
		btn_Shop.setBounds(290, 100, 100, 50);
		btn_Shop.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) throws DataIOException {
				setVisible(false);
				String inputId = logIn.getMyId();
				System.out.println(inputId);
				shop sh = new shop(logIn);
				ur.setMoney(logIn.getMyNo());
				String myMoney = String.valueOf(ur.countMoney(inputId));
				System.out.println(myMoney);
				sh.showMoney(myMoney);
				sh.showGUI();
				
			}
		});
		contentPane.add(btn_Shop);
		
		JButton btn_Rank = new JButton("순위");
		btn_Rank.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btn_Rank.setBorder(new EmptyBorder(5, 5, 5, 5));
		btn_Rank.setBackground(new Color(255, 255, 255));
		btn_Rank.setBounds(100, 200, 100, 50);
		btn_Rank.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) throws DataIOException {
				setVisible(false);
				new Rank(logIn).showGUI();
				
			}
		});
		contentPane.add(btn_Rank);
		
		JButton btn_MyRoom = new JButton("마이룸");
		btn_MyRoom.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btn_MyRoom.setBorder(new EmptyBorder(5, 5, 5, 5));
		btn_MyRoom.setBackground(new Color(255, 255, 255));
		btn_MyRoom.setBounds(290, 200, 100, 50);
		btn_MyRoom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) throws DataIOException {
				setVisible(false);			
				new MyRoom(logIn).showGUI();
			}
		});
		contentPane.add(btn_MyRoom);
	}
	
	public void showGUI() throws DataIOException {
		setVisible(true);
	}

}
