package svn;

import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;

public class DirEntryHandler implements ISVNDirEntryHandler {

	public void handleDirEntry(SVNDirEntry arg0) throws SVNException {
		System.out.println(arg0+"/"+SVNDirEntry.DIRENT_KIND);
	}
}
