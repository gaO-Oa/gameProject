package Frames;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import exceptions.DataIOException;
import info.UserinfoRankingImpl;

public class Rank extends JFrame {
	
	ClassLoader classLoader = getClass().getClassLoader();
	Toolkit kit = Toolkit.getDefaultToolkit();
	Image b = kit.getImage(classLoader.getResource("rank.png"));
	Image c1 = kit.getImage(classLoader.getResource("c1.gif"));
	Image c2 = kit.getImage(classLoader.getResource("c2.gif"));
	Image c3 = kit.getImage(classLoader.getResource("c3.gif"));
	Image c4 = kit.getImage(classLoader.getResource("c4.gif"));
	Image c5 = kit.getImage(classLoader.getResource("c5.gif"));
	
	private Image[] cImage = {c1, c2, c3, c4, c5};
	
	final String[] imagetTitle = {"c1", "c2", "c3", "c4", "c5"};

	final String[] rankTitle = {"1 등", "2 등", "3 등", "4 등", "5 등"};
	
	private JLabel contentPane;
	private JLabel[] whatRank = new JLabel[rankTitle.length];
	private JLabel[] whoNic = new JLabel[5];
	private JLabel[] whoScore = new JLabel[5]; 
	private JLabel[] whoCharacter = new JLabel[5];
	private JLabel lbl_characterA;
	private int bestCha;
	
	public Rank(LogIn login) throws DataIOException {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Menu menu = new Menu(login);
				menu.showGUI();
				cImage = null;
			} 
		});
		
		setBounds(100, 100, 500, 600);
		contentPane = new JLabel();
		contentPane.setBorder(null);
		contentPane.setIcon(new ImageIcon(b));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(65, 54, 349, 90);
		contentPane.add(panel);
		panel.setLayout(null);
		
		// 캐릭터 -------------------------------------------------------
		
		UserinfoRankingImpl uRank = new UserinfoRankingImpl();
		bestCha = uRank.getBestcharacter(1);
		System.out.println(bestCha + "??????");
		
		lbl_characterA = new JLabel("");
		// lbl_characterA.setBackground();
		lbl_characterA.setOpaque(false);
		lbl_characterA.setIcon(new ImageIcon(cImage[bestCha - 1]));
		lbl_characterA.setBounds(20, 0, 90, 90);
		panel.add(lbl_characterA);
		
		for (int i = 1; i < whoCharacter.length; i++) {
			int orderByCha = uRank.getBestcharacter(i + 1) - 1;
			System.out.println(orderByCha);
			whoCharacter[i] = new JLabel("");
			whoCharacter[i].setIcon(new ImageIcon(cImage[orderByCha]));
			whoCharacter[i].setBounds(85, 155 + (i - 1) * 100 , 90, 90);
			contentPane.add(whoCharacter[i]);			
		}
		
		// 등수 -------------------------------------------------------
		
		JLabel lbl_rankA = new JLabel(rankTitle[0]);
		lbl_rankA.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lbl_rankA.setBounds(117, 10, 57, 90);
		panel.add(lbl_rankA);
		
		for (int i = 1; i < whatRank.length; i++) {
			whatRank[i] = new JLabel(rankTitle[i]);
			whatRank[i].setFont(new Font("맑은 고딕", Font.BOLD, 14));
			whatRank[i].setBounds(183, 155 + (i - 1) * 100 , 57, 90);
			contentPane.add(whatRank[i]);
		}
	
		// 닉네임 -------------------------------------------------------
		
		String bestNic = uRank.getBestNic(1);
		JLabel lbl_nicknameA = new JLabel(bestNic);
		lbl_nicknameA.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lbl_nicknameA.setBounds(186, 10, 57, 90);
		panel.add(lbl_nicknameA);
		
		for (int i = 1; i < whoNic.length; i++) {
			String orderByNic = uRank.getBestNic(i + 1);
			whoNic[i] = new JLabel(orderByNic);
			whoNic[i].setFont(new Font("맑은 고딕", Font.BOLD, 13));
			whoNic[i].setBounds(252, 155 + (i - 1) * 100 , 57, 90);
			contentPane.add(whoNic[i]);
			
		}
		
		// 점수 -------------------------------------------------------
		
		String bestScore = String.valueOf(uRank.getBestScore(1));
		
		JLabel lbl_scoreA = new JLabel(bestScore);
		lbl_scoreA.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lbl_scoreA.setBounds(255, 10, 57, 90);
		panel.add(lbl_scoreA);
		
		for (int i = 1; i < whoScore.length; i++) {
			
			String orderByS = String.valueOf(uRank.getBestScore(i + 1));
			
			whoScore[i] = new JLabel(orderByS);
			whoScore[i].setFont(new Font("맑은 고딕", Font.BOLD, 13));
			whoScore[i].setBounds(325, 155 + (i - 1) * 100 , 57, 90);
			contentPane.add(whoScore[i]);
		}
				
	}
	public void showGUI() throws DataIOException {
		setVisible(true);
	}

}
