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
	 * �ļ�����
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
	 * ����һ���µ�Ŀ¼ ��������ɹ����������Ŀ¼ ����ʧ�ܾͷ���null
	 * 
	 * @param file
	 * @return
	 */
	public static File createFileDir(File file) {
		File f;
		if (file.exists()) {
			// ·���Ѿ�����
			f = file;
		} else {
			// ·��������
			if (file.mkdirs()) {
				// ��������ɹ�
				f = file;
			} else {
				// ����ʧ��
				f = null;
			}
		}
		return f;
	}

	/**
	 * ��ȡ�ļ���׺�� ���Ҹ�ΪСд
	 */
	public static String getFileSuffix(String fileName) {
		String suffix;
		suffix = fileName.trim()
				.substring(fileName.trim().lastIndexOf(".") + 1).toLowerCase();
		return suffix;
	}

	/**
	 * �ľ���·��Ϊ���·�� ע�������webӦ�����ֵ�·��
	 * 
	 * @param request
	 * @param path
	 * @return
	 */
	public static String changePathToRelative(HttpServletRequest request,
			String path) {
		String relativePath = "";
		String contextPath = request.getContextPath();
		// ��ȡ��·����Web�е�·��
		String webPath = path.substring(path.lastIndexOf(contextPath
				.substring(1)));
		// ��web·���л�ȡ���·��
		relativePath = webPath.substring(request.getContextPath().length());
		return relativePath;
	}

	/**
	 * ����һ������·��
	 */
	@SuppressWarnings("deprecation")
	public static File changePathToAbsol(HttpServletRequest request, String path) {
		return FileUtil.createFileDir(new File(request.getRealPath("")
				+ changePath(path)));
	}

	/**
	 * �滻·���е�/��\
	 */
	public static String changePath(String path) {
		return path.replace("//", File.separator).replace("\\", File.separator);
	}

	/**
	 * ɾ��һ���ļ��������Ŀ¼��ɾ��Ŀ¼��Ŀ¼�µ������ļ�
	 */
	public static boolean deleteFile(File file) {
		boolean ok = true;
		if (file.isFile()) {
			// �ļ�
			if (file.exists()) {
				file.delete();
			}
		} else if (file.isDirectory()) {
			// Ŀ¼���ݹ�ɾ��
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
