package mongmi.client;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import javax.sound.sampled.*; 
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

class MainPage extends JFrame {//

	JButton startB;//1.Start��ư
	JLabel background; //1.��� �г�
	JPanel cp, pMom;
	JTextField idTf;
	Font font = new Font("����ǹ��� �־�", Font.PLAIN, 40);
	
	String id;
	LoginPage lp;
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	Clip clip;
	
	MainPage(LoginPage lp, Socket s){
		this.lp = lp;
		this.s = s;
		try{
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		}catch(IOException ie){}

		playSound("bgm_quiz.wav");
	}
	
	void playSound(String filename){ 
        File file = new File("bgm/" + filename);
        if(file.exists()){ 
            try{
                AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(stream);
                clip.start();
				clip.loop(8);
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
		cp.setOpaque(true); //�����ϰ�
//ū�гε� ��Ƶ� �г� ����
		pMom = new JPanel();			
		pMom.setOpaque(true);		
		cp.add(pMom);
		pMom.setLayout(null);//�ۼַ�Ʈ
//���ȭ�� ����//������Ʈ
		background = new JLabel(new ImageIcon("img/background/Main.png"));
		background.setBounds(0, 0, 350, 550); //��ư ��ġ(0.0)�� ũ��(350,550)����
		background.setOpaque(true);//��������
		pMom.add(background);
//ū �гε� ��ġ
		idTf = new JTextField();		
		
		idTf.setBorder(BorderFactory.createEmptyBorder());//�ܰ���X
		idTf.setFont(font);
		idTf.setOpaque(false);
		idTf.setBounds(110, 367, 200,70);
		background.add(idTf);
		KeyListener keyListener = new MainKeyL(this);
		idTf.addKeyListener(keyListener);

		startB = new JButton(new ImageIcon("img/button/StartB.png"));
		startB.setPressedIcon(new ImageIcon("img/button/StartBC.png"));
		
		startB.setBorderPainted(false);//�ܰ���
        startB.setFocusPainted(false);
        startB.setContentAreaFilled(false);//��ư ��� �����?
		startB.setBounds(-5,440,360,100);//��ư ��ġ(-5,440)�� ũ��(360,100) ����
		background.add(startB);
		ActionListener listener = new MainActL(this);
		startB.addActionListener(listener);//���� ��ư ������ �׼�(QuizPage�� ��ȯ)

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

class MainActL implements ActionListener{
	MainPage mp;

	MainActL(MainPage mp){
		this.mp = mp;
	}
	@Override
	public void actionPerformed(ActionEvent e){
		mp.id = mp.idTf.getText();
		if(mp.id.length() < 6){
			if(mp.id.equals("")) mp.id = "GUEST";
			try{
				mp.dos.writeUTF(mp.id);
			}
			catch (IOException ie){
			}
			mp.idTf.setEnabled(false);//id�Է� �� ��Ȱ��ȭ
			mp.setVisible(false);
			mp.dispose();
			new QuizPage(mp, mp.s).init();
		}else{
			JOptionPane.showMessageDialog(null, "���̵� ���̴� �ִ� 5�ڱ��� �����ؿ�.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

class MainKeyL implements KeyListener{
	MainPage mp;

	MainKeyL(MainPage mp){
		this.mp = mp;
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
												//VK_ENTER = ��� , ���� Ű�� ���� Ű���� �ǹ��Ѵ�
			mp.id = mp.idTf.getText();
			if(mp.id.length() < 6){
				if(mp.id.equals("")) mp.id = "GUEST";
				try{
					mp.dos.writeUTF(mp.id);
				}catch (IOException ie){}
				mp.idTf.setEnabled(false);//id�Է� �� ��Ȱ��ȭ
				mp.setVisible(false);
				mp.dispose();
				new QuizPage(mp, mp.s).init();
			}else{
				JOptionPane.showMessageDialog(null, "���̵� ���̴� �ִ� 5�ڱ��� �����ؿ�.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}