package homework10;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * A HuffmanCompressor object contains no data - it is just an implementation of the Compressor interface. It contains the compress and decompress methods, along with a series of
 * helper methods for counting tokens, building the Huffman tree, and storing data in byte arrays.
 * 
 * The only methods that should be public are the constructor and the Compressor interface methods, the rest should be private. I have left them public, though, for testing.
 * 
 * @author Peter Jensen & Brayden Lopez - CS 2420
 * @version Spring 2014
 */
public class HuffmanCompressor implements Compressor
{

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// If true, it will output the tokens when it builds them //
	private boolean debug = false;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	/**
	 * This constructor does nothing. There are no fields to initialize. It is provided simply for testing. (You must make a HuffmanCompressor object in order to test the compress
	 * and decompress methods.)
	 */
	public HuffmanCompressor()
	{
	}

	public HuffmanCompressor(boolean debug)
	{
		// If true, it will output the tokens when it builds them //
		this.debug = debug;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	/**
	 * Given any array of bytes that contain some data, this method returns a compressed form of the original data. The returned, compressed bytes must contain sufficient
	 * information so that the decompress method below can reconstruct the original data.
	 * 
	 * Huffman coding is used to compress the data.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * Some of the code for this method has been provided for you. You should figure out what it does, it will significantly help you.
	 * 
	 * @param compressedData
	 *            An array of bytes that contains some data that should be compressed
	 * @return An array of bytes that contains the compressed form of the original data
	 */
	@Override
	public byte[] compress(byte[] data)
	{
		ArrayList<HuffmanToken> tokens = countTokens(data);
		Map<Byte, ArrayList<Boolean>> charMap = createEncodingMap(tokens);
		ArrayList<Boolean> encodedBits = encodeBytes(data, charMap);

		if (debug)
			HuffmanTools.dumpHuffmanCodes(tokens);

		try
		{
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			DataOutputStream output = new DataOutputStream(byteOutput);

			// Write the length of the compressed data //
			output.writeInt(data.length);

			// Write the list of tokens to the output stream //
			writeTokenList(output, tokens);

			// Write the compressed data //
			writeBitCodes(output, encodedBits);

			return byteOutput.toByteArray();
		}
		catch (IOException e)
		{
			System.out.println("Fatal compression error: " + e.getMessage());
			e.printStackTrace();
			return new byte[0];
		}

	}

	/**
	 * Given an array of bytes that contain compressed data that was compressed using this compressor, this method will reconstruct and return the original, uncompressed data. The
	 * compressed bytes must contain sufficient information so that this method can reconstruct the original data bytes.
	 * 
	 * Huffman coding is used to decompress the data.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * Some of the code for this method has been provided for you. You should figure out what it does, it will significantly help you.
	 * 
	 * @param compressedData
	 *            An array of bytes that contains some data in compressed form
	 * @return An array of bytes that contains the original, uncompressed data
	 */
	@Override
	public byte[] decompress(byte[] compressedData)
	{
		int dataLength;
		ArrayList<HuffmanToken> tokens;
		ArrayList<Boolean> encodedBits;

		try
		{
			ByteArrayInputStream byteInput = new ByteArrayInputStream(compressedData);
			DataInputStream input = new DataInputStream(byteInput);

			// Get the length of the data //
			dataLength = input.readInt();

			// Read the list of tokens //
			tokens = readTokenList(input);

			// Get the compressed data //
			encodedBits = readBitCodes(input);

			// Build tree from the list of tokens //
			HuffmanNode root = buildHuffmanCodeTree(tokens);

			if (debug)
				HuffmanTools.dumpHuffmanCodes(tokens); // Useful for debugging

			// Decompress the bits based on the tree of HuffmanTokens //
			return decodeBits(encodedBits, root, dataLength);
		}
		catch (IOException e)
		{
			System.out.println("Fatal compression error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}


	// ===========================================================
	// Methods
	// ===========================================================
	
	

	/**
	 * This helper method counts the number of times each data value occurs in the given byte array. For each different value, a HuffmanToken is created and stored. When the same
	 * value is seen again, its token's frequency is increased. After all the different data values have been counted this method will return an ArrayList of HuffmanToken objects.
	 * Each token will contain a count of how many times that token occurred in the byte array. (If you summed up the counts, the total would be same as the length of the data
	 * array.)
	 * 
	 * Note that the returned tokens in the ArrayList may be in any order.
	 * 
	 * @param data
	 *            A list of data bytes
	 * @return A list of HuffmanTokens that contain token counts
	 */
	private ArrayList<HuffmanToken> countTokens(byte[] data)
	{
		// Build a map of tokens, based on a key of bytes //
		HashMap<Byte, HuffmanToken> tokens = new HashMap<Byte, HuffmanToken>();
		for (byte b : data)
		{
			if (!tokens.containsKey(b))
				tokens.put(b, new HuffmanToken(b));
			else
				tokens.get(b).incrementFrequency();
		}

		// Transform the map into an array list //
		ArrayList<HuffmanToken> tokensList = new ArrayList<HuffmanToken>();
		for (Entry<Byte, HuffmanToken> entry : tokens.entrySet())
			tokensList.add(entry.getValue());

		return tokensList;
	}

	/**
	 * This helper method builds and returns a Huffman tree that contains the given tokens in its leaf nodes.
	 * 
	 * The Huffman tree-building algorithm is used here. You may find it in the book or in your notes from lecture. Remember to first create leaf nodes for all the tokens, and add
	 * these leaf nodes to a priority queue. You may then build and return the tree.
	 * 
	 * It is assumed that the tokens do not have Huffman codes when this method is called. Due to the side-effect of one of the HuffmanToken constructors, the HuffmanToken objects
	 * will have correct Huffman codes when this method finishes building the tree. (They are built as the tree is built.)
	 * 
	 * @param tokens
	 *            A list of Tokens, each one with a frequency count
	 * @return The root node of a Huffman tree
	 */
	private HuffmanNode buildHuffmanCodeTree(ArrayList<HuffmanToken> tokens)
	{
		PriorityQueue<HuffmanNode> mQueue = new PriorityQueue<HuffmanNode>();

		for (HuffmanToken token : tokens)
		{
			HuffmanNode node = new HuffmanNode(token);
			mQueue.add(node);
		}

		// PQueue is already in order, just traverse the queue and rebuilt as a tree //
		while (mQueue.size() > 1)
		{
			HuffmanNode left = mQueue.remove();
			HuffmanNode right = mQueue.remove();
			HuffmanNode parent = new HuffmanNode(left, right);

			mQueue.offer(parent);
		}

		return mQueue.element();
	}

	/**
	 * This helper method creates a dictionary of Huffman codes from a list of Huffman tokens. It is assumed that the Huffman tokens have correct Huffman codes stored in them.
	 * 
	 * This method is for convenience only. Values and Huffman codes are extracted from the tokens and added to a Map object so that they can be quickly retrieved when needed.
	 * 
	 * @param tokens
	 *            A list of Tokens, each one with a Huffman code
	 * @return A map that maps byte values to their Huffman codes
	 */
	private Map<Byte, ArrayList<Boolean>> createEncodingMap(ArrayList<HuffmanToken> tokens)
	{
		Map<Byte, ArrayList<Boolean>> elMappo = new HashMap<Byte, ArrayList<Boolean>>();
		HuffmanNode root = buildHuffmanCodeTree(tokens);

		buildMap(new ArrayList<Boolean>(), elMappo, root);

		return elMappo;
	}

	private void buildMap(ArrayList<Boolean> toBuild, Map<Byte, ArrayList<Boolean>> map, HuffmanNode node)
	{

		if (node.getLeftSubtree() != null)
			buildMap(addAndReturn(false, toBuild), map, node.getLeftSubtree());

		if (node.getRightSubtree() != null)
			buildMap(addAndReturn(true, toBuild), map, node.getRightSubtree());

		if (node.getToken() != null)
		{
			node.getToken().setCode(toBuild);
			map.put(node.getToken().getValue(), toBuild);
		}

	}

	private <T> ArrayList<T> addAndReturn(T item, ArrayList<T> list)
	{
		ArrayList<T> newList = new ArrayList<T>(list);
		newList.add(item);
		return newList;
	}

	

	/**
	 * This helper method encodes an array of data bytes as an ArrayList of bits (Boolean values). Huffman codes are used to translate the bytes into bits.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * For every value in the data array, the corresponding Huffman code is retrieved from the map and added to a new ArrayList that will be returned.
	 * 
	 * @param data
	 *            An array of data bytes that will be encoded (compressed)
	 * @param encodingMap
	 *            A map that maps byte values to Huffman codes (bits)
	 * @return An ArrayList of bits (Booleans) that represent the compressed (Huffman encoded) data
	 */
	private ArrayList<Boolean> encodeBytes(byte[] data, Map<Byte, ArrayList<Boolean>> encodingMap)
	{
		ArrayList<Boolean> finalEncoding = new ArrayList<Boolean>();

		for (byte b : data)
		{
			finalEncoding.addAll(encodingMap.get(b));
		}

		return finalEncoding;
	}

	/**
	 * This helper method decodes a list of bits (which are Huffman codes) into an array of bytes. In order to do the decoding, a Huffman tree containing the tokens is required.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * To do the decoding, follow the decoding algorithm given in the book or review your notes from lecture.
	 * <p>
	 * &nbsp;
	 * <p>
	 * 
	 * (You will need to build a Huffman tree prior to calling this method, and the Huffman tree you build should be exactly the same as the one that was used to encode the data.)
	 * 
	 * @param bitCodes
	 *            An ArrayList of bits (Booleans) that represent the compressed (Huffman encoded) data
	 * @param codeTree
	 *            A Huffman tree that can be used to decode the bits
	 * @param dataLength
	 *            The number of bytes that will be in the decoded byte array
	 * @return An array of bytes that represent the uncompressed data
	 */
	private byte[] decodeBits(ArrayList<Boolean> bitCodes, HuffmanNode codeTree, int dataLength)
	{
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(dataLength);
		DataOutputStream output = new DataOutputStream(byteOutput);

		HuffmanNode currentNode = codeTree;
		int index = 0;
		try
		{
			while (output.size() < dataLength)
			{
				if (index >= bitCodes.size())
				{
					output.write(currentNode.getToken().getValue());
					currentNode = codeTree;
					return byteOutput.toByteArray();
				}
				if (!bitCodes.get(index))
				{
					// Bit read was 0, go left //

					if (currentNode.getToken() == null)
					{
						currentNode = currentNode.getLeftSubtree();
						index++;
					}
					else
					{
						output.write(currentNode.getToken().getValue());
						currentNode = codeTree;
					}
				}
				else
				{
					// Bit read was 1, go right //

					if (currentNode.getToken() == null)
					{
						currentNode = currentNode.getRightSubtree();
						index++;
					}
					else
					{
						output.write(currentNode.getToken().getValue());
						currentNode = codeTree;
					}
				}

			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return byteOutput.toByteArray();
	}
	
	// ----- IO Operations ----- //
	

	/**
	 * This helper method writes the list of Huffman tokens to the specified output stream.
	 * 
	 * @param output
	 *            the output stream
	 * @param tokens
	 *            the tokens to output
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeTokenList(DataOutputStream output, ArrayList<HuffmanToken> tokens) throws IOException
	{
		// Write the size of the token list //
		output.writeInt(tokens.size());

		for (HuffmanToken token : tokens)
		{
			// Write the value of the token //
			output.writeByte(token.getValue());

			// Write the frequency of the token //
			output.writeInt(token.getFrequency());
		}
	}

	/**
	 * This helper method reads data from the input stream and converts the incoming data into a list of Huffman tokens.
	 * 
	 * @param input
	 *            the input stream to read from
	 * @return the array list of huffman tokens
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private ArrayList<HuffmanToken> readTokenList(DataInputStream input) throws IOException
	{
		ArrayList<HuffmanToken> tokens = new ArrayList<HuffmanToken>();

		// Get the size of the token table //
		int count = input.readInt();

		for (int i = 0; i < count; i++)
		{
			// Read the value of the token //
			HuffmanToken token = new HuffmanToken(input.readByte());

			// Read the frequency of the token //
			token.setFrequency(input.readInt());

			tokens.add(token);
		}

		return tokens;
	}

	/**
	 * Writes the array of booleans (represents 1 or 0 bits) to the specified output stream.
	 * 
	 * @param output
	 *            the output stream to write to
	 * @param bits
	 *            the bits to write
	 * @return the number of bytes written.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private int writeBitCodes(DataOutputStream output, ArrayList<Boolean> bits) throws IOException
	{
		int bytesWritten = 0;

		// Convert 8 bit chunks into a byte //
		for (int pos = 0; pos < bits.size(); pos += 8)
		{
			int b = 0;
			for (int i = 0; i < 8; i++)
			{
				b = b * 2;
				if (pos + i < bits.size() && bits.get(pos + i))
					b = b + 1;
			}

			// Write the obtained byte to the output stream //
			output.writeByte((byte) b);
			bytesWritten++;
		}

		return bytesWritten;
	}

	/**
	 * This helper method reads data from the input stream and converts it into an array of booleans (representing 1 and 0 bits)
	 * 
	 * @param input
	 *            the input stream to read from
	 * @return the array list of booleans
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private ArrayList<Boolean> readBitCodes(DataInputStream input) throws IOException
	{
		ArrayList<Boolean> bits = new ArrayList<Boolean>();

		while (input.available() > 0)
		{
			// Reads the byte in 8 bit chunks //
			int b = input.readByte();

			// Read each bit from the obtained byte //
			for (int i = 7; i >= 0; i--)
				bits.add(((b >> i) & 1) == 1);
		}

		return bits;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================



}
