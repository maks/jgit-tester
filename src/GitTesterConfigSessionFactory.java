import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;


public class GitTesterConfigSessionFactory extends JschConfigSessionFactory {

	private final UserInfo userInfo;
	private String prvkeyPath;

	public GitTesterConfigSessionFactory(UserInfo userInfo, String prvKeyPath) {
		this.userInfo = userInfo;
		this.prvkeyPath = prvKeyPath;
	}

	@Override
	protected void configure(Host host, Session session) {
		// DON'T let the hostKeyRepository ask the questions
		session.setConfig("StrictHostKeyChecking", "no");
		session.setUserInfo(userInfo);
	}

	@Override
	protected JSch createDefaultJSch(FS fs) throws JSchException {
		final JSch jsch = new JSch();
		jsch.addIdentity(prvkeyPath);
		return jsch;
	}
}
