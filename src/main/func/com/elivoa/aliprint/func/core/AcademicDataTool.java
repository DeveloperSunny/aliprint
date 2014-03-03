package com.elivoa.aliprint.func.core;

import java.util.List;

import com.elivoa.aliprint.func.Strings;

public class AcademicDataTool {

	public static List<String> splitAuthors(String authorString) {
		List<String> authors = Strings.safeSplitToList(authorString, ",");
		return authors;
	}

}
