package homework7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class TreeReader {
	;

	public static Node<String> readTreeFromFile(String file) throws IOException {
		return readTreeFromFile(new File(file));
	}

	public static Node<String> readTreeFromFile(File file) throws IOException {
		if (!file.exists())
			return null;

		Node<String> current = null;
		Node<String> parent = null;

		InputStream fileStream = null;
		BufferedReader reader = null;
		String line;

		try {
			fileStream = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(fileStream,
					Charset.forName("UTF-8")));
			while ((line = reader.readLine()) != null) {
				String[] split = line.trim().split(" ");
				if (split[0].startsWith("</")) {
					// Is a closing tag //

					// Go back up the tree //
					current = parent;
					if (!current.isRoot())
						parent = parent.getParent();

					// </index>
					String index = split[0].substring(2).replace('>', '\0').trim();
					System.out.println("CLOSING TAG: " + index);
				} else {
					// Is an opening tag //
					parent = current;
					// <index value>

					String index = split[0].substring(1).trim();
					String value = split[1].replace('>', '\0').trim();

					current = new Node<String>(value);
					if (parent == null) {
						parent = current;
						parent.setRoot(true);
					} else {
						parent.getChildren().add(current);
						current.setParent(parent);
					}

					System.out.println("OPENING TAG: " + index);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
		}

		return parent;
	}
}
