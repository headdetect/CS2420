package homework7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class TreeVisualizer
{

	public static void main(String[] args) throws IOException
	{
		JFrame frame = new JFrame("Title?");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		Node<String> nodes = TreeReader.readTreeFromFile("test.tree");
		
		TreeVisualizerPanel panel = new TreeVisualizerPanel(nodes);

		JScrollPane pane = new JScrollPane(panel);
		panel.setEnclosingPane(pane);

		pane.setPreferredSize(new Dimension(500, 500));
		frame.add(pane, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

	static int indentStatus = 0;

	static void writeElements(Node<String> node)
	{
		if (node.isRoot())
		{
			indentStatus = 0;
			System.out.println(node.getValue());
		}
		else
		{
			System.out.println(getIndents() + node.getValue());
		}

		for (int i = 0; i < node.getChildren().size(); i++)
		{
			indentStatus++;
			writeElements(node.getChildren().get(i));
		}
		indentStatus--;
	}

	private static String getIndents()
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < indentStatus; i++)
			builder.append('\t');
		return builder.toString();
	}

	static Node<String> generateRandomTree()
	{
		Node<String> root = new Node<String>("root");
		root.setRoot(true);
		for (int i = 1; i < 10; i++)
		{
			int len = (int) (Math.random() * 10 + 1);
			int skip = (int) (Math.random() * 3 + 1);
			Node<String> parent = new Node<String>("Layer 1: " + i);
			for (int j = 1; j < len; j += skip)
			{
				Node<String> child = new Node<String>("Layer 2: " + j);
				parent.getChildren().add(child);
				child.setParent(parent);

				boolean makeChildren = (int)(Math.random() * 10) % 2 == 1;
				if (makeChildren)
				{
					int plen = (int) (Math.random() * 10 + 1);
					int pskip = (int) (Math.random() * 3 + 1);
					for(int k = 0; k < plen; k += pskip)
					{
						Node<String> grandChild = new Node<String>("Layer 3: " + k);
						child.getChildren().add(grandChild);
						grandChild.setParent(child);
					}
				}
			}
			root.getChildren().add(parent);
			parent.setParent(root);
		}

		return root;
	}

}
