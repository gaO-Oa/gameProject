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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import info.UserinfoRepositoryImpl;

public class LogIn extends JFrame {

	ClassLoader classLoader = getClass().getClassLoader();
	Toolkit kit = Toolkit.getDefaultToolkit();
	Image mainLogin = kit.getImage(classLoader.getResource("mainLogIn.png"));
 
	private String myId;
	private int myNo;
	private int myLastRound;
	private int myCharacter;
	private UserinfoRepositoryImpl ur;
	private JLabel contentPane;
	private JLabel[] infoTitle = new JLabel[2];
	private String[] infoTitles = { "아이디", "비밀번호" };
	private JTextField infoField = new JTextField(17);
	private JPasswordField infoFiePw = new JPasswordField(17);
	private JButton[] jbts = new JButton[3];
	private String[] jbtsTitles = { "회원가입", "로그인", "메뉴얼" };

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	public int getMyCharacter() {
		return myCharacter;
	}
	public void setMyCharacter(int myCharacter) {
		this.myCharacter = myCharacter;
	}
	public int getMyNo() {
		return myNo;
	}
	public int getMyLastRound() {
		return myLastRound;
	}

	public String getMyId() {
		return myId;
	}

	public LogIn() {
		ur = new UserinfoRepositoryImpl();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 405);
		contentPane = new JLabel();
		contentPane.setBorder(null);
		contentPane.setIcon(new ImageIcon(mainLogin));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		for (int i = 0; i < infoTitle.length; i++) {
			infoTitle[i] = new JLabel(infoTitles[i]);
			infoTitle[i].setFont(new Font("맑은 고딕", Font.BOLD, 16));
			infoTitle[i].setBounds(123, 115 + i * 34, 68, 24);
			contentPane.add(infoTitle[i]);
		}

		infoField.setBounds(248, 117, 116, 21);
		infoFiePw.setBounds(248, 117 + 34, 116, 21);
		contentPane.add(infoField);
		contentPane.add(infoFiePw);

		for (int i = 0; i < jbts.length; i++) {
			jbts[i] = new JButton(jbtsTitles[i]);
			jbts[i].setFont(new Font("맑은 고딕", Font.BOLD, 13));
			jbts[i].setBorder(null);
			jbts[i].setBounds(58 + i * 142, 210, 97, 35);
			jbts[i].setBackground(new Color(176, 232, 103));
			contentPane.add(jbts[i]);
		}

		jbts[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUp().showGUI();
			}
		});

		jbts[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String inputId = infoField.getText();
				String inputPw = infoFiePw.getText();

				int result = ur.login(inputId, inputPw);

				if (result == 1) {
					new Menu(LogIn.this).showGUI();
					myId = inputId;
					myNo = ur.getMyNo(myId);
					myLastRound = ur.findLastRound(myNo);
					System.out.println("로그인 성공");
					System.out.println(myId);
					System.out.println(myNo + "//" + myLastRound);


				} else {
					System.out.println("로그인 실패");
					JOptionPane.showMessageDialog(null, "아이디 및 비밀번호를 확인하세요", "로그인 실패!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		myCharacter = ur.getMyCharacter(myId);

		jbts[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manual mn = new manual();
				mn.setVisible(true);
			}

		});

	}

	public void showGUI() {
		setVisible(true);
	}
}
