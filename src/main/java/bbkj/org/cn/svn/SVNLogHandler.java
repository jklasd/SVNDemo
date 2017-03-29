package bbkj.org.cn.svn;

import java.util.List;
import java.util.Set;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;

public class SVNLogHandler implements ISVNLogEntryHandler{
	private String tagPath;
	private Set<String> svnfilePath;
	private List<String> files;
	public SVNLogHandler(List<String> files){
		this.files = files;
	}
	public void setTagPath(String tag){
		tagPath = tag;
	}
	
	public void handleLogEntry(SVNLogEntry logEntry) throws SVNException {
		for(SVNLogEntryPath slep:logEntry.getChangedPaths().values()){
			String logPath = slep.getPath();
			if(logPath.startsWith(tagPath+"/") //过滤
					&& "A,M".contains(slep.getType()+"")){
				files.add(logPath.replace(".java", ".class"));
			}
		}
		System.out.println(tagPath+":"+logEntry.getRevision()+"加载完成");
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public Set<String> getSvnfilePath() {
		return svnfilePath;
	}

	public void setSvnfilePath(Set<String> svnfilePath) {
		this.svnfilePath = svnfilePath;
	}

}
