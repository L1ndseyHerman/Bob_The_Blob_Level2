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
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;


public class Panel extends JPanel implements ActionListener, KeyListener, MouseInputListener
	{
		//	A Timer that gets called x times a second on the MyPanel
		Timer t = new Timer(5, this);
		
		//	Need two separate booleans for the up arrow key: jumping and falling.
		boolean jumpingUp = false, fallingDown = false, movingLeft = false, movingRight = false;
		
		//	The two ways of ending the game
		boolean beatLevel = false, gameOver = false;
		
		//	Adding the story before the level!
		boolean storyTime = true;
		
		//	A location in the middle-ish of the screen for storyTime.
		Bob bobTheBlob = new Bob(400, 210);
		
		//	Will be the gap in both the x- and y-direction between letters.
		double letterGap = 5;
		//	Moving this here now.
		double letterSize = 2;
		
		Block[] theBlocks = new Block[80];
		//	30 less than max
		WinBlock theWinBlock = new WinBlock(970, 470);
		
		//	Needs to be one greater than Bob+Block, so 71, not 70 even though 70 sounds right
		double jumpHeight = 71, currentHeight = 460;		
		
		
		VoidWraith[] theWraiths = new VoidWraith[7];
		
		//	For Story Mode
		int clickCounter = 0;
		
		public Panel(int width, int height, Color bgColor)
		{
			//	Every. MyPanel. Ever.
			setPreferredSize(new Dimension(width, height));
			setBackground(bgColor);
			//	IMPT!! PUT IN ALL PROGRAMS TO MAKE THE MOUSE STUFF WORK!!
			this.addMouseListener(this);
			//	ALSO THIS!!
			this.addMouseMotionListener(this);
			//	Starts the Timer
			t.start();
			//	The 3 things below are needed for keyboard input.
			addKeyListener(this);
			setFocusable(true);
			setFocusTraversalKeysEnabled(false);
			
			//	Bottom row.
			theBlocks[0] = new Block(250, 470);
			theBlocks[1] = new Block(280, 470);
			theBlocks[2] = new Block(310, 470);
			theBlocks[3] = new Block(340, 470);
			theBlocks[4] = new Block(370, 470);
			theBlocks[5] = new Block(400, 470);
			theBlocks[6] = new Block(430, 470);
			theBlocks[7] = new Block(460, 470);
			theBlocks[8] = new Block(490, 470);
			//	End of bottom row
			//	Next row up
			theBlocks[9] = new Block(280, 440);
			theBlocks[10] = new Block(310, 440);
			theBlocks[11] = new Block(340, 440);
			theBlocks[12] = new Block(370, 440);
			theBlocks[13] = new Block(400, 440);
			theBlocks[14] = new Block(430, 440);
			theBlocks[15] = new Block(460, 440);
			theBlocks[16] = new Block(490, 440);
			//	End of second row
			//	Starting third row
			theBlocks[17] = new Block(310, 410);
			theBlocks[18] = new Block(340, 410);
			theBlocks[19] = new Block(370, 410);
			theBlocks[20] = new Block(400, 410);
			theBlocks[21] = new Block(430, 410);
			theBlocks[22] = new Block(460, 410);
			theBlocks[23] = new Block(490, 410);
			//	End of third row 
			//	Starting fourth row
			theBlocks[24] = new Block(340, 380);
			theBlocks[25] = new Block(370, 380);
			theBlocks[26] = new Block(400, 380);
			theBlocks[27] = new Block(430, 380);
			theBlocks[28] = new Block(460, 380);
			theBlocks[29] = new Block(490, 380);
			//	End of fourth row
			//	Starting fifth row without steps
			theBlocks[30] = new Block(340, 350);
			theBlocks[31] = new Block(370, 350);
			theBlocks[32] = new Block(400, 350);
			theBlocks[33] = new Block(430, 350);
			theBlocks[34] = new Block(460, 350);
			theBlocks[35] = new Block(490, 350);
			//	End of fifth row
			//	Starting sixth row
			theBlocks[36] = new Block(340, 320);
			theBlocks[37] = new Block(370, 320);
			theBlocks[38] = new Block(400, 320);
			theBlocks[39] = new Block(430, 320);
			theBlocks[40] = new Block(460, 320);
			theBlocks[41] = new Block(490, 320);
			//	End of sixth row
			//	First block to jump to 
			theBlocks[42] = new Block(160, 380);
			//	Second block cluster to jump to
			theBlocks[43] = new Block(30, 380);
			theBlocks[44] = new Block(0, 380);
			theBlocks[45] = new Block(0, 350);
			theBlocks[46] = new Block(0, 320);
			//	Third block to jump to
			theBlocks[47] = new Block(160, 290);
			//	Starting seventh row
			theBlocks[48] = new Block(340, 290);
			theBlocks[49] = new Block(370, 290);
			theBlocks[50] = new Block(400, 290);
			theBlocks[51] = new Block(430, 290);
			theBlocks[52] = new Block(460, 290);
			theBlocks[53] = new Block(490, 290);
			//	End of seventh row
			//	Start of eighth row with steps
			theBlocks[54] = new Block(370, 260);
			theBlocks[55] = new Block(400, 260);
			theBlocks[56] = new Block(430, 260);
			theBlocks[57] = new Block(460, 260);
			theBlocks[58] = new Block(490, 260);
			//	End of eighth row
			//	Start of ninth row with steps
			theBlocks[59] = new Block(400, 230);
			theBlocks[60] = new Block(430, 230);
			theBlocks[61] = new Block(460, 230);
			theBlocks[62] = new Block(490, 230);
			//	End of ninth row
			//	Block to jump on 
			theBlocks[63] = new Block(290, 170);
			//	Another block
			theBlocks[64] = new Block(170, 140);
			//	More
			theBlocks[65] = new Block(290, 80);
			//	And all rows from here on out! Tenth row
			theBlocks[66] = new Block(430, 200);
			theBlocks[67] = new Block(460, 200);
			theBlocks[68] = new Block(490, 200);
			//	End of tenth row
			//	Eleventh row
			theBlocks[69] = new Block(430, 170);
			theBlocks[70] = new Block(460, 170);
			theBlocks[71] = new Block(490, 170);
			//	End of eleventh row
			//	Twelfth row
			theBlocks[72] = new Block(430, 140);
			theBlocks[73] = new Block(460, 140);
			theBlocks[74] = new Block(490, 140);
			//	End of twelfth row
			//	Thirteenth row
			theBlocks[75] = new Block(430, 110);
			theBlocks[76] = new Block(460, 110);
			theBlocks[77] = new Block(490, 110);
			//	End of thirteenth row
			//	Top row
			theBlocks[78] = new Block(460, 80);
			theBlocks[79] = new Block(490, 80);
			
			
			
			//	Void Wraiths
			theWraiths[0] = new VoidWraith(0, 340, 1.5, 0);
			theWraiths[1] = new VoidWraith(0, 280, 2, 0);
			theWraiths[2] = new VoidWraith(0, 200, 2.5, 0);
			theWraiths[3] = new VoidWraith(0, 130, 3, 0);
			theWraiths[4] = new VoidWraith(0, 40, 1.5, 0);
			theWraiths[5] = new VoidWraith(540, 0, 0, 1);
			theWraiths[6] = new VoidWraith(640, 0, 0, 2);
		}
		
		protected void paintComponent(Graphics arg0)
		{
			super.paintComponent(arg0);
			Graphics2D g = (Graphics2D)arg0;
			if (storyTime == true)
			{
				
				if (clickCounter == 0)
				{
					bobTheBlob.setEmotion("meh");
					bobTheBlob.draw(g);
					TextBox bobSpeechBubble = new TextBox (bobTheBlob.getRightSide()+bobTheBlob.getDiameter()/8, bobTheBlob.getTopSide()+7*bobTheBlob.getDiameter()/10, 310, 40);
					bobSpeechBubble.draw(g);
					//	Moving the Letter array sizes here so that they can change for each new speech bubble
					Letter lineOne[] = new Letter[23];
					
					
					//	There's got to be a better way to do this, but I can't think of it right now.
					lineOne[0] = new Letter('H', bobSpeechBubble.getLeftSide()+letterGap/2+1*letterGap/2+0*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[1] = new Letter('u', bobSpeechBubble.getLeftSide()+letterGap/2+2*letterGap/2+1*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[2] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+3*letterGap/2+2*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[3] = new Letter('.', bobSpeechBubble.getLeftSide()+letterGap/2+4*letterGap/2+3*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[4] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+5*letterGap/2+4*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[5] = new Letter('I', bobSpeechBubble.getLeftSide()+letterGap/2+6*letterGap/2+5*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[6] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+7*letterGap/2+6*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[7] = new Letter('t', bobSpeechBubble.getLeftSide()+letterGap/2+8*letterGap/2+7*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[8] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+9*letterGap/2+8*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[9] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+10*letterGap/2+9*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[10] = new Letter('u', bobSpeechBubble.getLeftSide()+letterGap/2+11*letterGap/2+10*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[11] = new Letter('g', bobSpeechBubble.getLeftSide()+letterGap/2+12*letterGap/2+11*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[12] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+13*letterGap/2+12*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[13] = new Letter('t', bobSpeechBubble.getLeftSide()+letterGap/2+14*letterGap/2+13*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[14] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+15*letterGap/2+14*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[15] = new Letter('f', bobSpeechBubble.getLeftSide()+letterGap/2+16*letterGap/2+15*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[16] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+17*letterGap/2+16*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[17] = new Letter('r', bobSpeechBubble.getLeftSide()+letterGap/2+18*letterGap/2+17*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[18] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+19*letterGap/2+18*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[19] = new Letter('s', bobSpeechBubble.getLeftSide()+letterGap/2+20*letterGap/2+19*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[20] = new Letter('u', bobSpeechBubble.getLeftSide()+letterGap/2+21*letterGap/2+20*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[21] = new Letter('r', bobSpeechBubble.getLeftSide()+letterGap/2+22*letterGap/2+21*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[22] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+23*letterGap/2+22*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					
					Letter lineTwo[] = new Letter[24];
					
					lineTwo[0] = new Letter('t', bobSpeechBubble.getLeftSide()+letterGap/2+1*letterGap/2+0*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[1] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+2*letterGap/2+1*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[2] = new Letter('a', bobSpeechBubble.getLeftSide()+letterGap/2+3*letterGap/2+2*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[3] = new Letter('t', bobSpeechBubble.getLeftSide()+letterGap/2+4*letterGap/2+3*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[4] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+5*letterGap/2+4*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[5] = new Letter('w', bobSpeechBubble.getLeftSide()+letterGap/2+6*letterGap/2+5*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[6] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+7*letterGap/2+6*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[7] = new Letter('u', bobSpeechBubble.getLeftSide()+letterGap/2+8*letterGap/2+7*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[8] = new Letter('l', bobSpeechBubble.getLeftSide()+letterGap/2+9*letterGap/2+8*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[9] = new Letter('d', bobSpeechBubble.getLeftSide()+letterGap/2+10*letterGap/2+9*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[10] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+11*letterGap/2+10*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[11] = new Letter('t', bobSpeechBubble.getLeftSide()+letterGap/2+12*letterGap/2+11*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[12] = new Letter('a', bobSpeechBubble.getLeftSide()+letterGap/2+13*letterGap/2+12*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[13] = new Letter('k', bobSpeechBubble.getLeftSide()+letterGap/2+14*letterGap/2+13*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[14] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+15*letterGap/2+14*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[15] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+16*letterGap/2+15*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[16] = new Letter('m', bobSpeechBubble.getLeftSide()+letterGap/2+17*letterGap/2+16*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[17] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+18*letterGap/2+17*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[18] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+19*letterGap/2+18*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[19] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+20*letterGap/2+19*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[20] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+21*letterGap/2+20*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[21] = new Letter('m', bobSpeechBubble.getLeftSide()+letterGap/2+22*letterGap/2+21*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[22] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+23*letterGap/2+22*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[23] = new Letter('.', bobSpeechBubble.getLeftSide()+letterGap/2+24*letterGap/2+23*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					
					Letter lineThree[] = new Letter[8];
					
					lineThree[0] = new Letter('O', bobSpeechBubble.getLeftSide()+letterGap/2+1*letterGap/2+0*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[1] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+2*letterGap/2+1*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[2] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+3*letterGap/2+2*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[3] = new Letter('w', bobSpeechBubble.getLeftSide()+letterGap/2+4*letterGap/2+3*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[4] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+5*letterGap/2+4*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[5] = new Letter('l', bobSpeechBubble.getLeftSide()+letterGap/2+6*letterGap/2+5*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[6] = new Letter('l', bobSpeechBubble.getLeftSide()+letterGap/2+7*letterGap/2+6*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[7] = new Letter('.', bobSpeechBubble.getLeftSide()+letterGap/2+8*letterGap/2+7*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					
					for (int index=0; index<lineOne.length; index++)
					{
						lineOne[index].draw(g);
					}

					for (int index=0; index<lineTwo.length; index++)
					{
						lineTwo[index].draw(g);
					}
					
					for (int index=0; index<lineThree.length; index++)
					{
						lineThree[index].draw(g);
					}
					
				}
				
				
				else if (clickCounter==1)
				{
					bobTheBlob.setEmotion("neutralHappy");
					bobTheBlob.draw(g);
					TextBox bobSpeechBubble = new TextBox (bobTheBlob.getRightSide()+bobTheBlob.getDiameter()/8, bobTheBlob.getTopSide()+7*bobTheBlob.getDiameter()/10, 320, 25);
					bobSpeechBubble.draw(g);
					
					Letter lineOne[] = new Letter[25];
					
					lineOne[0] = new Letter('T', bobSpeechBubble.getLeftSide()+letterGap/2+1*letterGap/2+0*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[1] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+2*letterGap/2+1*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[2] = new Letter('i', bobSpeechBubble.getLeftSide()+letterGap/2+3*letterGap/2+2*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[3] = new Letter('s', bobSpeechBubble.getLeftSide()+letterGap/2+4*letterGap/2+3*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[4] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+5*letterGap/2+4*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[5] = new Letter('l', bobSpeechBubble.getLeftSide()+letterGap/2+6*letterGap/2+5*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[6] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+7*letterGap/2+6*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[7] = new Letter('v', bobSpeechBubble.getLeftSide()+letterGap/2+8*letterGap/2+7*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[8] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+9*letterGap/2+8*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[9] = new Letter('l', bobSpeechBubble.getLeftSide()+letterGap/2+10*letterGap/2+9*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[10] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+11*letterGap/2+10*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[11] = new Letter('w', bobSpeechBubble.getLeftSide()+letterGap/2+12*letterGap/2+11*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[12] = new Letter('i', bobSpeechBubble.getLeftSide()+letterGap/2+13*letterGap/2+12*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[13] = new Letter('l', bobSpeechBubble.getLeftSide()+letterGap/2+14*letterGap/2+13*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[14] = new Letter('l', bobSpeechBubble.getLeftSide()+letterGap/2+15*letterGap/2+14*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[15] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+16*letterGap/2+15*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[16] = new Letter('f', bobSpeechBubble.getLeftSide()+letterGap/2+17*letterGap/2+16*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[17] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+18*letterGap/2+17*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[18] = new Letter('r', bobSpeechBubble.getLeftSide()+letterGap/2+19*letterGap/2+18*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[19] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+20*letterGap/2+19*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[20] = new Letter('s', bobSpeechBubble.getLeftSide()+letterGap/2+21*letterGap/2+20*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[21] = new Letter('u', bobSpeechBubble.getLeftSide()+letterGap/2+22*letterGap/2+21*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[22] = new Letter('r', bobSpeechBubble.getLeftSide()+letterGap/2+23*letterGap/2+22*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[23] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+24*letterGap/2+23*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[24] = new Letter('!', bobSpeechBubble.getLeftSide()+letterGap/2+25*letterGap/2+24*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					
					for (int index=0; index<lineOne.length; index++)
					{
						lineOne[index].draw(g);
					}
					
				}
				
				
				else if (clickCounter==2)
				{
					bobTheBlob.setEmotion("surprised");
					bobTheBlob.draw(g);
					TextBox bobSpeechBubble = new TextBox (bobTheBlob.getRightSide()+bobTheBlob.getDiameter()/8, bobTheBlob.getTopSide()+7*bobTheBlob.getDiameter()/10, 310, 40);
					bobSpeechBubble.draw(g);
					
					Letter lineOne[] = new Letter[24];
					
					lineOne[0] = new Letter('B', bobSpeechBubble.getLeftSide()+letterGap/2+1*letterGap/2+0*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[1] = new Letter('y', bobSpeechBubble.getLeftSide()+letterGap/2+2*letterGap/2+1*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[2] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+3*letterGap/2+2*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[3] = new Letter('t', bobSpeechBubble.getLeftSide()+letterGap/2+4*letterGap/2+3*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[4] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+5*letterGap/2+4*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[5] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+6*letterGap/2+5*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[6] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+7*letterGap/2+6*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[7] = new Letter('w', bobSpeechBubble.getLeftSide()+letterGap/2+8*letterGap/2+7*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[8] = new Letter('a', bobSpeechBubble.getLeftSide()+letterGap/2+9*letterGap/2+8*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[9] = new Letter('y', bobSpeechBubble.getLeftSide()+letterGap/2+10*letterGap/2+9*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[10] = new Letter(',', bobSpeechBubble.getLeftSide()+letterGap/2+11*letterGap/2+10*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[11] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+12*letterGap/2+11*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[12] = new Letter('V', bobSpeechBubble.getLeftSide()+letterGap/2+13*letterGap/2+12*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[13] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+14*letterGap/2+13*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[14] = new Letter('i', bobSpeechBubble.getLeftSide()+letterGap/2+15*letterGap/2+14*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[15] = new Letter('d', bobSpeechBubble.getLeftSide()+letterGap/2+16*letterGap/2+15*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[16] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+17*letterGap/2+16*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[17] = new Letter('W', bobSpeechBubble.getLeftSide()+letterGap/2+18*letterGap/2+17*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[18] = new Letter('r', bobSpeechBubble.getLeftSide()+letterGap/2+19*letterGap/2+18*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[19] = new Letter('a', bobSpeechBubble.getLeftSide()+letterGap/2+20*letterGap/2+19*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[20] = new Letter('i', bobSpeechBubble.getLeftSide()+letterGap/2+21*letterGap/2+20*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[21] = new Letter('t', bobSpeechBubble.getLeftSide()+letterGap/2+22*letterGap/2+21*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[22] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+23*letterGap/2+22*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[23] = new Letter('s', bobSpeechBubble.getLeftSide()+letterGap/2+24*letterGap/2+23*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					
					for (int index=0; index<lineOne.length; index++)
					{
						lineOne[index].draw(g);
					}
					
					Letter lineTwo[] = new Letter[24];
					
					lineTwo[0] = new Letter('a', bobSpeechBubble.getLeftSide()+letterGap/2+1*letterGap/2+0*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[1] = new Letter('r', bobSpeechBubble.getLeftSide()+letterGap/2+2*letterGap/2+1*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[2] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+3*letterGap/2+2*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[3] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+4*letterGap/2+3*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[4] = new Letter('a', bobSpeechBubble.getLeftSide()+letterGap/2+5*letterGap/2+4*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[5] = new Letter('l', bobSpeechBubble.getLeftSide()+letterGap/2+6*letterGap/2+5*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[6] = new Letter('s', bobSpeechBubble.getLeftSide()+letterGap/2+7*letterGap/2+6*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[7] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+8*letterGap/2+7*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[8] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+9*letterGap/2+8*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[9] = new Letter('g', bobSpeechBubble.getLeftSide()+letterGap/2+10*letterGap/2+9*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[10] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+11*letterGap/2+10*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[11] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+12*letterGap/2+11*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[12] = new Letter('s', bobSpeechBubble.getLeftSide()+letterGap/2+13*letterGap/2+12*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[13] = new Letter('t', bobSpeechBubble.getLeftSide()+letterGap/2+14*letterGap/2+13*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[14] = new Letter('s', bobSpeechBubble.getLeftSide()+letterGap/2+15*letterGap/2+14*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[15] = new Letter(',', bobSpeechBubble.getLeftSide()+letterGap/2+16*letterGap/2+15*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[16] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+17*letterGap/2+16*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[17] = new Letter('s', bobSpeechBubble.getLeftSide()+letterGap/2+18*letterGap/2+17*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[18] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+19*letterGap/2+18*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[19] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+20*letterGap/2+19*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[20] = new Letter('t', bobSpeechBubble.getLeftSide()+letterGap/2+21*letterGap/2+20*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[21] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+22*letterGap/2+21*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[22] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+23*letterGap/2+22*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					lineTwo[23] = new Letter('y', bobSpeechBubble.getLeftSide()+letterGap/2+24*letterGap/2+23*5*letterSize, bobSpeechBubble.getTopSide()+2*letterGap+5*letterSize, letterSize);
					
					for (int index=0; index<lineTwo.length; index++)
					{
						lineTwo[index].draw(g);
					}
					
					
					Letter lineThree[] = new Letter[22];
					
					lineThree[0] = new Letter('c', bobSpeechBubble.getLeftSide()+letterGap/2+1*letterGap/2+0*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[1] = new Letter('a', bobSpeechBubble.getLeftSide()+letterGap/2+2*letterGap/2+1*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[2] = new Letter('n', bobSpeechBubble.getLeftSide()+letterGap/2+3*letterGap/2+2*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[3] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+4*letterGap/2+3*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[4] = new Letter('g', bobSpeechBubble.getLeftSide()+letterGap/2+5*letterGap/2+4*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[5] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+6*letterGap/2+5*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[6] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+7*letterGap/2+6*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[7] = new Letter('t', bobSpeechBubble.getLeftSide()+letterGap/2+8*letterGap/2+7*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[8] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+9*letterGap/2+8*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[9] = new Letter('r', bobSpeechBubble.getLeftSide()+letterGap/2+10*letterGap/2+9*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[10] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+11*letterGap/2+10*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[11] = new Letter('u', bobSpeechBubble.getLeftSide()+letterGap/2+12*letterGap/2+11*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[12] = new Letter('g', bobSpeechBubble.getLeftSide()+letterGap/2+13*letterGap/2+12*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[13] = new Letter('h', bobSpeechBubble.getLeftSide()+letterGap/2+14*letterGap/2+13*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[14] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+15*letterGap/2+14*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[15] = new Letter('b', bobSpeechBubble.getLeftSide()+letterGap/2+16*letterGap/2+15*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[16] = new Letter('l', bobSpeechBubble.getLeftSide()+letterGap/2+17*letterGap/2+16*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[17] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+18*letterGap/2+17*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[18] = new Letter('c', bobSpeechBubble.getLeftSide()+letterGap/2+19*letterGap/2+18*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[19] = new Letter('k', bobSpeechBubble.getLeftSide()+letterGap/2+20*letterGap/2+19*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[20] = new Letter('s', bobSpeechBubble.getLeftSide()+letterGap/2+21*letterGap/2+20*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					lineThree[21] = new Letter('.', bobSpeechBubble.getLeftSide()+letterGap/2+22*letterGap/2+21*5*letterSize, bobSpeechBubble.getTopSide()+3*letterGap+2*5*letterSize, letterSize);
					
					for (int index=0; index<lineThree.length; index++)
					{
						lineThree[index].draw(g);
					}
					
					
				}
				
				
				else if (clickCounter==3)
				{
					bobTheBlob.setEmotion("neutralHappy");
					bobTheBlob.draw(g);
					TextBox bobSpeechBubble = new TextBox (bobTheBlob.getRightSide()+bobTheBlob.getDiameter()/8, bobTheBlob.getTopSide()+7*bobTheBlob.getDiameter()/10, 130, 25);
					bobSpeechBubble.draw(g);
					
					Letter lineOne[] = new Letter[10];
					
					lineOne[0] = new Letter('G', bobSpeechBubble.getLeftSide()+letterGap/2+1*letterGap/2+0*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[1] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+2*letterGap/2+1*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[2] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+3*letterGap/2+2*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[3] = new Letter('d', bobSpeechBubble.getLeftSide()+letterGap/2+4*letterGap/2+3*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[4] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+5*letterGap/2+4*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[5] = new Letter('l', bobSpeechBubble.getLeftSide()+letterGap/2+6*letterGap/2+5*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[6] = new Letter('u', bobSpeechBubble.getLeftSide()+letterGap/2+7*letterGap/2+6*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[7] = new Letter('c', bobSpeechBubble.getLeftSide()+letterGap/2+8*letterGap/2+7*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[8] = new Letter('k', bobSpeechBubble.getLeftSide()+letterGap/2+9*letterGap/2+8*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					lineOne[9] = new Letter('!', bobSpeechBubble.getLeftSide()+letterGap/2+10*letterGap/2+9*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
					
					for (int index=0; index<lineOne.length; index++)
					{
						lineOne[index].draw(g);
					}
					
				}

				
			}
			
			
			if (beatLevel == false && gameOver == false && storyTime == false)
			{
				bobTheBlob.setEmotion("neutralHappy");
				for(int index=0; index<theBlocks.length; index++)
				{
					theBlocks[index].draw(g);
				}
				theWinBlock.draw(g);
				bobTheBlob.draw(g);
				for (int index=0; index<theWraiths.length; index++)
				{
					theWraiths[index].draw(g);
				}
			}
			if (beatLevel == true)
			{
				bobTheBlob.setEmotion("animeEcstatic");
				bobTheBlob.draw(g);
				TextBox bobSpeechBubble = new TextBox (bobTheBlob.getRightSide()+bobTheBlob.getDiameter()/8, bobTheBlob.getTopSide()+7*bobTheBlob.getDiameter()/10, 110, 20);
				bobSpeechBubble.draw(g);
				
				Letter lineOne[] = new Letter[8];
				
				lineOne[0] = new Letter('Y', bobSpeechBubble.getLeftSide()+letterGap/2+1*letterGap/2+0*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[1] = new Letter('o', bobSpeechBubble.getLeftSide()+letterGap/2+2*letterGap/2+1*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[2] = new Letter('u', bobSpeechBubble.getLeftSide()+letterGap/2+3*letterGap/2+2*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[3] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+4*letterGap/2+3*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[4] = new Letter('W', bobSpeechBubble.getLeftSide()+letterGap/2+5*letterGap/2+4*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[5] = new Letter('i', bobSpeechBubble.getLeftSide()+letterGap/2+6*letterGap/2+5*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[6] = new Letter('n', bobSpeechBubble.getLeftSide()+letterGap/2+7*letterGap/2+6*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[7] = new Letter('!', bobSpeechBubble.getLeftSide()+letterGap/2+8*letterGap/2+7*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				
				
				for (int index=0; index<lineOne.length; index++)
				{
					lineOne[index].draw(g);
				}
				
			}
			if (gameOver == true)
			{
				bobTheBlob.setEmotion("surprised");
				bobTheBlob.draw(g);
				TextBox bobSpeechBubble = new TextBox (bobTheBlob.getRightSide()+bobTheBlob.getDiameter()/8, bobTheBlob.getTopSide()+7*bobTheBlob.getDiameter()/10, 130, 20);
				bobSpeechBubble.draw(g);
				
				Letter lineOne[] = new Letter[10];
				
				lineOne[0] = new Letter('G', bobSpeechBubble.getLeftSide()+letterGap/2+1*letterGap/2+0*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[1] = new Letter('a', bobSpeechBubble.getLeftSide()+letterGap/2+2*letterGap/2+1*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[2] = new Letter('m', bobSpeechBubble.getLeftSide()+letterGap/2+3*letterGap/2+2*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[3] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+4*letterGap/2+3*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[4] = new Letter(' ', bobSpeechBubble.getLeftSide()+letterGap/2+5*letterGap/2+4*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[5] = new Letter('O', bobSpeechBubble.getLeftSide()+letterGap/2+6*letterGap/2+5*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[6] = new Letter('v', bobSpeechBubble.getLeftSide()+letterGap/2+7*letterGap/2+6*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[7] = new Letter('e', bobSpeechBubble.getLeftSide()+letterGap/2+8*letterGap/2+7*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[8] = new Letter('r', bobSpeechBubble.getLeftSide()+letterGap/2+9*letterGap/2+8*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				lineOne[9] = new Letter('!', bobSpeechBubble.getLeftSide()+letterGap/2+10*letterGap/2+9*5*letterSize, bobSpeechBubble.getTopSide()+letterGap, letterSize);
				
				for (int index=0; index<lineOne.length; index++)
				{
					lineOne[index].draw(g);
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			//	The method that gets called whenever the Timer goes off. 
			
			
			if (storyTime == false && gameOver == false && beatLevel == false)
			{
				moveEnemies();
			
			
				checkEnemies();
			
			
				checkWinBlock();
			
			
				repaint();
			
			
				checkBlocksUp();
			

				checkBlocksLeft();
			
			
				checkBlocksRight();
			}
			
		}
		

		//	This method only moves the enemies. The actual checking of whether or not Bob 
		//	is touching the enemy happens in the checkEnemies().
		public void moveEnemies()
		{
			for (int index=0; index<theWraiths.length; index++)
			{
				theWraiths[index].move(theWraiths[index].getXSpeed(), theWraiths[index].getYSpeed());
				theWraiths[index].setX(theWraiths[index].getX() + theWraiths[index].getXSpeed());
				theWraiths[index].setY(theWraiths[index].getY() + theWraiths[index].getYSpeed());
				if (theWraiths[index].getRightSide() + theWraiths[index].getXSpeed() >= 1000 || theWraiths[index].getLeftSide() + theWraiths[index].getXSpeed() <= 0)
				{
					theWraiths[index].setXSpeed(theWraiths[index].getXSpeed() * -1);
				}
				if (theWraiths[index].getBottomSide() + theWraiths[index].getYSpeed() >= 500 || theWraiths[index].getTopSide() + theWraiths[index].getYSpeed() <= 0)
				{
					theWraiths[index].setYSpeed(theWraiths[index].getYSpeed() * -1);
				}
			}
		}
		
		
		@Override
		public void keyPressed(KeyEvent arg0) 
		{
			// TODO Auto-generated method stub
			int code = arg0.getKeyCode();
			if (code == KeyEvent.VK_UP)
			{
				if (fallingDown == false && jumpingUp == false)
				{
					jumpingUp = true;
					currentHeight = bobTheBlob.getY();
				}
			}
			if (code == KeyEvent.VK_LEFT)
			{
				movingLeft = true;
			}
			if (code == KeyEvent.VK_RIGHT)
			{
				movingRight = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) 
		{
			// TODO Auto-generated method stub
			int code = arg0.getKeyCode();
			if (code == KeyEvent.VK_LEFT)
			{
				movingLeft = false;
			}
			if (code == KeyEvent.VK_RIGHT)
			{
				movingRight = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}


		
		public void checkBlocksUp()
		{
			if (jumpingUp == true && bobTheBlob.getTopSide() > currentHeight-jumpHeight)
			{
				boolean blockInWay = false;
				for(int index=0; index<theBlocks.length; index++)
				{
					if (bobTheBlob.getRightSide() > theBlocks[index].getLeftSide() && bobTheBlob.getLeftSide() < theBlocks[index].getRightSide() && bobTheBlob.getTopSide() <= theBlocks[index].getBottomSide() && bobTheBlob.getTopSide() > theBlocks[index].getTopSide())
					{
						blockInWay = true;
					}
				}
				if (blockInWay == false)
				{
					bobTheBlob.setY(bobTheBlob.getY() - 1);
					bobTheBlob.move(0, -1);
					repaint();
				}
				if (blockInWay == true || bobTheBlob.getTopSide() == currentHeight-jumpHeight)
				{
					jumpingUp = false;
					fallingDown = true;
				}
			}
			if (fallingDown == true)
			{
				boolean blockInWay = false;
				for (int index=0; index<theBlocks.length; index++)
				{
					if (bobTheBlob.getBottomSide() == theBlocks[index].getTopSide() && bobTheBlob.getRightSide() > theBlocks[index].getLeftSide() && bobTheBlob.getLeftSide() < theBlocks[index].getRightSide())
					{
						blockInWay = true;
					}
				}
				if (blockInWay == false && bobTheBlob.getTopSide() < 460)
				{
					bobTheBlob.setY(bobTheBlob.getY() + 1);
					bobTheBlob.move(0, 1);
					repaint();
				}
				if (blockInWay == true || bobTheBlob.getTopSide() == 460)
				{
					fallingDown = false;
				}
			}
			//	BOB IS BIGGER THAN THE BLOCKS!! ALL OF BOB WON'T FIT ON ONE, JUST MAKE SURE THE MIDDLE (X + 1/2DIAMETER) IS BETW BLOCK X-VALS INSTEAD!!!!!!!!!!
			//	Actually, doing if Bob is at all touching the block, but his square is bigger than circle, looks to not even be on block but is.
		}
		public void checkBlocksRight()
		{
			//	1000 - 40 - 1 = 959
			if (bobTheBlob.getLeftSide() <= 959 && movingRight == true)
			{
				//	A variable to see if any blocks are in the way. 
				boolean blockInWay = false;
				for(int index=0; index<theBlocks.length; index++)
				{
					if (bobTheBlob.getRightSide() == theBlocks[index].getLeftSide() && bobTheBlob.getBottomSide() > theBlocks[index].getTopSide() && bobTheBlob.getTopSide() < theBlocks[index].getBottomSide())
					{
						blockInWay = true;
					}
				}

				if (blockInWay == false)
				{
					bobTheBlob.setX(bobTheBlob.getX() + 1);
					bobTheBlob.move(1, 0);
					for (int index=0; index<theBlocks.length; index++)
					{
						//	CHANGED FROM > TO >=
						if (bobTheBlob.getLeftSide() >= theBlocks[index].getRightSide() && bobTheBlob.getBottomSide() == theBlocks[index].getTopSide() && jumpingUp == false && fallingDown == false)
						{
							fallingDown = true;
						}
						repaint();
					}
				}
			}
		}
		public void checkBlocksLeft()
		{
			if (bobTheBlob.getLeftSide() >= 1 && movingLeft == true)
			{
				//	There is a separate blockInWay variable for each direction, should reset each method call.
				boolean blockInWay = false;
				for(int index=0; index<theBlocks.length; index++)
				{
					if (bobTheBlob.getLeftSide() == theBlocks[index].getRightSide() && bobTheBlob.getBottomSide() > theBlocks[index].getTopSide() && bobTheBlob.getTopSide() < theBlocks[index].getBottomSide())
					{
						blockInWay = true;
					}
				}

				if (blockInWay == false)
				{
					bobTheBlob.setX(bobTheBlob.getX() - 1);
					bobTheBlob.move(-1, 0);
					for (int index=0; index<theBlocks.length; index++)
					{
						//	CHANGED FROM < TO <= 
						if (bobTheBlob.getRightSide() <= theBlocks[index].getLeftSide() && bobTheBlob.getBottomSide() == theBlocks[index].getTopSide() && jumpingUp == false && fallingDown == false) 
						{
							fallingDown = true;
						}
						repaint();
					}
				}
			}
		}
		
		
		
		
		public void checkEnemies()
		{
			if (beatLevel == false)
			{
				for (int index=0; index<theWraiths.length; index++)
				{
					if (bobTheBlob.getRightSide() >= theWraiths[index].getLeftSide() && bobTheBlob.getLeftSide() <= theWraiths[index].getRightSide() && bobTheBlob.getTopSide() <= theWraiths[index].getBottomSide() && bobTheBlob.getBottomSide() >= theWraiths[index].getTopSide())
					{
						gameOver = true;
					}
				}
			}
		}
		
		
		
		public void checkWinBlock()
		{
			if (bobTheBlob.getRightSide() >= theWinBlock.getLeftSide() && bobTheBlob.getLeftSide() <= theWinBlock.getRightSide() && bobTheBlob.getTopSide() <= theWinBlock.getBottomSide() && bobTheBlob.getBottomSide() >= theWinBlock.getTopSide())
			{
				beatLevel = true;
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) 
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) 
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) 
		{
			clickCounter++;
			//	REALLY IMPT REPAINT()!!!!!!!!!!!!!
			repaint();
			
			if (storyTime == true && clickCounter==4)
			{
				bobTheBlob.setX(0);
				bobTheBlob.setY(460);
				//	Going from 400 to 0 in x-direction and 210 to 460 in y-direction
				//	500 - 40 == 460.
				bobTheBlob.move(-400, 250);
				storyTime = false;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) 
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}

			
	}



