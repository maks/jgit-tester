import java.io.File;
import java.io.IOException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.transport.JschConfigSessionFactory;

import com.jcraft.jsch.UserInfo;


public class GitTester {
	
    private static SimpleCredentialsProvider creds;

	public static void main(String[] args) throws IOException {
        OptionParser parser = new OptionParser("a:r:u:s:o:cph");
        OptionSet options = parser.parse(args);

        File localGitPath = null;
        
        if (options.has("o")) {
            localGitPath = new File(options.valueOf("o").toString());
        } else {
            System.err.println("Missing local repo path with -o param");
            System.exit(1);
        }

        Repository repository = new FileRepository(
                        new File(localGitPath, ".git"));

        String URL = null;
        if (options.has("r")) {
            URL = options.valueOf("r").toString();
        } else {
            System.err.println("Missing URLish");
            System.exit(1);
        }

        if (options.has("a")) {
            authenticate(new File(options.valueOf("a").toString()));
            // eg "/home/user/.ssh/id_rsa"
        }
        if (options.has("u") || options.has("s")) {
            creds = new SimpleCredentialsProvider(options.valueOf("u")
                            .toString(), options.valueOf("s").toString());
        }
        
        // Action
        if (options.has("c")) {
            cloneRepo(localGitPath, URL);
        } else if (options.has("p")) {
            pullRepo(repository);
        } else if (options.has("h")) {
            getHead(repository);
        } else {
            System.err.println("Invalid options");
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
        
        if (creds != null) {
            clone.setCredentialsProvider(creds);
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

}
