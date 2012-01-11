import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;


public class GitTester {
	
	public static void main(String[] args) throws IOException {
		File PATH = new File("/tmp/testrepo");
		String URL = "https://maks@github.com/maks/sandbox.git";
		
		Repository repository = new FileRepository("/tmp/testrepo/.git");
		if (args[0].equalsIgnoreCase("c")) {
			cloneRepo(PATH, URL);
		}
		if (args[0].equalsIgnoreCase("p")) {
			pullRepo(repository);
		} else {
			System.err.println("Invalid option:"+args[0]);
		}
	}
	
	private static void cloneRepo(File path, String url) {
		              
		CloneCommand clone = Git.cloneRepository();
		clone.setBare(false);
		clone.setCloneAllBranches(false);		
		clone.setDirectory(path).setURI(url);		
		clone.call();   
	}
	
	private static void pullRepo(Repository repo) {
		Git git = new Git(repo);
		
		PullCommand pull = git.pull();
		try {
			pull.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
