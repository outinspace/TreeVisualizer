package wilson.nicholas.main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Canvas extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public static final int STARTWIDTH = 800;
	public static final int STARTHEIGHT = 1000;
	
	private int branchLengthCutoff = 10;
	private int maxBranchLength = 150;
	private double maxAngle = 30;//Degrees
	private int branchSpread = 5;
	private double growthDegration = 0.5;
	
	private CopyOnWriteArrayList<Shape> shapes = new CopyOnWriteArrayList<Shape>();
	
	private Thread t;
	
	public Canvas() {
		
		JButton startBtn = new JButton("Start");
		startBtn.addActionListener(e -> startTreeGeneration());
		this.add(startBtn);
	}
	
	void startTreeGeneration(){
		
		if(t != null && t.isAlive()){
			return;
		}
		
		t = new Thread(() -> {
			while(true){
				shapes.clear();
				
				branchLengthCutoff = (int) (Math.random()*10 + 7);//10-20
				maxBranchLength = (int) (Math.random()*100 + 100);//120-200
				maxAngle = Math.random()*15 + 30;//30-45
				branchSpread = (int) (Math.random()*3 + 3);//4-6
				growthDegration = Math.random()*0.3 + 0.4;//0.4-0.7
				
				drawRecursiveTree(branchLengthCutoff, maxBranchLength, branchSpread, growthDegration, this.getWidth()/2, this.getHeight()-10, 180, maxAngle);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t.start();
	}
	
	void drawRecursiveTree(int branchLengthCutoff, int length, int branchingSpread, double branchDegration, int x, int y, double startingTheta, double maxAngle){
		if(length > branchLengthCutoff){
			//Transform angle to coordinates
			int calcXTransform = (int) (length * Math.sin(Math.toRadians(startingTheta)));
			int calcYTransform = (int) (length * Math.cos(Math.toRadians(startingTheta)));
			System.out.println("Length: " + length + " XTransform: " + calcXTransform + " YTransform: " + calcYTransform);
			
			//Draw branch
			//g.drawLine(x, y, x + calcXTransform, y + calcYTransform);
			shapes.add(new Line2D.Double(x, y, x + calcXTransform, y + calcYTransform));
			
			//Update canvas
			this.repaint();
			
			//Generate random branches
			int branches = (int) (Math.random() * branchingSpread);
			for(int i = 0; i < branches; i++){
				double branchAngle = (Math.random() * maxAngle * 2) - maxAngle + startingTheta;
				int branchLength = (int) (length - (length*Math.random()*branchDegration + 1));
				
				drawRecursiveTree(branchLengthCutoff, branchLength, branchingSpread, branchDegration, x + calcXTransform, y + calcYTransform, branchAngle, maxAngle);
			}
			
			//Continue Trunk
			int branchLength = (int) (length - (length*Math.random()*branchDegration + 1));
			drawRecursiveTree(branchLengthCutoff, branchLength, branchingSpread, branchDegration, x + calcXTransform, y + calcYTransform, startingTheta, maxAngle);
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		
		g2d.draw(new Rectangle2D.Double(5, 5, this.getWidth()-10, this.getHeight()-10));
		g2d.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
		g2d.drawString("Branch Cutoff: " + branchLengthCutoff, 10, 40);
		g2d.drawString("Starting Length: " + maxBranchLength, 10, 60);
		g2d.drawString("Max Angle: " + maxAngle, 10, 80);
		g2d.drawString("Spread: " + branchSpread, 10, 100);
		g2d.drawString("Growth Deterioration: " + growthDegration, 10, 120);
		
		for(Shape s : shapes){
			g2d.draw(s);
		}
	}
}
