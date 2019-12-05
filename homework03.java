package homework3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

abstract class MyShape
{
	 Color c;
	 Color l;
	 int X1;
	 int X2;
	 int Y1;
	 int Y2;
	 int isGroup = 0;
	 int modeID;
	 int line = 3;
	 MyShape()
	 {
		 c = new Color(0,0,255);
		 l = new Color(0, 0, 0);
	 }
	 abstract void setPoint1(int x, int y);
	 abstract void setPoint2(int x, int y);
	 abstract void draw(Graphics g);
	 abstract void setColor(Color selC);
}

class MyLine extends MyShape
{
	//int Lx1; X1
	//int Ly1; Y1
	//int Lx2; X2
	//int Ly2; Y2
	
	@Override
	void setPoint1(int x, int y) {X1 = x; Y1 = y;}
	@Override
	void setPoint2(int x, int y) {X2 = x; Y2 = y;}
	@Override
	void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		
		if(line != 3)
		{
			g2.setColor(l);
			g2.setStroke(new BasicStroke(line));
			g2.drawLine(X1, Y1, X2, Y2);
		}
		g2.setColor(c);
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(X1, Y1, X2, Y2);
	}
	@Override
	void setColor(Color selC) {c = selC;}
}

class MyRect extends MyShape
{
	//int Rx; X1
	//int Ry; Y1
	//int Rw; X2
	//int Rh; Y2
	
	@Override
	void setPoint1(int x, int y) {X1 = x; Y1 = y;}
	@Override
	void setPoint2(int x, int y) { X2 = x - X1; Y2 = y - Y1; }
	@Override
	void draw(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		
		if(X2 > 0 && Y2 >0)
		{
			g2.setColor(c);
			g2.fillRect(X1, Y1, X2, Y2);
			g2.setColor(l);
			g2.setStroke(new BasicStroke(line));
			g2.drawRect(X1, Y1, X2, Y2);
		}
		else if(X2 <= 0 && Y2 >0)
		{
			g2.setColor(c);
			g2.fillRect(X1 + X2, Y1, -1*X2, Y2);
			g2.setColor(l);
			g2.setStroke(new BasicStroke(line));
			g2.drawRect(X1 + X2, Y1, -1*X2, Y2);
		}
		else if(X2 > 0 && Y2 <= 0)
		{
			g2.setColor(c);
			g2.fillRect(X1, Y1 + Y2, X2, -1*Y2);
			g2.setColor(l);
			g2.setStroke(new BasicStroke(line));
			g2.drawRect(X1, Y1 + Y2, X2, -1*Y2);
		}
		else
		{
			g2.setColor(c);
			g2.fillRect(X1 + X2, Y1 + Y2, -1*X2, -1*Y2);
			g2.setColor(l);
			g2.setStroke(new BasicStroke(line));
			g2.drawRect(X1 + X2, Y1 + Y2, -1*X2, -1*Y2); 
		}
	}
	@Override
	void setColor(Color selC) {c = selC;}
}

class MyCircle extends MyShape
{
	//int x1; X1
	//int y1; Y1
   	//int x2; X2
	//int y2; Y2
	
	int Cr;
	
	@Override
	void setPoint1(int x, int y) {X1 = x; Y1 = y;}
	@Override
	void setPoint2(int x, int y) {X2 = x; Y2 = y;}
	@Override
	void draw(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		
		Cr = (int)Math.sqrt((X2-X1)*(X2-X1) + (Y2-Y1)*(Y2-Y1));
		
		g2.setColor(c);
		g2.fillOval(X1-Cr, Y1-Cr, Cr*2, Cr*2);
		g2.setColor(l);
		g2.setStroke(new BasicStroke(line));
		g2.drawOval(X1-Cr, Y1-Cr, Cr*2, Cr*2);
	}
	@Override
	void setColor(Color selC) {c = selC;}
}

class MyCurve extends MyShape
{
	//int x1; X1
	//int y1; Y1
	//int x2; X2
	//int y2; Y2
	
	@Override
	void setPoint1(int x, int y) {X1 = x; Y1 = y;}
	@Override
	void setPoint2(int x, int y) {X2 = x; Y2 = y;}
	@Override
	void draw(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(c);
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(X1, Y1, X2, Y2);
	}
	@Override
	void setColor(Color selC) {c = selC;}
}

class ColorChooser extends JFrame implements ChangeListener {

	protected JColorChooser color;
	
	public ColorChooser()
	{
		color = new JColorChooser();
		
		color.getSelectionModel().addChangeListener(this);
		
		JPanel p = new JPanel();
		p.setBounds(30, 20, 100, 50);
		p.add(color);
		add(p);
		pack();
		
		this.setVisible(true);
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		Color newColor = color.getColor();	
	}
}

class MyDrawingPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener
{
	JToggleButton b1;
	JToggleButton b2;
	JToggleButton b3;
	JToggleButton b4;
	JToggleButton b5;
	JToggleButton b6;
	
	int CurG = 0;
	
	LinkedList <MyShape> shapes = new LinkedList<MyShape>();
	int numShapes = 0;
	final static int LINE = 0;
	final static int RECT = 1;
	final static int CIRCLE = 2;
	final static int CURVE = 3;
	final static int SELECT = 4;
	int mode = LINE;
	Color selC = Color.BLUE;
	
	int selX, selY;
	int selID = -1;
	int selGroup;
	
	public MyDrawingPanel()
	{
		requestFocus();
		
		b1 = new JToggleButton("£¯", true);
		b1.setPreferredSize(new Dimension(50,50));
		b1.addActionListener(this);
		
		b2 = new JToggleButton("¡à");
		b2.setPreferredSize(new Dimension(50,50));
		b2.addActionListener(this);
	
		b3 = new JToggleButton("¡Û");
		b3.setPreferredSize(new Dimension(50,50));
		b3.addActionListener(this);
		
		b4 = new JToggleButton("~");
		b4.setPreferredSize(new Dimension(50,50));
		b4.addActionListener(this);
		
		b5 = new JToggleButton();
		b5.setBackground(selC);
		b5.setPreferredSize(new Dimension(50,50));
		b5.addActionListener(this);
		
		b6 = new JToggleButton("¡è");
		b6.setPreferredSize(new Dimension(50,50));
		b6.addActionListener(this);
		
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(b5);
		add(b6);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(mode == SELECT)
		{
			for(int i=0; i<numShapes; i++)
			{
				shapes.get(i).draw(g);
			}
		}
		else
		{
			for(int i=0; i<numShapes; i++)
			{
				shapes.get(i).line = 3;
				shapes.get(i).l = new Color(0, 0, 0);
				if(shapes.get(i).X2 == 0 && shapes.get(i).Y2 == 0) continue;
				shapes.get(i).draw(g);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == b1)
		{
			mode = LINE;
			
			b2.setSelected(false);
			b3.setSelected(false);
			b4.setSelected(false);
			b5.setSelected(false);
			b6.setSelected(false);
		}
		else if(e.getSource() == b2)
		{
			mode = RECT;
			
			b1.setSelected(false);
			b3.setSelected(false);
			b4.setSelected(false);
			b5.setSelected(false);
			b6.setSelected(false);
		}
		else if(e.getSource() == b3)
		{
			mode = CIRCLE;
			
			b1.setSelected(false);
			b2.setSelected(false);
			b4.setSelected(false);
			b5.setSelected(false);
			b6.setSelected(false);
		}
		else if(e.getSource() == b4)
		{
			mode = CURVE;
			
			b1.setSelected(false);
			b2.setSelected(false);
			b3.setSelected(false);
			b5.setSelected(false);
			b6.setSelected(false);
		}
		else if(e.getSource() == b5)
		{
			b5.setSelected(false);
		
			selC = JColorChooser.showDialog(null, "", Color.BLUE);
			b5.setBackground(selC);
			repaint();
			
		}
		else if(e.getSource() == b6)
		{
			mode = SELECT;
			
			b1.setSelected(false);
			b2.setSelected(false);
			b3.setSelected(false);
			b4.setSelected(false);
			b5.setSelected(false);
		}
	}

	void findShapeID()
	{
		if(selID != -1)
		{
			shapes.get(selID).l = new Color(0, 0, 0);
			shapes.get(selID).line = 2;
		}
		for(int i = numShapes - 1; i >= 0; i--)
		{
			if (shapes.get(i).modeID == LINE)//³ªÁß¿¡
			{
				int r = (int)Math.sqrt((shapes.get(i).X2-shapes.get(i).X1)*(shapes.get(i).X2-shapes.get(i).X1)
						+ (shapes.get(i).Y2-shapes.get(i).Y1)*(shapes.get(i).Y2-shapes.get(i).Y1));
				int selr = (int)Math.sqrt((selX - shapes.get(i).X1)*(selX - shapes.get(i).X1)
						+ (selY - shapes.get(i).Y1)*(selY - shapes.get(i).Y1));
				
				int d = (shapes.get(i).Y2 - shapes.get(i).Y1)/(shapes.get(i).X2 - shapes.get(i).X1);
				int seld = (selY - shapes.get(i).Y1)/(selX - shapes.get(i).X1);
				if(d == seld && (selr < r))
				{
					selID = i;
					break;
				}
			}
			else if (shapes.get(i).modeID == RECT)
			{
				int maxX, minX;
				int maxY, minY;
				
				if(shapes.get(i).X2 + shapes.get(i).X1 > shapes.get(i).X1)	
				{ minX = shapes.get(i).X1; maxX = shapes.get(i).X1 + shapes.get(i).X2; }
				else														
				{ maxX = shapes.get(i).X1; minX = shapes.get(i).X1 + shapes.get(i).X2; }
				if(shapes.get(i).Y2 + shapes.get(i).Y1 > shapes.get(i).Y1)	
				{ minY = shapes.get(i).Y1; maxY = shapes.get(i).Y1 + shapes.get(i).Y2; }
				else														
				{ maxY = shapes.get(i).Y1; minY = shapes.get(i).Y1 + shapes.get(i).Y2; }
				
				if((selX>minX && selX<maxX) && (selY>minY && selY<maxY))
				{
					selID = i;
					break;
				}
			}
			else if (shapes.get(i).modeID == CIRCLE)
			{
				int r = (int)Math.sqrt((shapes.get(i).X2-shapes.get(i).X1)*(shapes.get(i).X2-shapes.get(i).X1) + (shapes.get(i).Y2-shapes.get(i).Y1)*(shapes.get(i).Y2-shapes.get(i).Y1));
				int selr = (int)Math.sqrt((selX - shapes.get(i).X1)*(selX - shapes.get(i).X1) + (selY - shapes.get(i).Y1)*(selY - shapes.get(i).Y1));
			
				if(selr < r)
				{
					selID = i;
					break;
				}
			}
			else if (shapes.get(i).modeID == CURVE)
			{
				if((selX == shapes.get(i).X1) && (selY == shapes.get(i).Y1))
				{
					selID = i;
					selGroup = shapes.get(i).isGroup;
					break;
				}
			}
		}
		
	}
	
	void DeleteCurve(int D_id)
	{
		int n = shapes.get(D_id).isGroup;
		
		if(D_id < 0) 
		{
			return;
		}
		
		while(shapes.get(D_id).isGroup == n)
		{
//			System.out.println("99999"+D_id);
			shapes.remove(D_id);
			if(D_id >= 0)
			{
				D_id--;
				numShapes --;
			}
//			System.out.println("10101010"+D_id);
			if(D_id < 0)
			{
				break;
			}
		}
		 if(D_id>=0)
	      {
	         if(shapes.get(D_id).isGroup == n)
	         {
	            shapes.remove(D_id);
	            numShapes --;
	            if(numShapes < 0) numShapes = 0;
	         }
	      }

		repaint();
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		switch(mode)
		{
		case LINE:
			requestFocus();
			shapes.add(numShapes, new MyLine());
			shapes.get(numShapes).setColor(selC);
			shapes.get(numShapes).setPoint1(e.getX(), e.getY());
			shapes.get(numShapes).modeID = LINE;
			numShapes++;
			break;
			
		case RECT:
			requestFocus();
			shapes.add(numShapes, new MyRect());
			shapes.get(numShapes).setColor(selC);
			shapes.get(numShapes).setPoint1(e.getX(), e.getY());
			shapes.get(numShapes).modeID = RECT;
			numShapes++;
			break;
			
		case CIRCLE:
			requestFocus();
			shapes.add(numShapes, new MyCircle());
			shapes.get(numShapes).setColor(selC);
			shapes.get(numShapes).setPoint1(e.getX(), e.getY());
			shapes.get(numShapes).modeID = CIRCLE;
			numShapes++;
			break;
			
		case CURVE:
			requestFocus();
			shapes.add(numShapes, new MyCurve());
			shapes.get(numShapes).setColor(selC);
			shapes.get(numShapes).setPoint1(e.getX(), e.getY());
			shapes.get(numShapes).isGroup = CurG;
			shapes.get(numShapes).modeID = CURVE;
			numShapes++;
			break;
			
		case SELECT:
			requestFocus();
			selX = e.getX();
			selY = e.getY();

			findShapeID();
			
			if(selID == -1) break;
			
			shapes.get(selID).l = new Color(255, 0, 0);
			shapes.get(selID).line = 5;
			break;
			
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(mode == SELECT)
		{
			if(selID == -1) return;
			
			if(shapes.get(selID).modeID == RECT)
			{
				int dragX = selX - e.getX();
				int dragY = selY - e.getY();
	
				shapes.get(selID).X1 = shapes.get(selID).X1 - dragX;
				shapes.get(selID).Y1 = shapes.get(selID).Y1 - dragY;	
			}
			else if(shapes.get(selID).modeID == CIRCLE)
			{
				int dragX = selX - e.getX();
				int dragY = selY - e.getY();
	
				shapes.get(selID).X1 = shapes.get(selID).X1 - dragX;
				shapes.get(selID).Y1 = shapes.get(selID).Y1 - dragY;	
				shapes.get(selID).X2 = shapes.get(selID).X2 - dragX;
				shapes.get(selID).Y2 = shapes.get(selID).Y2 - dragY;
			}
			else if(shapes.get(selID).modeID == LINE)
			{
				int dragX = selX - e.getX();
				int dragY = selY - e.getY();
	
				shapes.get(selID).X1 = shapes.get(selID).X1 - dragX;
				shapes.get(selID).Y1 = shapes.get(selID).Y1 - dragY;	
				shapes.get(selID).X2 = shapes.get(selID).X2 - dragX;
				shapes.get(selID).Y2 = shapes.get(selID).Y2 - dragY;
			}
			else if(shapes.get(selID).modeID == CURVE)
			{
				while(shapes.get(selID).isGroup == selGroup)
				{
					int dragX = selX - e.getX();
					int dragY = selY - e.getY();
	
					shapes.get(selID).X1 = shapes.get(selID).X1 - dragX;
					shapes.get(selID).Y1 = shapes.get(selID).Y1 - dragY;	
					shapes.get(selID).X2 = shapes.get(selID).X2 - dragX;
					shapes.get(selID).Y2 = shapes.get(selID).Y2 - dragY;
					selID--;
				}
			}
		}
		else
		{
			shapes.get(numShapes - 1).setPoint2(e.getX(), e.getY());
			if(mode == CURVE)
			{
				CurG++;
			}
		}
		repaint();
	}
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		if(mode == SELECT)
		{
			if(selID == -1) return;
			
			if(shapes.get(selID).modeID == RECT)
			{
				int dragX = selX - e.getX();
				int dragY = selY - e.getY();
			
				shapes.get(selID).X1 = shapes.get(selID).X1 - dragX;
				shapes.get(selID).Y1 = shapes.get(selID).Y1 - dragY;
					
				selX = e.getX();
				selY = e.getY();
			}
			else if(shapes.get(selID).modeID == CIRCLE)
			{
				int dragX = selX - e.getX();
				int dragY = selY - e.getY();
	
				shapes.get(selID).X1 = shapes.get(selID).X1 - dragX;
				shapes.get(selID).Y1 = shapes.get(selID).Y1 - dragY;	
				shapes.get(selID).X2 = shapes.get(selID).X2 - dragX;
				shapes.get(selID).Y2 = shapes.get(selID).Y2 - dragY;
			
				selX = e.getX();
				selY = e.getY();		
			}
			else if(shapes.get(selID).modeID == LINE)
			{
				int dragX = selX - e.getX();
				int dragY = selY - e.getY();
	
				shapes.get(selID).X1 = shapes.get(selID).X1 - dragX;
				shapes.get(selID).Y1 = shapes.get(selID).Y1 - dragY;	
				shapes.get(selID).X2 = shapes.get(selID).X2 - dragX;
				shapes.get(selID).Y2 = shapes.get(selID).Y2 - dragY;
			
				selX = e.getX();
				selY = e.getY();					
			}
			else if(shapes.get(selID).modeID == CURVE)
			{
				while(shapes.get(selID).isGroup == selGroup)
				{
					int dragX = selX - e.getX();
					int dragY = selY - e.getY();
		
					shapes.get(selID).X1 = shapes.get(selID).X1 - dragX;
					shapes.get(selID).Y1 = shapes.get(selID).Y1 - dragY;	
					shapes.get(selID).X2 = shapes.get(selID).X2 - dragX;
					shapes.get(selID).Y2 = shapes.get(selID).Y2 - dragY;
					
					selID--;		
					
					selX = e.getX();
					selY = e.getY();					
				}			
			}
		}
		else
		{
			shapes.get(numShapes - 1).setPoint2(e.getX(), e.getY());
			if(mode == CURVE)
			{
				shapes.add(numShapes, new MyCurve());
				shapes.get(numShapes).setColor(selC);
			
				shapes.get(numShapes).setPoint1(e.getX(), e.getY());
				shapes.get(numShapes).modeID = CURVE;
				shapes.get(numShapes).isGroup = CurG;
				numShapes++;
			}
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_DELETE)
		{
			if(mode == SELECT)
			{
				
				if(shapes.get(selID).modeID == CURVE)
				{				
						DeleteCurve(selID);
						if(selID <= 0)
						{
							shapes.remove(0);
						}
				}
				else
				{
					shapes.remove(selID);
					numShapes--;
				}
			}
		}
		repaint();
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			//System.out.println("¤²»ª");
			if(numShapes < 1)
			{
				//System.out.println("1111"+numShapes);
				numShapes = 0;
				repaint();
				return;
			}
			
			if(shapes.get(numShapes - 1).modeID == CURVE)
			{
				//System.out.println("22222"+numShapes);
				if(numShapes <= 1)
				{
			//		System.out.println("3333"+numShapes);
					shapes.remove(0);
					numShapes = 0;
				}
				else
				{
	//				System.out.println("4444"+numShapes);
					DeleteCurve(numShapes - 1);
				}
			}
			else
			{
		//		System.out.println("55555"+numShapes);
				shapes.remove(numShapes - 1);
				if(numShapes > 0)	
					numShapes --;
			}
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

public class homework03 extends JFrame
{

	homework03()
	{
		setSize(800, 600);
		setTitle("homework3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		MyDrawingPanel panel = new MyDrawingPanel();
		panel.setSize(800, 600);
		panel.setBackground(Color.WHITE);
		add(panel);
		
		setVisible(true);		
		panel.setFocusable(true);
	}	

	public static void main(String[] args) 
	{
		new homework03();
	}
}
