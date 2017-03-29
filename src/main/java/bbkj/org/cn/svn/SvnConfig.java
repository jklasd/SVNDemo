package bbkj.org.cn.svn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;


public class SvnConfig {
	private static ResourceBundle resource = ResourceBundle.getBundle("svn");
	
	public static String value(String key){
		return resource.getString(key);
	}
    private SVNClientManager manager;  
    private SVNLogHandler logList;
    
    private String[] svnfilePath;
    private long startRevision;
    private long endRevision;
    private long firstRevision;
    private long limit;
    static{
//    	DAVRepositoryFactory.setup(); 
    }
    public SvnConfig() {  
    	DefaultSVNOptions options = new DefaultSVNOptions();
    	manager = SVNClientManager.newInstance(options,value("svnuname"),value("svnpword")); //如果需要用户名密码  
        logList = new SVNLogHandler(new ArrayList<String>());
    }  
    public synchronized void init(long startRevision,long endRevision,long firstRevision,long limit,String[] svnfilePath){
    	this.svnfilePath = svnfilePath;
    	logList.setSvnfilePath(new HashSet<String>(Arrays.asList(svnfilePath)));
    	this.startRevision = startRevision;
    	this.endRevision = endRevision;
    	this.firstRevision = firstRevision;//不设起始版本
    	this.limit = limit;
    }
  
    public synchronized void go(String tagPath) throws SVNException{
    	System.out.println(tagPath);
    	logList.setTagPath(tagPath);
        SVNLogClient logClient = manager.getLogClient();
        String path = value("svnUrl")+tagPath;
//        System.out.println(svnfilePath);
        System.out.println(path);
        logClient.doLog(SVNURL.parseURIEncoded(path), svnfilePath, null, SVNRevision.create(startRevision), SVNRevision.create(endRevision), true, true, limit,logList);
    }

	public SVNLogHandler getLogList() {
		return logList;
	}

	public void setLogList(SVNLogHandler logList) {
		this.logList = logList;
	}  
}
