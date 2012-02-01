import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.JschConfigSessionFactory;

import com.jcraft.jsch.UserInfo;


public class GitTester {
	
	public static void main(String[] args) throws IOException {
		File PATH = new File("/tmp/testrepo");
		String URL = "https://maks@github.com/maks/sandbox.git";
		
		Repository repository = new FileRepository("/tmp/testrepo/.git");
		if (args[0].equalsIgnoreCase("ac")) {
			authenticatedCloneRepo(new File("/home/maks/.ssh/maksgithub_id_rsa"));
		}
		if (args[0].equalsIgnoreCase("c")) {
			cloneRepo(PATH, URL, null);
		}
		if (args[0].equalsIgnoreCase("p")) {
			pullRepo(repository);
		}
		if (args[0].equalsIgnoreCase("h")) {
			getHead(repository);
		} else {
			System.err.println("Invalid option:"+args[0]);
		}
	}
	
	private static void authenticatedCloneRepo(File privKeyFile) {
		String prvKey = null;
		try {
			prvKey = streamToString(new FileInputStream(privKeyFile));
			UserInfo userInfo = null;
			GitTesterConfigSessionFactorty sshFactory = new GitTesterConfigSessionFactorty(
					userInfo, prvKey);
			JschConfigSessionFactory.setInstance(sshFactory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void getHead(Repository repository) throws IOException {
		System.out.println(repository.getRef("master").getObjectId().getName());
	}

	private static void cloneRepo(File path, String url,
			CredentialsProvider credentialsProvider) {

		CloneCommand clone = Git.cloneRepository();
		clone.setBare(false);
		clone.setCloneAllBranches(false);
		clone.setDirectory(path).setURI(url);

		if (credentialsProvider != null) {
			clone.setCredentialsProvider(credentialsProvider);
		}
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

	public static String streamToString(InputStream inStream)
			throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(inStream));
		StringBuilder total = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
			total.append(line);
		}
		return total.toString();
	}

}
