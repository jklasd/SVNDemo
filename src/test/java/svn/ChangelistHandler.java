package svn;

import java.io.File;

import org.tmatesoft.svn.core.wc.ISVNChangelistHandler;

public class ChangelistHandler implements ISVNChangelistHandler{

	public void handle(File arg0, String arg1) {
		System.out.println(arg1);
		System.out.println(arg0);
	}
}
