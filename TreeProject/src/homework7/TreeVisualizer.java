package homework7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TreeVisualizer
{

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Title?");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		TreeVisualizerPanel panel = new TreeVisualizerPanel();

		/* The file structure is that of a XML type. No point in making my own tree structure that
		 * takes an xml string and puts it into itself, when I can just use javax utils that contain a 
		 * tree-est structure.
		 */

		JScrollPane pane = new JScrollPane(panel);
		pane.setBackground(Color.YELLOW);
		panel.setEnclosingPane(pane);

		pane.setPreferredSize(new Dimension(500, 500));
		frame.add(pane, BorderLayout.CENTER);
		frame.add(pane);

		frame.pack();
		frame.setVisible(true);
	}
	
	
	static void WriteElements(Node node) {
		System.out.println("\nCurrent Element :" + node.getNodeName());
		
		for (int i = 0; i < node.getChildNodes().getLength(); i++)
		{
			WriteElements(node.getChildNodes().item(i));
		}
	}

}
