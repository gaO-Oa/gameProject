package Frames;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class showGame extends JFrame {

	ClassLoader classLoader = getClass().getClassLoader();
	List<Huddle> huddleList = new ArrayList<>();
	List<Huddle> starList = new ArrayList<>();
	Toolkit kit = Toolkit.getDefaultToolkit();
	Image imstadardFirst = kit.getImage(classLoader.getResource("oneFirst.gif"));
	Image imstadardSecond = kit.getImage(classLoader.getResource("oneSecond.gif"));
	Image imgBg = kit.getImage(classLoader.getResource("background.png"));
	Image imgScore = kit.getImage(classLoader.getResource("score.png"));
	Image pepper = kit.getImage(classLoader.getResource("pepper.png"));
	Image starImage = kit.getImage(classLoader.getResource("star.png"));

	int whereX;
	int whereY;
	private LogIn login;
	private JPanel contentPnl;
	private JLabel scoreImg;
	private JLabel characterImg;
	private Timer scoreTimer; // 점수 타이머
	int scoreResult = 0;
	int starScore = 0;
	private JLabel lblMoney;
	private JLabel lblScore;
	private Timer backTimer; // 배경 타이머

	private boolean characterUp = false;
	private int x = 0;
	static final int BLACK = -16777216;
	private Timer huddleTimer;
	private Timer starTimer;
	private Timer downTimer;

	public int getScoreResult() {
		return scoreResult;
	}

	public int getStarScore() {
		return starScore;
	}
	
	public int getWhereX() {
		return whereX;
	}

	public void setWhereX(int whereX) {
		this.whereX = whereX;
	}

	public int getWhereY() {
		return whereY;
	}

	public void setWhereY(int whereY) {
		this.whereY = whereY;
	}

	public void grapPix() throws IOException {

		BufferedImage img = ImageIO.read(showGame.class.getClassLoader().getResource("01.png"));

		for (int i = 0; i < img.getWidth(); i++) {
			int red = img.getRGB(i, img.getHeight() - 11);
			if (red == -1237980) {
				Huddle huddle = new Huddle(pepper);
				huddleList.add(huddle);
				huddle.setBounds(i * 10, 320, 50, 50);
				getContentPane().add(huddle);
			}
		}
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 17; j < 25; j++) {
				int black = img.getRGB(i, img.getHeight() - j);
				if (black == -16777216) {
					Huddle star = new Huddle(starImage);
					starList.add(star);
					star.setBounds(i * 10, 250, 50, 50);
					getContentPane().add(star);
				}
			}
		}

		huddleTimer = new Timer(80, new ActionListener() { // 이동속도 변경
			@Override
			public void actionPerformed(ActionEvent e) {
				Iterator<Huddle> iter = huddleList.iterator();
				while (iter.hasNext()) {
					Huddle j = iter.next();
					j.setLocation(j.getX() - 10, j.getY()); // x값-10하기
					for (int i = 40; i < 84; i++) {
						if (j.getX() == i && 280 == getWhereY()) {
							if (characterUp == false) {
								j.setVisible(false);
								iter.remove();
								stopTimers();
								GameOver game = new GameOver();
								game.setVisible(true);
							}
						}
					}
				}
			}
		});
		huddleTimer.start(); // 타이머시작

		starTimer = new Timer(80, new ActionListener() { // 이동속도 변경
			@Override
			public void actionPerformed(ActionEvent e) {
				Iterator<Huddle> iter = starList.iterator();
				while (iter.hasNext()) {
					Huddle j = iter.next();
					j.setLocation(j.getX() - 10, j.getY()); // x값-10하기
					if (j.getX() >= 50 && j.getX() <= 130) {
						if (j.getY() >= 180 && j.getY() <= 260) {
							if (characterUp == true) {
								j.setVisible(false);
								iter.remove();
								starScore += 10;
								lblMoney.setText(String.valueOf(starScore));
							}
						}
					}
				}
			}
		});
		starTimer.start(); // 타이머시작
	}

	private void stopTimers() {
		huddleTimer.stop();
		starTimer.stop();
		backTimer.stop();
		scoreTimer.stop();
	}

	public showGame(LogIn logIn) {
		this.login = login;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stopTimers();
				huddleList = null;
				starList = null;
				dispose();
			}
		});
		
		contentPnl = new JPanel();
		setContentPane(contentPnl);
		contentPnl.setBorder(null);
		contentPnl.setLayout(null);
		setBounds(100, 100, 701, 437);

		JLabel bgIng = new JLabel(""); // 배경
		bgIng.setIcon(new ImageIcon(imgBg));
		bgIng.setBounds(0, 0, 100000, 400);

		backTimer = new Timer(25, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				x -= 4;
				if (x <= -15700) {
					backTimer.stop();
					x = 0;
				} else {
					bgIng.setLocation(x, 0);
				}
			}
		});
		backTimer.start();

		characterImg = new JLabel(""); // 게임중인 캐릭터
		whereX = 50;
		whereY = 280;
		characterImg.setFocusable(true);
		characterImg.setIcon(new ImageIcon(imstadardFirst));
		characterImg.setBounds(whereX, whereY, 90, 90);
		characterImg.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				downTimer.start();
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					characterUp = true;
					characterImg.setIcon(new ImageIcon(imstadardSecond));
					whereY = 220;
					characterImg.setBounds(whereX, whereY, 90, 90);
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					characterUp = false;
					characterImg.setIcon(new ImageIcon(imstadardFirst));
					whereY = 280;
					characterImg.setBounds(whereX, whereY, 90, 90);
				}
			}
		});

		downTimer = new Timer(1200, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (whereY == 220) {
					setWhereX(50);
					setWhereY(280);
					characterUp = false;
					characterImg.setIcon(new ImageIcon(imstadardFirst));
					characterImg.setBounds(whereX, whereY, 90, 90);
					downTimer.stop();
				}
			}
		});

		try {
			grapPix();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		scoreImg = new JLabel();
		scoreImg.setIcon(new ImageIcon(imgScore));
		scoreImg.setBounds(20, 10, 500, 73);

		lblMoney = new JLabel(String.valueOf(scoreResult));
		lblMoney.setBounds(240, 12, 50, 73);
		lblMoney.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMoney.setFont(new Font("맑은 고딕", Font.BOLD, 22));

		lblScore = new JLabel(String.valueOf(scoreResult));
		lblScore.setBounds(100, 12, 50, 73);
		lblScore.setHorizontalAlignment(SwingConstants.RIGHT);
		lblScore.setFont(new Font("맑은 고딕", Font.BOLD, 22));

		scoreResult = 0;
		scoreTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scoreResult += 10;
				lblScore.setText(String.valueOf(scoreResult));
			}
		});
		scoreTimer.start();
		contentPnl.add(lblMoney);
		contentPnl.add(lblScore);
		contentPnl.add(scoreImg);
		contentPnl.add(characterImg);
		contentPnl.add(bgIng);

	}
}