package homework7;

import homework7.Data.Node;
import homework7.Data.TreeReader;
import homework7.UI.TreeVisualizerPanel;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 * The Class TreeVisualizer.
 */
public class TreeVisualizer
{

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	static JMenuBar menuBar;

	static JMenuItem fileItem, loadMenu, closeMenu, viewItem, resetViewMenu;

	static JFrame frame;

	static TreeVisualizerPanel panel;

	static JScrollPane pane;

	/** The File Open Action Listener. */
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
					Node<String> nodes = TreeReader.readTreeFromFile(file);
					if (nodes == null)
					{
						JOptionPane.showMessageDialog(panel, "Is not a valid tree file");
						return;
					}
					panel.setTree(nodes);
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(panel, "File could not be opened");
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(panel, "Is not a valid tree file");
				}
			}
		}

	};

	/** The Close Action Listener. */
	private static final ActionListener mCloseActionListener = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// Probably not the best way to close the program //
			System.exit(0);

		}

	};

	/** The Reset View Action Listener. */
	private static final ActionListener mResetViewActionListener = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (panel != null)
				panel.resetLayout();

		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * The main method. Acts like the program constructor.
	 * 
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException
	{
		menuBar = new JMenuBar();
		fileItem = new JMenu("File");
		loadMenu = new JMenuItem("Load Tree");
		loadMenu.addActionListener(mFileOpenActionListener);
		closeMenu = new JMenuItem("Close");
		closeMenu.addActionListener(mCloseActionListener);

		viewItem = new JMenu("View");
		resetViewMenu = new JMenuItem("Reset View");
		resetViewMenu.addActionListener(mResetViewActionListener);

		fileItem.add(loadMenu);
		fileItem.add(closeMenu);

		viewItem.add(resetViewMenu);

		menuBar.add(fileItem);
		menuBar.add(viewItem);

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

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
