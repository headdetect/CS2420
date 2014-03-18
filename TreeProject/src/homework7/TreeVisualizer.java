package homework7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

public class TreeVisualizer
{
	static JMenuBar menuBar;
	static JMenuItem fileItem, loadMenu, closeMenu;
	static JFrame frame;
	static TreeVisualizerPanel panel;
	static JScrollPane pane;

	public static void main(String[] args) throws IOException
	{
		menuBar = new JMenuBar();
		fileItem = new JMenu("File");
		loadMenu = new JMenuItem("Load Tree");
		loadMenu.addActionListener(mFileOpenActionListener);
		closeMenu = new JMenuItem("Close");
		closeMenu.addActionListener(mCloseActionListener);

		fileItem.add(loadMenu);
		fileItem.add(closeMenu);
		menuBar.add(fileItem);

		frame = new JFrame("test.tree");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setJMenuBar(menuBar);

		Node<String> nodes = TreeReader.readTreeFromFile("test.tree");

		panel = new TreeVisualizerPanel(nodes);

		pane = new JScrollPane(panel);
		panel.setEnclosingPane(pane);

		pane.setPreferredSize(new Dimension(500, 500));
		frame.add(pane, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

	private static final ActionListener mFileOpenActionListener = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(panel);

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();
				try
				{
					panel.setTree(TreeReader.readTreeFromFile(file));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

	};

	private static final ActionListener mCloseActionListener = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// Probably not the best way to close the program //
			System.exit(0);

		}

	};

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

				boolean makeChildren = (int) (Math.random() * 10) % 2 == 1;
				if (makeChildren)
				{
					int plen = (int) (Math.random() * 10 + 1);
					int pskip = (int) (Math.random() * 3 + 1);
					for (int k = 0; k < plen; k += pskip)
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
