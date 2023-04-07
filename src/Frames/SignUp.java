package Frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import info.CheckRegexImpl;
import info.UserinfoRepositoryImpl;

public class SignUp extends JFrame implements KeyListener {

	ClassLoader classLoader = getClass().getClassLoader();
	Toolkit kit = Toolkit.getDefaultToolkit();
	Image mainsignUp = kit.getImage(classLoader.getResource("signBack.png"));

	private JLabel contentPane;
	private String[] whatInfoTitle = { "아이디 (영소문자 및 숫자 8자 이하)", "비밀번호 (영소문자 및 숫자 16자 이하)", "비밀번호 재확인",
			"닉네임 (한글 및 영대소문자 5자 이하)" };
	private JLabel[] whatInfo = new JLabel[whatInfoTitle.length];
	private JLabel[] countResult = new JLabel[4];
	private JTextField[] text_info = new JTextField[2];
	private JPasswordField[] pw_info = new JPasswordField[2];
	private JButton[] countBtn = new JButton[2];


	public SignUp() {
		setBounds(100, 100, 500, 600);

		contentPane = new JLabel();
		contentPane.setBorder(null);
		contentPane.setIcon(new ImageIcon(mainsignUp));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		for (int i = 0; i < whatInfo.length; i++) {
			whatInfo[i] = new JLabel(whatInfoTitle[i]);
			whatInfo[i].setFont(new Font("맑은 고딕", Font.BOLD, 12));
			whatInfo[i].setBounds(117, 100 + i * 56, 300, 28);
			contentPane.add(whatInfo[i]);
		}

		for (int i = 0; i < 4; i++) {
			countResult[i] = new JLabel("");
			countResult[i].setFont(new Font("맑은 고딕", Font.BOLD, 12));
			countResult[i].setBounds(325, 105 + i * 57, 89, 23);
			contentPane.add(countResult[i]);
		}

		for (int i = 0; i < 2; i++) {
			text_info[i] = new JTextField(20);
			text_info[i].setBounds(117, 130 + i * 170, 180, 21);
			text_info[i].addKeyListener(this);
			contentPane.add(text_info[i]);
		}

		for (int i = 0; i < 2; i++) {
			pw_info[i] = new JPasswordField(20);
			pw_info[i].setBounds(117, 190 + i * 57, 180, 21);
			pw_info[i].addKeyListener(this);
			contentPane.add(pw_info[i]);
		}

		// -----------------------------------------------------

		UserinfoRepositoryImpl ur = new UserinfoRepositoryImpl();
		CheckRegexImpl cr = new CheckRegexImpl();

		for (int i = 0; i < 2; i++) {
			countBtn[i] = new JButton("확인");
			countBtn[i].setBackground(Color.WHITE);
			countBtn[i].setFont(new Font("맑은 고딕", Font.BOLD, 12));
			countBtn[i].setBorder(null);
			countBtn[i].setBounds(320, 130 + i * 171, 60, 20);
			contentPane.add(countBtn[i]);
		}

		countBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputId = text_info[0].getText();
				int a = ur.countByID(inputId);
				int b = cr.checkIdByRegex(inputId);

				if (a != 1 && b == 1) {
					countResult[0].setText("사용가능");
				} else {
					countResult[0].setText("사용불가");
				}
			}
		});
		countBtn[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputNickname = text_info[1].getText();
				int a = ur.countByNickName(inputNickname);
				int b = cr.checkNnByRegex(inputNickname);
				if (a != 1 && b == 1) {
					countResult[3].setText("사용가능");
				} else {
					countResult[3].setText("사용불가");
				}
			}
		});
		// -----------------------------------------------------

		JButton btn_signup = new JButton("회원가입");
		btn_signup.setBounds(128, 400, 97, 23);
		btn_signup.setBackground(Color.WHITE);
		btn_signup.setBorder(null);
		btn_signup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean signUpResult = false;

				String inputId = text_info[0].getText();
				String inputPassword = pw_info[1].getText();
				String inputNickname = text_info[1].getText();

				if (ur.countByID(inputId) == 0 && ur.countByNickName(inputNickname) == 0
						&& cr.checkIdByRegex(inputId) == 1 && cr.checkPwByRegex(inputPassword) == 1
						&& cr.checkNnByRegex(inputNickname) == 1) {
					signUpResult = true;
				}
				if (signUpResult) {
					int result = ur.insert(inputId, inputPassword, inputNickname);
					int lastNo = ur.lastNo();
					int standardCharacter = ur.standardCharacter(lastNo);
					int standardRound = ur.stadardRound(lastNo);
					new SuccessSignUp(SignUp.this).showGUI();
				} else {
					JOptionPane.showMessageDialog(null, "다시 시도하세요", "회원가입 실패!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		contentPane.add(btn_signup);

		JButton btn_back = new JButton("뒤로가기");
		btn_back.setBounds(278, 400, 97, 23);
		btn_back.setBackground(Color.WHITE);
		btn_back.setBorder(null);
		btn_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LogIn().showGUI();
				dispose();
			}
		});
		contentPane.add(btn_back);
	}

	public void showGUI() {
		setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < 2; i++) {
			if (e.getSource() == text_info[i]) {
				countResult[i + (i * 2)].setText("");
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void keyReleased(KeyEvent e) {
		CheckRegexImpl cr = new CheckRegexImpl();

		String pwfirst = pw_info[0].getText();
		String pwSecond = pw_info[1].getText();

		if (e.getSource() == pw_info[0] || e.getSource() == pw_info[1]) {

			System.out.println(pwfirst + "//" + pwSecond);

			int a = cr.checkPwByRegex(pwfirst);
			int b = cr.checkPwByRegex(pwSecond);
			System.out.println(a + "//" + b);

			if (a == 1 && b == 1) {
				if (pwfirst != null || pwSecond != null) {
					if (pwfirst.equals(pwSecond)) {
						countResult[2].setText("사용가능");
					} else {
						countResult[2].setText("사용불가");
					}
				} else {
					countResult[2].setText("");
				}
			} else {
				countResult[2].setText("");
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
