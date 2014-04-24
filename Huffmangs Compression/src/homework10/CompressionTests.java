package homework10;

import homework10.HuffmanCompressor;
import homework10.HuffmanTools;

import java.util.HashMap;

import org.junit.Test;

public class CompressionTests
{
	private static final String Days = "80Days.txt";
	private static final String Ulysees = "Ulysses.txt";
	private static final String Yankee = "Yankee.txt";

	private static final String[] Files = new String[] { Days, Ulysees, Yankee };

	private static HashMap<String, byte[]> compressedDatas = new HashMap<String, byte[]>();

	@Test
	public void testCompressed()
	{
		System.out.println("====== Compression Test =======");
		System.out.println("File\t\tUncompressed size\tCompressed size\t\tCompressed Percentage\t\tResult");
		for (String file : Files)
		{
			byte[] og = HuffmanTools.readBytesFromFile(file);
			byte[] compressed = new HuffmanCompressor().compress(og);
			outputCompressionResult(og, compressed, file);
			compressedDatas.put(file, compressed);
		}
	}

	@Test
	public void testDecompressed()
	{
		System.out.println();
		System.out.println("====== Decompression Test =======");
		System.out.println("File\t\tResult");
		for (String file : Files)
		{
			byte[] og = HuffmanTools.readBytesFromFile(file);
			byte[] compressed = compressedDatas.get(file);
			byte[] decompressed = new HuffmanCompressor(false).decompress(compressed);

			outputDecompressionResult(og, decompressed, file);
		}
	}

	private void outputCompressionResult(byte[] orig, byte[] compressed, String file)
	{
		System.out.println(file + "\t" + orig.length + "\t\t\t" + compressed.length + "\t\t\t~" + ((float) compressed.length / (float) orig.length * 100) + "%\t\t\t"
				+ (compressed.length < orig.length ? "Passed" : "Failed"));
	}

	private void outputDecompressionResult(byte[] orig, byte[] decompressed, String file)
	{
		boolean isEqual = true;
		if (orig.length != decompressed.length)
			isEqual = false;
		else
			for (int i = 0; i < orig.length; i++)
				if (orig[i] != decompressed[i])
					isEqual = false;

		System.out.println(file + "\t" + (isEqual ? "Passed" : "Failed"));
	}

}
