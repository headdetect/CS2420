package lab10;

import java.io.File;
import java.io.FileInputStream;

public class MessageDecoder
{

	public static void main(String[] a)
	{

		try
		{
			File fi = new File("secret.txt");
			FileInputStream stream = new FileInputStream(fi);
			byte[] bytes = new byte[stream.available()];
			stream.read(bytes);

			// Bit[i][2] = Bit[i / 8][i % 8] 
			
			byte current = 0x00;
			byte[] newBytes = new byte[bytes.length / 8];
			
			for (int i = 0; i < bytes.length; i++)
			{
				
				int bytePos = i / 8;
				int bitPos = i % 8;
				byte thisByte = bytes[i];
				thisByte = (byte) ((thisByte >> 2) & 0x1);
				if(bitPos == 0) current = 0x00;
				
				current = (byte) (current | (thisByte << bitPos));
				newBytes[bytePos] = (byte)(current & 0xFF);
			}
			
			System.out.print(new String(newBytes, "ASCII"));

			stream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
