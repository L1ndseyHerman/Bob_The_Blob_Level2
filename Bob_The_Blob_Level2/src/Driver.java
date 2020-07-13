import java.awt.Color;

import javax.swing.JFrame;

//	Estimated time LEVEL TWO: 6.5 hrs
//	Help with arrow key input by macheads101 (on YouTube)

	//	Just test and clean up!

public class Driver 
{

	public static void main(String[] args) 
	{
		//	Changing from 1350 to 1000, because the min laptop screen width is 1024px, but that's a weird number, so 1000 it is!
		//	Making a color variable
		Panel panel = new Panel(1000, 500, new Color(200, 200, 255));
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

}
