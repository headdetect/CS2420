package homework10;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

/**
 * This is a set of tools you may use to help you debug your HuffmanCompressor class. Each of the static methods below are simply used for loading, storing, converting, and
 * displaying arrays of bytes. Note that these methods will NOT be available when your HuffmanCompressor is graded. This means that these methods should only be used for testing,
 * and they should not be called from any code that you hand in.
 * 
 * @author Peter Jensen - CS 2420
 * @version Spring 2014
 */
public class HuffmanTools
{

	/**
	 * Given any array of bytes, this method will print the binary value of the bytes to the terminal. This is useful for seeing the bit patterns of bytes.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * Typical output: 01000110 10011010 etc.
	 * 
	 * @param data
	 *            An array of bytes
	 * @param start
	 *            The index of the first byte to display
	 * @param quantity
	 *            The number of bytes to display
	 */
	static public void dumpBytesAsBits(byte[] data, int start, int quantity)
	{
		for (int i = 0; i < quantity && i + start < data.length; i++)
		{
			for (int bit = 7; bit >= 0; bit--)
				System.out.print(((data[i + start] >> bit) & 1));

			System.out.print(" ");
		}
	}

	/**
	 * Given any array of bytes, this method will print the decimal value of the bytes to the terminal. This is useful for seeing the numeric values of bytes. The range of values
	 * is 0...255 inclusive.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * Typical output: 35 120 221 etc.
	 * 
	 * @param data
	 *            An array of bytes
	 * @param start
	 *            The index of the first byte to display
	 * @param quantity
	 *            The number of bytes to display
	 */
	static public void dumpBytesAsValues(byte[] data, int start, int quantity)
	{
		for (int i = 0; i < quantity && i + start < data.length; i++)
		{
			System.out.print(((data[i + start] + 256) % 256));
			System.out.print(" ");
		}
	}

	/**
	 * Given any array of bytes, this method will group four bytes together, convert the four bytes to an integer, and then print out this integer value. This is useful for seeing
	 * the values of bytes when they are translated to integers.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * Typical output: 371873 46 -17293 etc.
	 * 
	 * @param data
	 *            An array of bytes
	 * @param start
	 *            The index of the first byte to display
	 * @param quantity
	 *            The number of integers to display
	 */
	static public int dumpBytesAsIntegers(byte[] data, int start, int quantity)
	{
		int value = 0;
		for (int i = 0; i < quantity && i * 4 + 3 + start < data.length; i++)
		{
			int pos = start + i * 4;
			value = (((data[pos] + 256) % 256) << 24) | (((data[pos + 1] + 256) % 256) << 16) | (((data[pos + 2] + 256) % 256) << 8) | (((data[pos + 3] + 256) % 256));
			System.out.print(value);
			System.out.print(" ");
		}
		return value;
	}

	/**
	 * Given any array of bytes, this method will print the ASCII characters that correspond to the bytes to the terminal. This is useful for seeing the character values stored in
	 * bytes.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * Note that if the value of the byte is less than 32 or greater than 127, then there is no displayable character that can be printed. Instead, this method will print out
	 * 'char=#' with # replaced with a value to indicate the character value.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * Typical output: 'A' 'B' 'C' 'char=10' 'D' etc.
	 * 
	 * @param data
	 *            An array of bytes
	 * @param start
	 *            The index of the first byte to display
	 * @param quantity
	 *            The number of bytes to display
	 */
	static public void dumpBytesAsCharacters(byte[] data, int start, int quantity)
	{
		int value = 0;
		for (int i = 0; i < quantity && i + start < data.length; i++)
		{
			value = (data[i + start] + 256) % 256;
			if (value >= 32 && value <= 127)
				System.out.print("'" + (char) value + "'");
			else
				System.out.print("'char=" + value + "'");
			System.out.print(" ");
		}
	}

	/**
	 * Given any array of bytes that contains huffman coded data, this method will attempt to print out a summary about the compressed data. If your compressed data is in a
	 * different format than this method expects, unpredictable results will be output.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * Typical output:
	 * <p>
	 * &nbsp;
	 * <p>
	 * Unencoded data length: 5 <br>
	 * Number of tokens: 4 <br>
	 * Token: 'H' Frequency: 1 <br>
	 * Token: 'e' Frequency: 1 <br>
	 * Token: 'l' Frequency: 2 <br>
	 * Token: 'o' Frequency: 1 <br>
	 * First ten bytes of encoded data: 11010001 11000000
	 * <p>
	 * 
	 * @param data
	 *            An array containing huffman compressed bytes
	 */
	static public void dumpHuffmanCodedData(byte[] data)
	{
		System.out.print("Unencoded data length: ");
		dumpBytesAsIntegers(data, 0, 1);
		System.out.println();
		System.out.print("Number of tokens: ");
		int count = dumpBytesAsIntegers(data, 4, 1);
		System.out.println();

		for (int i = 0; i < count; i++)
		{
			System.out.print(" Token: ");
			dumpBytesAsCharacters(data, 8 + i * 5, 1);
			System.out.print(" Frequency: ");
			dumpBytesAsIntegers(data, 8 + i * 5 + 1, 1);
			System.out.println();
		}

		System.out.print("First ten bytes of encoded data: ");
		dumpBytesAsBits(data, 8 + count * 5, 10);
		System.out.println();
	}

	/**
	 * Given any ArrayList of HuffmanToken objects, this method will print out a summary of the tokens along with their Huffman codes.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * If this method won't compile for you, just delete it. (It requires a HuffmanToken class, and you might not have one.)
	 * 
	 * Typical output:
	 * <p>
	 * &nbsp;
	 * <p>
	 * Huffman token codes: <br>
	 * Token: 'H' Code: 110 <br>
	 * Token: 'e' Code: 10 <br>
	 * Token: 'l' Code: 0 <br>
	 * Token: 'o' Code: 111
	 * <p>
	 * 
	 * @param tokens
	 *            An ArrayList of HuffmanToken objects
	 */
	static public void dumpHuffmanCodes(ArrayList<HuffmanToken> tokens)
	{
		byte[] b = { 0 };
		System.out.println("Huffman token codes: ");

		for (HuffmanToken token : tokens)
		{
			b[0] = token.getValue();
			System.out.print(" Token: ");
			dumpBytesAsCharacters(b, 0, 1);
			System.out.print(" Code: ");
			for (boolean bit : token.getCode())
			{
				if (bit)
					System.out.print("1");
				else
					System.out.print("0");
			}
			System.out.println();
		}
	}

	/**
	 * This method converts a String object into an array of bytes. This is convenient for testing your code, you can type in a String as a parameter, and you'll get an array of
	 * bytes back. You can then use this array of bytes as input to the compress method to test your data compression code.
	 * 
	 * @param s
	 *            Any String
	 * @return A byte array that contains the byte representation of the String.
	 */
	static public byte[] createBytesFromString(String s)
	{
		return s.getBytes();
	}

	/**
	 * This method attempts to convert an array of bytes to a String.
	 * 
	 * @param data
	 *            An array of bytes
	 * @return The String that these bytes represent
	 */
	static public String createStringFromBytes(byte[] data)
	{
		return new String(data);
	}

	/**
	 * This method displays a dialog box, allows the user to select a file, reads the bytes from the file, and returns an array containing all the bytes in the file.
	 * 
	 * @return The bytes contained in a user-selected file
	 */
	static public byte[] readBytesFromFile()
	{
		System.out.println("The open file dialog window may be behind your other windows.");
		try
		{
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				FileInputStream input = new FileInputStream(chooser.getSelectedFile());
				byte[] data = new byte[input.available()];
				input.read(data);
				input.close();
				return data;
			}
			return null;
		}
		catch (IOException e)
		{
			System.out.println("Unable to read bytes: " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method displays a dialog box, allows the user to select a save file, and writes data bytes to the file. This will replace the contents of the selected file -- use
	 * cautiously.
	 * 
	 * @param data
	 *            The bytes to be written to a user-selected file
	 */
	static public void writeBytesToFile(byte[] data)
	{
		System.out.println("The save file dialog window may be behind your other windows.");
		try
		{
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				FileOutputStream output = new FileOutputStream(chooser.getSelectedFile());
				output.write(data);
				output.close();
			}
		}
		catch (IOException e)
		{
			System.out.println("Unable to write bytes: " + e.getMessage());
		}
	}

	/**
	 * This method reads the data bytes from a file and returns an array of bytes that contain the file's data.
	 * 
	 * @param filename
	 *            The name of a file
	 * @return The bytes contained in the specified file
	 */
	static public byte[] readBytesFromFile(String filename)
	{
		try
		{
			FileInputStream input = new FileInputStream(filename);
			byte[] data = new byte[input.available()];
			input.read(data);
			input.close();
			return data;
		}
		catch (IOException e)
		{
			System.out.println("Unable to read bytes from " + filename);
			return null;
		}
	}

	/**
	 * This method writes the data bytes to the specified file. Caution - this replaces the contents of the file.
	 * 
	 * @param filename
	 *            The name of a file
	 * @param data
	 *            The bytes to be written to the specified file
	 * @return The bytes contained in the specified file
	 */
	static public void writeBytesToFile(String filename, byte[] data)
	{
		try
		{
			FileOutputStream output = new FileOutputStream(filename);
			output.write(data);
			output.close();
		}
		catch (IOException e)
		{
			System.out.println("Unable to read bytes from " + filename);
		}
	}
}
