package bbkj.org.cn.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bbkj.org.cn.util.FileUtil;

public class CopyFile {
	private Pattern pattern = Pattern.compile(".*\\/.*\\.[^\\/]*$");
	
	public void execute(String sourceFilePath,String targetDirPath,String filepath){
		
		File file = new File(sourceFilePath);
		if(!file.exists()){
			System.out.println("文件不存在===="+file);
			return;//文件不存在
		}
//		if(file.getPath().contains("acc")){
//			System.out.println("问题");
//		}
		File targetFile = new File(targetDirPath+filepath);
		FileUtil.createFile(targetFile);
		Matcher ms = pattern.matcher(filepath);
		if(ms.find())
			copyFile(targetFile,file);
		else{
			System.out.println("这是个文件夹");
			targetFile.delete();
			targetFile.mkdir();
		}
	}

	private void copyFile(File targetFile, File file) {
		try {
			if(file==null || !file.exists()){
				System.out.println("源文件不存在");
				return;
			}
			if(targetFile==null || !targetFile.exists()){
				System.out.println("目标文件不存在");
			}
//			if(file.getPath().contains("moneyagain")){
//				System.out.println("问题");
//			}
			InputStream is = new FileInputStream(file);
			OutputStream os = new FileOutputStream(targetFile);
			byte[] bytes = new byte[1024];
			int m = 0;
			while((m=is.read(bytes))!=-1){
				os.write(bytes, 0, m); 
			}
			System.out.println(targetFile+"复制完成");
			is.close();
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
