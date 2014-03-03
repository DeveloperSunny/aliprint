package com.elivoa.aliprint.algorithms;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.elivoa.aliprint.func.FileTool;
import com.elivoa.aliprint.func.LineProcesser;
import com.elivoa.aliprint.func.Strings;
import com.google.common.collect.Sets;

public class StopwordsFilter {
	/**
	 * An unmodifiable set containing some common English words that are not usually useful for searching.
	 */
	public static final Set<String> ENGLISH_STOP_WORDS_SET;

	static {
		final List<String> stopWords = Arrays.asList("a", "an", "and", "are", "as", "at", "be", "but", "by", "for",
				"if", "in", "into", "is", "it", "no", "not", "of", "on", "or", "such", "that", "the", "their",
				"then", "there", "these", "they", "this", "to", "was", "will", "with");
		ENGLISH_STOP_WORDS_SET = Sets.newHashSet();
		ENGLISH_STOP_WORDS_SET.addAll(stopWords);
	}

	public static int addWords(String filePath) {
		File file = new File(filePath);
		if (null != file && file.exists() && file.isFile()) {
			try {
				FileTool.readLineByLine(file, new LineProcesser() {
					public int process(int lineNumber, String line) {
						ENGLISH_STOP_WORDS_SET.add(Strings.deepTrim(line));
						return 0;
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	// add stemmer support
	public static boolean isStopWords(String string) {
		return ENGLISH_STOP_WORDS_SET.contains(string);
	}
}
