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
 * 通用文件上传类，对FileUpload的包装
 */


public class FileUploadUtil {
	/**
	 * 初始化,获取FileItem的列表并返回
	 */
	@SuppressWarnings("unchecked")
	public static List<FileItem> init(HttpServletRequest request,HttpServletResponse response) {
		ServletEncoding.setEncoding(request, response);
		List<FileItem> listFileItem = new ArrayList<FileItem>();
		
		//Map<String, String> fileUpInfo = new HashMap<String, String>();
		
		//List<FileItem> list = new ArrayList<FileItem>();
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);    //内存中允许的最大数据
		factory.setRepository(new File("d:\\"));  // 一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		servletFileUpload.setSizeMax(4096*4096);  //文件超过大小就抛异常
		try {
			// 获取request中包含的FileItems
			listFileItem = servletFileUpload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
			System.out.print("size is too big ");
		}
		return listFileItem;		
	}

	/**
	 * 获取文件域信息保存在Map<String,FileItem>
	 * 
	 * @param fileItems
	 * @return
	 */
	public static Map<String, FileItem> getFile(List<FileItem> fileItems) {
		Map<String, FileItem> mapFiles = new HashMap<String, FileItem>();
		for (int i = 0; i < fileItems.size(); i++) {
			FileItem fileItem = (FileItem) fileItems.get(i);
			if (!fileItem.isFormField()) {
				// 不是表单域，就将FileItem对象直接存入Map中
				mapFiles.put(fileItem.getFieldName(), fileItem);
			}
		}
		return mapFiles;
	}

	/**
	 * 获取表单域信息,保存在Map(String,String)
	 * 
	 * @param fileItems
	 * @return
	 */
	public static Map<String, String> getField(List<FileItem> fileItems) {
		Map<String, String> mapFields = new HashMap<String, String>();
		for (int i = 0; i < fileItems.size(); i++) {
			FileItem fileItem = fileItems.get(i);
			if (fileItem.isFormField()) {
				// 表单域则取出其value值存入Map中
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
	 * pathDir表示目录 将获取的文件域另存为目pathDir下面
	 * 
	 * @param fileItem
	 * @param path
	 * @return
	 */
	public static String saveAs(FileItem fileItem, File pathDir) {
		String path = "";
		// 获取文件后缀
		String suffix = FileUtil.getFileSuffix(fileItem.getName());
		if (pathDir.exists()) {
			// 如果路径存在
			File file = new File(pathDir, UUID.randomUUID() + "." + suffix);
			try {
				fileItem.write(file);
			} catch (Exception e) {
				e.printStackTrace();

			}
			path = file.getPath();
		} else {
			// 路径不存在先创建
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
	 * 把一个文件集合另存为某个路径，并将其以文件域名字，路径名方式返回Map(String,String)
	 */
	public static Map<String, String> saveAs(HttpServletRequest request,Map<String, FileItem> mapFile,
			File pathDir) {
		Map<String, String> saveAsMap = new HashMap<String, String>();
		// 获取Key值的集合并且迭代,从Map中取出FileItem
		Iterator<String> i = mapFile.keySet().iterator();
System.out.println(mapFile.keySet());
		while(i.hasNext()) {
			//取出Map中的映射
			String fieldName = i.next();
			FileItem fileItem = mapFile.get(fieldName);
			// 将文件另存为pathDir
			String path = FileUtil.changePathToRelative(request, FileUploadUtil.saveAs(fileItem, pathDir));
			saveAsMap.put(fieldName, path);
		}
		return saveAsMap;
	}	
}
