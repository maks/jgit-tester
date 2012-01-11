import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;


public class GitTester {
	
	public static void main(String[] args) throws IOException {
		File PATH = new File("/tmp/testrepo");
		String url = "https://maks@github.com/maks/sandbox.git";
				
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(PATH).readEnvironment().findGitDir().build();
		
		Git git = new Git(repository);              
		CloneCommand clone = git.cloneRepository();
		clone.setBare(false);
		clone.setCloneAllBranches(false);
		
		clone.setDirectory(PATH).setURI(url);
		
		clone.call();   
	}
}
