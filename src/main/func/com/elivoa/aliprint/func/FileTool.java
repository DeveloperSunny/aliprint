package com.elivoa.aliprint.func;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;

/**
 * @desc - FileTool
 * 
 * @author gb<elivoa@gmail.com> @date Mar 6, 2009 @version 0.2.0.0
 * 
 * @ModifiedDate Mar 6, 2009
 * 
 * @ModifiedDate Mar 9, 2009 - Fixed a bug of readFileToString().
 * 
 * @Modified By gb, @Date Mar 23, 2009 - Refactor, merge in util.
 * 
 */
public class FileTool {
	private static final Log log = LogFactory.getLog(FileTool.class);

	public static final String DEFAULT_FILE_CHARSET = "utf-8";

	public static OutputStreamWriter openWriter(String filepath) {
		return openWriter(filepath, true);
	}

	/*
	 * Open an OutputStreamWriter, auto create file path and delete exists file.
	 */
	public static OutputStreamWriter openWriter(String filepath, boolean append) {
		try {
			File file = new File(filepath);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			} else {
				log.info("File exists, delete. '" + filepath + "'");
				file.delete();
				file.createNewFile();
			}
			file.createNewFile();// TODO test to delete this.
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filepath, append),
					DEFAULT_FILE_CHARSET);
			return writer;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Flush and close a Writer.
	 * 
	 * @ deprecated
	 */
	public static void close(OutputStreamWriter writer) {
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * write string 's' to file. delete if file exists.
	 * 
	 * @param fname
	 *            - file name
	 * @param s
	 *            - string content
	 */
	public static void writeToFile(String fname, String s) {
		if (null == s)
			s = "";
		try {
			File file = new File(fname);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fname, false),
					DEFAULT_FILE_CHARSET);
			writer.write(s);
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * append to file
	 * 
	 * @param fname
	 * @param s
	 */
	private static void appendToFile(String fname, String s) {
		OutputStreamWriter writer = null;
		try {
			File file = new File(fname);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			writer = new OutputStreamWriter(new FileOutputStream(fname, true), DEFAULT_FILE_CHARSET);
			writer.write(s);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (null != writer) {
					writer.close();
				}
			} catch (IOException e) {
			}
		}
	}

	public static String readFileToString(String filePath) {
		return readFileToString(new File(filePath));
	}

	public static String readFileToString(File filePath) {
		return readFileToString(filePath, DEFAULT_FILE_CHARSET);
	}

	public static StringBuilder readFileToStringBuilder(File filePath) {
		return readFileToStringBuilder(filePath, DEFAULT_FILE_CHARSET);
	}

	public static String readFileToString(File file, String encoding) {
		return readFileToStringBuilder(file, encoding).toString();
	}

	public static StringBuilder readFileToStringBuilder(File file, String encoding) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), encoding);
			int r = -1;
			sb = new StringBuilder();
			char[] buffer = new char[64 * 1024];
			while ((r = reader.read(buffer)) != -1) {
				for (int i = 0; i < r; i++) {
					sb.append(buffer[i]);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb;
	}

	public static List<String> readFileToStringList(File file, String encoding) throws IOException {
		List<String> list = new ArrayList<String>();

		int lineNumber = 0;
		String line = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			// line = reader.readLine();
			// read content.
			while ((line = reader.readLine()) != null) {
				list.add(line);
				lineNumber++;
			}
			reader.close();
		} catch (NumberFormatException e) {
			throw new RuntimeException("Format not valid for inpub line " + lineNumber + ":\"" + line + "\", File:"
					+ file.getPath());
		}
		return list;
	}

	/**
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String readFileToStringByLine(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		return sb.toString();
	}

	/**
	 * @Add by vivo. 2008-08-23
	 */
	public static File[] listFiles(String inputFolder) throws FileNotFoundException {
		return listFiles(inputFolder, null);
	}

	public static File[] listFiles(String inputFolder, FilenameFilter filter) throws FileNotFoundException {
		// check file.
		File inputFolderFile = new File(inputFolder);
		if (null == inputFolderFile || !inputFolderFile.exists()) {
			throw new FileNotFoundException("File Not Exists. (" + inputFolder + ")");
		}
		if (!inputFolderFile.canRead() || !inputFolderFile.isDirectory()) {
			throw new RuntimeException("File is Not a Folder or Can't Read. (" + inputFolder + ")");
		}

		// list file.
		File[] files = null;
		if (null == filter) {
			files = inputFolderFile.listFiles();
		} else {
			files = inputFolderFile.listFiles(filter);
		}
		return files;
	}

	public static List<File> listAllFiles(String inputFolder, FilenameFilter filter, int depth, boolean debugOutput,
			boolean debugOutputCount) throws FileNotFoundException {

		// check file.
		File inputFolderFile = new File(inputFolder);
		if (null == inputFolderFile || !inputFolderFile.exists()) {
			throw new FileNotFoundException("File Not Exists. (" + inputFolder + ")");
		}
		if (!inputFolderFile.canRead() || !inputFolderFile.isDirectory()) {
			throw new RuntimeException("File is Not a Folder or Can't Read. (" + inputFolder + ")");
		}

		// list file.
		List<File> files = Lists.newArrayList();
		Stack<File> stack = new Stack<File>();
		stack.push(inputFolderFile);

		int folderCount = 0;
		while (!stack.isEmpty()) {
			File folder = stack.pop();
			if (debugOutput) {
				System.out.println("found folder: " + folder.getAbsolutePath());
			}
			if (debugOutputCount) {
				if (files.size() % 100 == 0) {
					System.out.printf("Detecting files. %d folder, %d files.\b\n", folderCount++, files.size());
				}
			}

			// 1. push folders into stack.
			File[] tmpFiles = folder.listFiles();
			for (File f : tmpFiles) {
				if (f.isDirectory()) {
					stack.push(f);
				}
			}

			// 2. add files.
			if (null == filter) {
				tmpFiles = folder.listFiles();
			} else {
				tmpFiles = folder.listFiles(filter);
			}
			if (null != tmpFiles) {
				for (File f : tmpFiles) {
					if (!f.isDirectory()) {
						files.add(f);
					}
				}
			}
		}

		// final debug
		if (debugOutputCount) {
			System.out.printf("Detecting files. %d folder, %d files.\b\n", folderCount++, files.size());
		}

		return files;
	}

	public static void main(String[] args) throws FileNotFoundException {
		List<File> files = listAllFiles("/var/site/shared-data/pdf", new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.toLowerCase().endsWith(".pdf")) {
					return true;
				}
				return false;
			}
		}, 1, false, true);

		System.out.println("============================");
		for (File f : files) {
			System.out.println(f.getAbsolutePath());
		}

	}

	/**
	 * with callback.
	 * 
	 * @param file
	 * @param lineProcesser
	 * @return
	 * @throws IOException
	 */
	public static int readLineByLine(File file, LineProcesser lineProcesser, String charset) throws IOException {
		if (null == file) {
			System.err.println("FileTool:File is null.");
			return -1;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
		int lineNumber = 0;
		String line = "";
		while ((line = reader.readLine()) != null) {
			int status = lineProcesser.process(lineNumber, line);
			if (-1 == status) {// stop signal
				break;
			}
			lineNumber++;
		}
		return lineNumber;
	}

	public static int readLineByLine(File file, LineProcesser lineProcesser) throws IOException {
		return readLineByLine(file, lineProcesser, DEFAULT_FILE_CHARSET);
	}

	public static String readToStringAndClose(InputStream stream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder sb = new StringBuilder();
		String line = "";
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void ensureDirs(String newPath) {
		File file = new File(newPath);
		File parentFile = file.getParentFile();
		parentFile.mkdirs();
	}
}
