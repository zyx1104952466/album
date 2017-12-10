/**
 * 
 */
package xgu.jkx.fupeng.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Administrator
 * 
 */
public class FileUtil {

	private static final int BUFFER_SIZE = 32 * 1024;

	/**
	 * 文件工具
	 */
	public FileUtil() {

	}

	public static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建一个新的目录 如果创建成功返回这个新目录 创建失败就返回null
	 * 
	 * @param file
	 * @return
	 */
	public static File createFileDir(File file) {
		File f;
		if (file.exists()) {
			// 路径已经存在
			f = file;
		} else {
			// 路径不存在
			if (file.mkdirs()) {
				// 如果创建成功
				f = file;
			} else {
				// 创建失败
				f = null;
			}
		}
		return f;
	}

	/**
	 * 获取文件后缀名 并且改为小写
	 */
	public static String getFileSuffix(String fileName) {
		String suffix;
		suffix = fileName.trim()
				.substring(fileName.trim().lastIndexOf(".") + 1).toLowerCase();
		return suffix;
	}

	/**
	 * 改绝对路径为相对路径 注意是相对web应用名字的路径
	 * 
	 * @param request
	 * @param path
	 * @return
	 */
	public static String changePathToRelative(HttpServletRequest request,
			String path) {
		String relativePath = "";
		String contextPath = request.getContextPath();
		// 获取该路径在Web中的路径
		String webPath = path.substring(path.lastIndexOf(contextPath
				.substring(1)));
		// 从web路径中获取相对路径
		relativePath = webPath.substring(request.getContextPath().length());
		return relativePath;
	}

	/**
	 * 构造一个绝对路径
	 */
	@SuppressWarnings("deprecation")
	public static File changePathToAbsol(HttpServletRequest request, String path) {
		return FileUtil.createFileDir(new File(request.getRealPath("")
				+ changePath(path)));
	}

	/**
	 * 替换路径中的/和\
	 */
	public static String changePath(String path) {
		return path.replace("//", File.separator).replace("\\", File.separator);
	}

	/**
	 * 删除一个文件，如果是目录就删除目录和目录下的所有文件
	 */
	public static boolean deleteFile(File file) {
		boolean ok = true;
		if (file.isFile()) {
			// 文件
			if (file.exists()) {
				file.delete();
			}
		} else if (file.isDirectory()) {
			// 目录，递归删除
			System.out.println(file.getName());
			if (file.listFiles() == null || file.listFiles().length == 0) {
				file.delete();
			} else {
				File[] f = file.listFiles();
				for (int i = 0; i < f.length; i++) {
					deleteFile(f[i]);
					System.out.println(f[i].getName());
				}
			}
			file.delete();
		}
		return ok;
	}

	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	public static String getFileName(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(0, pos);
	}

	public static void main(String[] args) {
		String filename = FileUtil.getFileName("123.jpg");
		System.out.println(filename);
	}
}
