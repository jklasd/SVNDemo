package svn;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNChangelistClient;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;


public class SvnService {
	static {  
        DAVRepositoryFactory.setup();  
    }  
  
    private SVNClientManager manager;  
    private SVNURL repositoryBaseUrl;  
  
    public SvnService() {  
        DefaultSVNOptions options = new DefaultSVNOptions();
         manager = SVNClientManager.newInstance(options,"zjb","zjb@szTNF#P2P"); //如果需要用户名密码  
        try {  
            repositoryBaseUrl = SVNURL.parseURIEncoded("svn://192.168.0.243/p2p");// 传入svn地址  
        } catch (SVNException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
  
    public void test() throws SVNException{  
        SVNLogClient logClient = manager.getLogClient();  
//  
//        // svn list  
//        DirEntryHandler handler = new DirEntryHandler(); // 在svn  
//                                                            // co时对每个文件目录的处理，实现ISVNDirEntryHandler接口  
//        logClient.doList(repositoryBaseUrl, SVNRevision.HEAD, SVNRevision.HEAD,false, true, handler); // 列出当前svn地址的目录，对每个文件进行处理  
  
        logClient.doLog(repositoryBaseUrl, new String[]{}, SVNRevision.create(5), SVNRevision.create(516), SVNRevision.create(4672), true, true, 5, new LogHandler());
        // svn co  
//        ChangelistHandler listHandler = new ChangelistHandler(); // svn  co时对每个文件的处理
//        
//        
//        SVNChangelistClient client = manager.getChangelistClient();
//        List<String> changeLog = new ArrayList<String>();
//        File tag = new File("E:/jiaruimei/jiaruimei-YM/jiaruimei/src/jiaruimei/mgt/部署记录.txt");
//        changeLog.add("svn://192.168.0.243/p2p/trunk/code/mgt/部署记录.txt");
//        try {
//        	client.doGetChangeLists(tag, changeLog, SVNDepth.EMPTY, listHandler);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }  
  
    public static void main(String[] args) throws SVNException {  
    	SvnService svntest = new SvnService();  
        svntest.test(); 
        
        System.out.println("-================");
        
        System.out.println("/trunk/code/mgt".replace("/code", ""));
        
    } 
}
