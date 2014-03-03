package com.elivoa.aliprint.func.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.elivoa.aliprint.func.security.SHA1Encrypt;

/**
 * FileUtil 文件处理工具，所有与文件处理相关的操作都可以通过调用该工具的接口完成
 */
public class FileUtil {

	/**
	 * @param seed
	 * @return
	 */
	public static String generateTempStorageName(String seed) {
		try {
			seed += new Long(System.currentTimeMillis()).toString();
			return SHA1Encrypt.SHA1(seed) + "." + new Long(System.currentTimeMillis()).toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new Integer((seed + (new Date().toString())).hashCode()).toString() + "."
					+ new Long(System.currentTimeMillis()).toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new Integer((seed + (new Date().toString())).hashCode()).toString() + "."
					+ new Long(System.currentTimeMillis()).toString();
		}
	}

	/**
	 * 获得文件的目录路径
	 * 
	 * @param f
	 *            要获取的文件对象
	 * @return 目录路径
	 */
	public static String getFileDirectory(File f) {
		String apath = f.getAbsolutePath();
		String dir = apath.substring(0, apath.lastIndexOf("/"));
		return dir.toLowerCase();
	}

	/**
	 * 获取文件的基本名，例如输入mypicture.jpg，则输出mypicture
	 * 
	 * @param fileName
	 *            输入的文件名
	 * @return 输出的文件基本名
	 */
	public static String getFileBasename(String fileName) {
		int startPos = fileName.lastIndexOf("/") + 1;
		int endPos = fileName.lastIndexOf(".");
		if (endPos < startPos) {
			return fileName;
		} else {
			return fileName.substring(startPos, endPos).toLowerCase();
		}
	}

	/**
	 * 获取文件的扩展名，例如输入mypicture.jpg，则输出.jpg
	 * 
	 * @param fileName
	 *            输入的文件名
	 * @return 输出的文件扩展名
	 */
	public static String getFileExtension(String fileName) {
		int startPos = fileName.lastIndexOf(".");
		if (startPos < 0) {
			return "";
		} else {
			return fileName.substring(startPos + 1).toLowerCase();
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @throws IOException
	 *             当读取文件是出现错误，则抛出该异常
	 */
	public static void copyFile(String srcFile, String destFile) throws IOException {
		File f1 = new File(srcFile);
		File f2 = new File(destFile);

		File destDir = new File(getFileDirectory(f2));
		if (!destDir.exists()) {
			destDir.mkdirs();
		}

		if (!destDir.isDirectory()) {
			throw new IOException("Target file path is invalid");
		}

		InputStream in = new FileInputStream(f1);
		OutputStream out = new FileOutputStream(f2);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}

		in.close();
		out.close();
	}

	public static void copyFile(File sourceFile, File destinationFile) throws IOException {

		File destDir = new File(getFileDirectory(destinationFile));
		if (!destDir.exists()) {
			destDir.mkdirs();
		}

		if (!destDir.isDirectory()) {
			throw new IOException("Target file path is invalid");
		}

		InputStream in = new FileInputStream(sourceFile);
		OutputStream out = new FileOutputStream(destinationFile);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}

		in.close();
		out.close();
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            要删除文件的路径
	 * @throws IOException
	 *             如果读取文件的时候出现错误，则抛出该异常
	 */
	public static void del(String path) throws IOException {
		File f = new File(path);
		f.delete();
	}

	/**
	 * 删除文件夹
	 * 
	 * @param path
	 *            要删除文件的路径
	 * @throws IOException
	 *             如果读取文件的时候出现错误，则抛出该异常
	 */
	public static void delFolder(String path) throws IOException {
		File f = new File(path);
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			if (null != files) {
				for (File file : files) {
					file.delete();
				}
			}
		}
		// delete folder;
		f.delete();
	}

	/**
	 * 获得当前的临时文件名称
	 * 
	 * @param prefix
	 *            临时文件的前缀
	 * @return 产生的临时文件名称
	 */
	public static String getTempFileName(String prefix) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHMMSS ");
			return new String(prefix + md.digest(sdf.format(new Date()).getBytes()) + ".tmp");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// Log errors
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHMMSS ");
			return new String(prefix + sdf.format(new Date()) + ".tmp");
		}

	}

	/**
	 * 为指定的文件准备路径，创建所有路径中可能不存在的目录
	 * 
	 * @param file
	 *            指定的文件对象
	 * @throws IOException
	 *             如果在创建路径的过程中出现文件读写错误，则抛出该异常
	 */
	public static void preparePath(File file) throws IOException {
		String dirname = FileUtil.getFileDirectory(file);
		File folder = new File(dirname);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
}
