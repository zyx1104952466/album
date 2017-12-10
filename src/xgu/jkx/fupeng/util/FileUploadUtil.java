package xgu.jkx.fupeng.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import xgu.jkx.fupeng.http.ServletEncoding;

/**
 * ͨ���ļ��ϴ��࣬��FileUpload�İ�װ
 */


public class FileUploadUtil {
	/**
	 * ��ʼ��,��ȡFileItem���б�����
	 */
	@SuppressWarnings("unchecked")
	public static List<FileItem> init(HttpServletRequest request,HttpServletResponse response) {
		ServletEncoding.setEncoding(request, response);
		List<FileItem> listFileItem = new ArrayList<FileItem>();
		
		//Map<String, String> fileUpInfo = new HashMap<String, String>();
		
		//List<FileItem> list = new ArrayList<FileItem>();
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);    //�ڴ���������������
		factory.setRepository(new File("d:\\"));  // һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		servletFileUpload.setSizeMax(4096*4096);  //�ļ�������С�����쳣
		try {
			// ��ȡrequest�а�����FileItems
			listFileItem = servletFileUpload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
			System.out.print("size is too big ");
		}
		return listFileItem;		
	}

	/**
	 * ��ȡ�ļ�����Ϣ������Map<String,FileItem>
	 * 
	 * @param fileItems
	 * @return
	 */
	public static Map<String, FileItem> getFile(List<FileItem> fileItems) {
		Map<String, FileItem> mapFiles = new HashMap<String, FileItem>();
		for (int i = 0; i < fileItems.size(); i++) {
			FileItem fileItem = (FileItem) fileItems.get(i);
			if (!fileItem.isFormField()) {
				// ���Ǳ��򣬾ͽ�FileItem����ֱ�Ӵ���Map��
				mapFiles.put(fileItem.getFieldName(), fileItem);
			}
		}
		return mapFiles;
	}

	/**
	 * ��ȡ������Ϣ,������Map(String,String)
	 * 
	 * @param fileItems
	 * @return
	 */
	public static Map<String, String> getField(List<FileItem> fileItems) {
		Map<String, String> mapFields = new HashMap<String, String>();
		for (int i = 0; i < fileItems.size(); i++) {
			FileItem fileItem = fileItems.get(i);
			if (fileItem.isFormField()) {
				// ������ȡ����valueֵ����Map��
				try {
					mapFields.put(fileItem.getFieldName(), fileItem
							.getString("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					mapFields = null;
				}
			}
		}
		return mapFields;
	}

	/**
	 * pathDir��ʾĿ¼ ����ȡ���ļ������ΪĿpathDir����
	 * 
	 * @param fileItem
	 * @param path
	 * @return
	 */
	public static String saveAs(FileItem fileItem, File pathDir) {
		String path = "";
		// ��ȡ�ļ���׺
		String suffix = FileUtil.getFileSuffix(fileItem.getName());
		if (pathDir.exists()) {
			// ���·������
			File file = new File(pathDir, UUID.randomUUID() + "." + suffix);
			try {
				fileItem.write(file);
			} catch (Exception e) {
				e.printStackTrace();

			}
			path = file.getPath();
		} else {
			// ·���������ȴ���
			File dir = FileUtil.createFileDir(new File(pathDir.getPath()));
			File file = new File(dir, UUID.randomUUID() + "." + suffix);
			try {
				fileItem.write(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			path = file.getPath();
		}
		return path;
	}

	/**
	 * ��һ���ļ��������Ϊĳ��·�������������ļ������֣�·������ʽ����Map(String,String)
	 */
	public static Map<String, String> saveAs(HttpServletRequest request,Map<String, FileItem> mapFile,
			File pathDir) {
		Map<String, String> saveAsMap = new HashMap<String, String>();
		// ��ȡKeyֵ�ļ��ϲ��ҵ���,��Map��ȡ��FileItem
		Iterator<String> i = mapFile.keySet().iterator();
System.out.println(mapFile.keySet());
		while(i.hasNext()) {
			//ȡ��Map�е�ӳ��
			String fieldName = i.next();
			FileItem fileItem = mapFile.get(fieldName);
			// ���ļ����ΪpathDir
			String path = FileUtil.changePathToRelative(request, FileUploadUtil.saveAs(fileItem, pathDir));
			saveAsMap.put(fieldName, path);
		}
		return saveAsMap;
	}	
}
