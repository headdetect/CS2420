package homework10.tests;

import static org.junit.Assert.*;
import homework10.HuffmanCompressor;
import homework10.HuffmanTools;

import org.junit.Test;

public class CompressionTests
{
	private static final String Days = "80Days.txt";
	private static final String Ulysees = "Ulysses.txt";
	private static final String Yankee = "Yankee.txt";
	
	private static final String[] Files = new String[] { Days, Ulysees, Yankee};

	@Test
	public void testCompressed()
	{
		System.out.println("File\t\tUncompressed size\tCompressed size\t\tCompressed Percentage\t\tResult");
		for(String file : Files) {
			byte[] og = HuffmanTools.readBytesFromFile(file);
			byte[] compressed = new HuffmanCompressor(false).compress(og);
			doTestAppature(og, compressed, file);
		}
	}

	private void doTestAppature(byte[] orig, byte[] compressed, String file)
	{
		System.out.println(file + "\t" + orig.length + "\t\t\t" + compressed.length + "\t\t\t~" + ((float)compressed.length / (float)orig.length * 100) + "%\t\t\t" + (compressed.length < orig.length));
		//assertTrue(compressed.length < orig.length);
	}

}
