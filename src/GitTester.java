import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.transport.JschConfigSessionFactory;

import com.jcraft.jsch.UserInfo;


public class GitTester {
	
	public static void main(String[] args) throws IOException {
		File PATH = new File("/tmp/testrepo");
		
		if (args.length < 2) {
			System.out.println("need a param of: c, p, ac");
			return;
		}

		String URL = args[1];
		Repository repository = new FileRepository("/tmp/testrepo/.git");

		if (args[0].equalsIgnoreCase("ac")) {
			authenticate(new File("/home/maks/.ssh/maksgithub_id_rsa"));
			cloneRepo(PATH, URL);
		} else if (args[0].equalsIgnoreCase("c")) {
			cloneRepo(PATH, URL);
		} else if (args[0].equalsIgnoreCase("p")) {
			pullRepo(repository);
		} else if (args[0].equalsIgnoreCase("h")) {
			getHead(repository);
		} else {
			System.err.println("Invalid option:"+args[0]);
		}
	}
	
	private static void authenticate(File prvKeyFile) {
			UserInfo userInfo = new BasicUserInfo();
			GitTesterConfigSessionFactory sshFactory = new GitTesterConfigSessionFactory(
					userInfo, prvKeyFile.getAbsolutePath());
			JschConfigSessionFactory.setInstance(sshFactory);
	}

	private static void getHead(Repository repository) throws IOException {
		System.out.println(repository.getRef("master").getObjectId().getName());
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
