/*
 * Copyright 2015 Wojciech Zankowski
 *
 * http://www.zankowski.pl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.zankowski.yahoofinanceapi;

import javax.xml.bind.ValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 25.08.2015
 *
 * @author Wojciech Zankowski
 */
public class UrlDataParser {

	private final static Logger LOGGER = Logger.getLogger(UrlDataParser.class.getName());

	protected static String oneLineReader(URL url) throws IOException, ValidationException {
		List<String> lines = manyLinesReader(url);

		if (lines.size() > 1) {
			throw new ValidationException("More than one line.");
		}

		return lines.get(0);
	}

	protected static List<String> manyLinesReader(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		List<String> lines = new ArrayList<>();
		InputStream input = null;
		BufferedReader br = null;

		try {
			input = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LOGGER.log(Level.WARNING, "Couldn't close input stream. " + e.getMessage());
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.log(Level.WARNING, "Couldn't close Buffered Reader. " + e.getMessage());
				}
			}
		}

		return lines;
	}

	protected static BufferedReader historicalDataStream(URL url) throws IOException {
		URLConnection connection = url.openConnection();

		InputStream input = connection.getInputStream();
		return new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
	}

}
