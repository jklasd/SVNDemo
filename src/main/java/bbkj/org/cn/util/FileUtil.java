package bbkj.org.cn.util;

import java.io.File;

public class FileUtil {
	public static synchronized void deleteFile(File file) {
		if(file.exists()){
			if(file.isFile()){
				file.delete();
				return;
			}else if(file.listFiles().length>0){
				for(File f:file.listFiles()){
					deleteFile(f);
				}
			}
			file.delete();
		}
	}
	
	public static synchronized boolean createFile(File file){
		try {
			if(file.exists()){
				return false;
			}
			
			if(!file.getParentFile().exists()){
				createFile(file.getParentFile());
			}
			int start = file.getPath().lastIndexOf(".");
			int end = file.getPath().length();
			String filesuffix= start>0?file.getPath().substring(start,end):file.getPath();
			if(Config.value("file_suffix").contains(filesuffix)){
				return file.createNewFile();
			}else{
				return file.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
