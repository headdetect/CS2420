package homework10.tests;

import static org.junit.Assert.*;
import homework10.HuffmanCompressor;
import homework10.HuffmanTools;

import org.junit.Before;
import org.junit.Test;

public class CompressionTests
{
	private static final String Days = "80Days.txt";
	private static final String Ulysees = "Ulysses.txt";
	private static final String Yankee = "Yankee.txt";
	
	private static final String[] Files = new String[] {Days, Ulysees, Yankee};

	@Test
	public void testCompressed()
	{
		System.out.println("File\tOg Len\tCompress\t%%");
		for(String file : Files) {
			byte[] og = HuffmanTools.readBytesFromFile(Days);
			byte[] compressed = new HuffmanCompressor().compress(og);
			doTestAppature(og, compressed, file);
		}
	}

	private void doTestAppature(byte[] orig, byte[] compressed, String file)
	{
		System.out.println(file + "\t" + orig.length + "\t" + compressed.length + "\t" + (orig.length / compressed.length * 100));
		assertTrue(compressed.length < orig.length);
	}

}
