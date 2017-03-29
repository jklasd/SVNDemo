package bbkj.org.cn.service;

import java.awt.TextField;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.tmatesoft.svn.core.SVNException;

import bbkj.org.cn.svn.SvnConfig;
import bbkj.org.cn.util.Config;
import bbkj.org.cn.util.FileUtil;
import bbkj.org.cn.window.SvnWin;

public class MainService {
	private String item;
	private SvnWin SVN_WIN;
	private TextField SWtip;
	private static final ExecutorService ec = Executors.newFixedThreadPool(25);
	private void run(Runnable run){
		ec.execute(run);
	}
	public MainService(){}
	public MainService(String item){
		this.item = item;
	}
	public boolean init(){
		String text = "";
		SWtip.setText("正在检查配置信息……");
		try {
			text = "svnUrl不存在";
			SvnConfig.value("svnUrl");
			text = "svnuname不存在";
			SvnConfig.value("svnuname");
			text = "svnpword不存在";
			SvnConfig.value("svnpword");
			
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			SWtip.setText("配置信息有误……【"+text+"】");
			return false;
		}
	}
	public void loadSvnData(){
		try {
			String items = Config.value("items");
			SWtip.setText("清空目标文件夹");
			if(this.item!=null){
				init(this.item);
				run(new LoadItem(this.item));
			}else{
				init(items);
				SWtip.setText("开始运行……");
				for(String item:items.split(";")){
					run(new LoadItem(item));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void init(String itemsName){
		String[] items = itemsName.split(";");
		for(String item : items){
			FileUtil.deleteFile(new File(Config.value(item+"_tag")));
		}
	}
	
	public SvnWin getSVN_WIN() {
		return SVN_WIN;
	}
	public void setSVN_WIN(SvnWin sVN_WIN) {
		SVN_WIN = sVN_WIN;
	}

	public TextField getSWtip() {
		return SWtip;
	}
	public void setSWtip(TextField sWtip) {
		SWtip = sWtip;
	}

	class LoadItem implements Runnable{
		private String item;
		private ProgressRunnable progressR;
		private CopyFile cf= new CopyFile();
		public LoadItem(String item){
			this.item = item;
		}
		private Set<String> files = new HashSet<String>();
		public void run() {
			
			Integer start_v = null;
			Integer end_v = null;
			try {
				String s = SVN_WIN.getJpanel(item).getStartV().getText();
				if(s.trim().length()>0){
					start_v = Integer.valueOf(s);
				}
				s = SVN_WIN.getJpanel(item).getEndV().getText();
				if(s.trim().length()>0){
					end_v = Integer.valueOf(s);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String svn_path = Config.value(item+"_svn_path");
			if(svn_path==null)
				return;
			String[] verions = Config.value(item+"_v").split("-");

			SvnConfig sc = new SvnConfig();
			
			if(start_v!=null && end_v!=null){
				sc.init(start_v, end_v, 5, 100, new String[]{});
			}else{
				sc.init(Integer.valueOf(verions[0]), Integer.valueOf(verions[1]), 5, 100, new String[]{});
			}
			try {
				sc.go(svn_path);
			} catch (SVNException e) {
				e.printStackTrace();
			}
			
			for(String str :sc.getLogList().getFiles()){
				if(files.contains(str)){
				}else{
					files.add(str);
				}
			}
			progressR = new ProgressRunnable(item, files.size());
			
			MainService.this.run(progressR);
			
			String[] replace_paths = Config.value(item+"_replace_path").split(";");
			
			for(String replace_path:replace_paths){
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				String[] key_path= replace_path.split(":");
				String res = Config.value(key_path[0]);
				key_path[2]=key_path[2].replace("null", "");
				String tagDir = Config.value(item+"_tag");//目标文件主目录路径
				
				for(String str : files){
					if(!str.contains(key_path[1])){
						progressR.count__();
						continue;
					}
					String filePath = str.replace(svn_path+key_path[1], "");
					
					String resourceFile = res+filePath;//源文件主目录路径
					try {
						cf.execute(resourceFile, tagDir, key_path[2]+filePath);
						if(filePath.endsWith(".class")){
							//判断是否存在匿名内部类
							int i = 1;
							String newfilepath = filePath.replace(".class", "$"+i+".class");
							File param = new File(resourceFile+newfilepath);
							while(param.exists()){
								System.out.println("存在匿名内部类:"+newfilepath);
								cf.execute(resourceFile,tagDir,newfilepath);
								i++;
								newfilepath = filePath.replace(".class", "$"+i+".class");
								param = new File(resourceFile+newfilepath);
							}
						}

						progressR.setProgress(progressR.getProgress()+1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			progressR.setBreak_(true);
			System.out.println("项目"+item+"完成");
		}
	}
	
	class ProgressRunnable implements Runnable{
		private String item;
		private int count;
		private int progress;
		
		private boolean break_;
		
		public ProgressRunnable(String item,int count){
			this.item=item;
			this.count=count;
		}
		public void count__() {
			this.count--;
		}
		public void run() {
			while(progress<count&&!break_){
				
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				int p = progress*100/count;
				JProgressBar jpb = MainService.this.getSVN_WIN().getJpanel(item).getProgressBar();
				jpb.setValue(p);
				JLabel label = MainService.this.getSVN_WIN().getJpanel(item).getProgressLable();
				label.setText(p+"%");
			}
		}

		public int getProgress() {
			return progress;
		}

		public void setProgress(int progress) {
			this.progress = progress;
		}

		public boolean isBreak_() {
			return break_;
		}

		public void setBreak_(boolean break_) {
			this.break_ = break_;
		}
	}
}
