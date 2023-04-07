package Frames;

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

import info.UserinfoRepositoryImpl;

public class showGame extends JFrame {
	
	ClassLoader classLoader = getClass().getClassLoader();
	List<Huddle> huddleList = new ArrayList<>();
	List<Huddle> starList = new ArrayList<>();
	Toolkit kit = Toolkit.getDefaultToolkit();
	
	Image c1 = kit.getImage(classLoader.getResource("c1.gif"));
	Image c2 = kit.getImage(classLoader.getResource("c2.gif"));
	Image c3 = kit.getImage(classLoader.getResource("c3.gif"));
	Image c4 = kit.getImage(classLoader.getResource("c4.gif"));
	Image c5 = kit.getImage(classLoader.getResource("c5.gif"));
	private Image[] cImage = {c1, c2, c3, c4, c5};
	

	Image imgBg = kit.getImage(classLoader.getResource("background.png"));
	Image imgScore = kit.getImage(classLoader.getResource("score.png"));
	Image pepper = kit.getImage(classLoader.getResource("pepper.png"));
	Image starImage = kit.getImage(classLoader.getResource("star.png"));

	int whereX;
	int whereY;
	private LogIn login;
	private UserinfoRepositoryImpl ur = new UserinfoRepositoryImpl();
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
	private int jump = 0;
	private int x = 0;
	static final int BLACK = -16777216;
	private Timer huddleTimer;
	private Timer starTimer;
	private Timer downTimer;
	private int myNo;
	private int findC;
	private int downspeed = -5;
	private Timer totheGround;

	public int getScoreResult() {
		return scoreResult;
	}

	public int getStarScore() {
		return starScore;
	}

	public void grapPix(LogIn logIn) throws IOException {
		this.login = logIn;
		
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
		huddleTimer = new Timer(24, new ActionListener() { // 장애물 이동
			@Override
			public void actionPerformed(ActionEvent e) {
				Iterator<Huddle> iter = huddleList.iterator();
				while (iter.hasNext()) {
					Huddle j = iter.next();
					j.setLocation(j.getX() - 6, j.getY()); // x값-10하기
					for (int i = 40; i < 84; i++) {
						if (j.getX() - 1 == i && characterImg.getY() > 255) {
							j.setVisible(false);
							iter.remove();
							stopTimers();
						}
					}
				}
			}
		});
		huddleTimer.start(); // 타이머시작

		starTimer = new Timer(24, new ActionListener() { // 초코비 이동
			@Override
			public void actionPerformed(ActionEvent e) {
				Iterator<Huddle> iter = starList.iterator();
				while (iter.hasNext()) {
					Huddle j = iter.next();
					j.setLocation(j.getX() - 6, j.getY()); // x값-10하기
					if (j.getX() >= 50 && j.getX() <= 120) {
						if (j.getY() >= 180 && (j.getY() <= 260 && j.getY() >= 240)) {
							if (characterImg.getY() < 250) {
								j.setVisible(false);
								iter.remove();
								starScore += 50;
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
		GameOver game = new GameOver(login, showGame.this);
		game.setVisible(true);
		ur.saveScore(myNo, ur.findLastRound(myNo), scoreResult, starScore, login.getMyCharacter());
	}

	public showGame(LogIn logIn) {
		this.login = logIn;
		myNo = ur.getMyNo(login.getMyId());
		login.setMyCharacter(ur.getMyCharacter(login.getMyId()));
		
		//게임을 종료하기위한 메소드.
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stopTimers();
				huddleList = null;
				starList = null;
				Menu menu = new Menu(login);
				menu.showGUI();
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

		backTimer = new Timer(17, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				x -= 4;
				if (x <= -15700) {
					backTimer.start();
					huddleTimer.setRepeats(true);
					starTimer.setRepeats(true);
					x = 0;
				} else {
					bgIng.setLocation(x, 0);
				}
			}
		});
		backTimer.start();
		
		downspeed = 0;
		
		totheGround = new Timer(18, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (characterImg.getY() <= 280) {
					int y = characterImg.getY() + downspeed;
					System.out.println(downspeed);
					if (y > 280) {
						y = 280;
						downspeed = 0;
						jump = 0;
					} else {
						downspeed = downspeed + 1;
					}
					characterImg.setBounds(whereX, y, 90, 90);
				}		
			}
		});

		findC = ur.findCharacte(logIn.getMyId()) - 1;
		System.out.println("내캐릭터" + findC);
		characterImg = new JLabel(""); // 게임중인 캐릭터
		whereX = 50;
		whereY = 280;
		characterImg.setFocusable(true);
		characterImg.setIcon(new ImageIcon(cImage[findC]));
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
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					characterUp = true;
					if (jump < 2) {
						jump++;
						downspeed = -2;
						characterImg.setIcon(new ImageIcon(cImage[findC]));
						whereY = characterImg.getY() - 90;
						characterImg.setBounds(whereX, whereY, 90, 90);
						totheGround.start();
					}
				}
			}
		});
		
		try {
			grapPix(logIn);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		

		scoreImg = new JLabel();
		scoreImg.setIcon(new ImageIcon(imgScore));
		scoreImg.setBounds(20, 10, 500, 73);

		lblMoney = new JLabel(String.valueOf(scoreResult));
		lblMoney.setBounds(240, 12, 50, 73);
		lblMoney.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMoney.setFont(new Font("맑은 고딕", Font.BOLD, 18));

		lblScore = new JLabel(String.valueOf(scoreResult));
		lblScore.setBounds(100, 12, 50, 73);
		lblScore.setHorizontalAlignment(SwingConstants.RIGHT);
		lblScore.setFont(new Font("맑은 고딕", Font.BOLD, 18));

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