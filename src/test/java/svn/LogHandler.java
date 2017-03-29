package svn;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;

public class LogHandler implements ISVNLogEntryHandler{

	public void handleLogEntry(SVNLogEntry logEntry) throws SVNException {
		System.out.println(logEntry.getChangedPaths());
//		for(SVNLogEntryPath slep:logEntry.getChangedPaths().values()){
//			if("A,M".contains(slep.getType()+"")){
//				System.out.println(logEntry.getRevision()+"==="+slep.getPath());
//			}
//		}
	}
}
