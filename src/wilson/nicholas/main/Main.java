package wilson.nicholas.main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main{

	private JFrame mainFrame;
	
	public Main() {
		JFrame mainFrame = new JFrame("Tree Visualizer");
		Canvas c = new Canvas();
		mainFrame.add(c, BorderLayout.CENTER);
		//mainFrame.setContentPane(c);
		
		//mainFrame.pack();
		mainFrame.setSize(Canvas.STARTWIDTH, Canvas.STARTHEIGHT);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public int getHeight(){
		return mainFrame.getHeight();
	}
	
	public int getWidth(){
		return mainFrame.getWidth();
	}
	
	public JFrame getMainFrame(){
		return mainFrame;
	}
	
	public static void main(String [] args){
		new Main();
	}

}
