package mongmi.client;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import javax.sound.sampled.*; 
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

class QuizPage extends JFrame{

	JButton passB;
	JLabel background;
	JLabel timerL,quizL,hintL;
	JLabel markO,markX;
	JPanel cp, pMom;
	JTextField answerTf;
	Font font = new Font("����ǹ��� �־�", Font.PLAIN, 40);
	int score;
	ActionListener listener;
	KeyListener keyListener;

	MainPage mp;
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	String id;
	Question qt;

	Clip clip;



	QuizPage(MainPage mp,Socket s){
		this.mp = mp;
		this.s = s;
		this.dis = mp.dis;
		this.dos = mp.dos;
		this.id = mp.id;
		this.clip = mp.clip;

		listener = new QuizActL(this);
		qt = new Question(this);
		keyListener = new QuizKeyL(this);
	}
	
	void playSound(String filename){ 
        File file = new File("bgm/" + filename);
        if(file.exists()){ 
            try{
                AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.start();
                if(filename.equals("bgm_quiz.wav")); //clip.loop(8);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{ 
            System.out.println("File Not Found!");
        }
    }

	void init(){
//���� �ǾƷ� �� �г�(�����̳�) ����//�����̳ʷ� ������ ���� ������ �����ȯ�� ����?
		cp = new JPanel();//ct
		cp.setBorder(null);
		setContentPane(cp);
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS)); //����->������ ������� ��ġ
		cp.setOpaque(true);//�����ϰ�
//ū�гε� ��Ƶ� �г� ����
		pMom = new JPanel();			
		pMom.setOpaque(true);		
		cp.add(pMom);
		pMom.setLayout(null);
//���ȭ�� ����
		background = new JLabel(new ImageIcon("img/background/Quiz.png"));
		background.setBounds(0, 0, 350, 550); //��ư ��ġ(0.0)�� ũ��(350,550)����
		background.setOpaque(true);//��������
		pMom.add(background);

//���ȭ�鿡 ä�� gif ��������� �� ����
		markO = new JLabel(new ImageIcon("img/mark/Opa.png"));
		markO.setBounds(100, 320, 150, 150); //��ư ��ġ(100.320)�� ũ��(150,150)����
		markO.setOpaque(false);//��������
		background.add(markO);

		markX = new JLabel(new ImageIcon("img/mark/Opa.png"));
		markX.setBounds(185, 120, 150, 150); //��ư ��ġ(185.120)�� ũ��(150,150)����
		markX.setOpaque(false);//��������
		background.add(markX);

//���ȭ�鿡 ��ư,�� ����
		timerL = new JLabel("",JLabel.CENTER);
		timerL.setFont(font);
		timerL.setBounds(10, 15, 150, 45); //��ư ��ġ(10.15)�� ũ��(150,45)����
		timerL.setOpaque(false);//��������
		background.add(timerL);
		TimerModule tm = new TimerModule(this);//�ð��� ����
		tm.start();//�ð��귯���� �޼ҵ� ȣ��

		quizL = new JLabel("",JLabel.CENTER);
		quizL.setFont(font);
		quizL.setBounds(45, 85, 260, 50); //��ư ��ġ(45.85)�� ũ��(260,50)����
		quizL.setOpaque(false);//��������
		background.add(quizL);

		hintL = new JLabel("",JLabel.CENTER);
		//Font font = new Font("����ǹ��� �־�", Font.PLAIN, 20);
		hintL.setFont(new Font("����ǹ��� �־�", Font.PLAIN, 20));
		hintL.setBounds(35,  480, 270, 50); //��ư ��ġ(35,480)�� ũ��(270,50)����
		hintL.setOpaque(false);//��������
		background.add(hintL);
		qt.init();
		qt.show();//Question class�� �ִ� ����,��Ʈ ����ϴ� �޼ҵ�
//ct�� �Է��� ��Field
		answerTf = new JTextField();		
		
		answerTf.setBorder(BorderFactory.createEmptyBorder());
		answerTf.setFont(new Font("����ǹ��� �־�", Font.PLAIN, 30));
		answerTf.setOpaque(false);
		answerTf.setBounds(40, 355, 280	,70);
		answerTf.setHorizontalAlignment(JTextField.CENTER);
		background.add(answerTf);
		/////////����ڰ� ���� �Է����� �� event!!!!/////////
		answerTf.addKeyListener(keyListener);

		passB = new JButton(new ImageIcon("img/button/PassB.png"));
		passB.setPressedIcon(new ImageIcon("img/button/PassBC.png"));
		
		passB.setBorderPainted(false);//�ܰ���
        passB.setFocusPainted(false);
        passB.setContentAreaFilled(false);//��ư ��� �����?
		passB.setBounds(170,-10,200,100);//��ư ��ġ(170,-10)�� ũ��(200,100) ����
		background.add(passB);
		/////////����ڰ� pass�� Ŭ������ �� event!!!!/////////
		passB.addActionListener(listener);

		setUI();
	}

	void setUI(){
		setTitle("���� �ʼ�����");
		setIconImage(new ImageIcon("img/icon/ICon.png").getImage());
		setSize(360, 585);
		setVisible(true);
		setLocation(600,150);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
}

class QuizActL implements ActionListener{
	QuizPage qp;

	QuizActL(QuizPage qp){
		this.qp = qp;
	}
	@Override
	public void actionPerformed(ActionEvent e){
		qp.playSound("eff_pass.wav");
		qp.qt.removeItem();//�н� ������ �� ������ ������ ����
		qp.qt.show();//���� ���� ������
	}
}
class QuizKeyL implements KeyListener{
	QuizPage qp;
	
	QuizKeyL(QuizPage qp){
		this.qp = qp;
	}

	@Override
	public void keyTyped(KeyEvent e) { 
	}
	@Override
	public void keyPressed(KeyEvent e) {   
	}
	@Override
	public void keyReleased(KeyEvent e) {//Ű�� ���ȴٰ� ����������

		if (e.getKeyCode()==KeyEvent.VK_ENTER) {//������ Ű���� ������ �ִ� �ڵ� ���� ��Ÿ����
			String answer = "";
			
			answer = qp.answerTf.getText();//����ڰ� �Է��� ���� answer�� ����
			qp.answerTf.setText("");//�Է� �� �Է�â ����
			answer = answer.trim();//����ڰ� �Է��� �� ���� ����
			//System.out.println(qp.qt.vAnswer.get(qp.qt.random));�� Ȯ��
			if(answer.equals(qp.qt.vAnswer.get(qp.qt.random))){
				ImageIcon correct = new ImageIcon("img/mark/Correct.gif");
				correct.getImage().flush();
				qp.markO.setIcon(correct);
				qp.playSound("eff_star.wav");
				qp.score++;//���߸� ���� ȹ��
				qp.qt.removeItem();
				qp.qt.show();
			}else{
				ImageIcon inCorrect = new ImageIcon("img/mark/InCorrect.gif");
				qp.playSound("eff_wrong.wav");
				inCorrect.getImage().flush();
				qp.markX.setIcon(inCorrect);
			}
			//System.out.println("���� : "+qp.score);//���߸� ����
		}
	}
}

class TimerModule extends Thread{//���� �ð��� ������ MyScore�������� �Ѿ

	QuizPage qp;

	int time = 10;
	int minute;
	int second;
	TimerModule(QuizPage qp){
		this.qp = qp;	
	}
	public void run(){

		while(time>0){
			minute = time/60;
			second = time%60;
			String m = Integer.toString(minute);
			String s = Integer.toString(second);
			qp.timerL.setText(m+" : "+s);
			time--;
			try{
				Thread.sleep(1000);//1�ʾ����� ����!
			}catch(Exception e){}
		}
		try{
			qp.dos.writeByte(qp.score);
			System.out.println(qp.score);
		}catch(IOException ie){}
		qp.clip.stop();
		qp.setVisible(false);
        qp.dispose();
        new MyScore(qp, qp.s).init();
	}
	
}