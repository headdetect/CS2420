package homework7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;


public class TreeVisualizer
{

	public static void main(String[] args)
	{
		writeElements(generateRandomTree());
		
		
		JFrame frame = new JFrame("Title?");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		TreeVisualizerPanel panel = new TreeVisualizerPanel();

		JScrollPane pane = new JScrollPane(panel);
		pane.setBackground(Color.YELLOW);
		panel.setEnclosingPane(pane);

		pane.setPreferredSize(new Dimension(500, 500));
		frame.add(pane, BorderLayout.CENTER);
		frame.add(pane);

		frame.pack();
		frame.setVisible(true);
	}
	
	
	static void writeElements(Node<String> node) {
		System.out.println("\nCurrent Element :" + node.getValue());
		
		for (int i = 0; i < node.getChildren().size(); i++)
		{
			writeElements(node.getChildren().get(i));
		}
	}
	
	static Node<String> generateRandomTree() {
		Node<String> root = new Node<String>("root");
		for(int i = 0; i < 10; i++) {
			int len = (int)(Math.random() * 10);
			Node<String> parent = new Node<String>("" + i);
			for(int j = 0; j < len; i++) {
				Node<String> child = new Node<String>("" + j);
				parent.getChildren().add(child);
			}
			root.getChildren().add(parent);
		}
		
		return root;
	}

}
