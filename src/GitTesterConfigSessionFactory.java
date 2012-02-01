import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;


public class GitTesterConfigSessionFactory extends JschConfigSessionFactory {

	private final UserInfo userInfo;
	private String prvkey;

	public GitTesterConfigSessionFactory(UserInfo userInfo, String prvKey) {
		this.userInfo = userInfo;
		this.prvkey = prvKey;
	}

	@Override
	protected void configure(Host host, Session session) {
		session.setConfig("StrictHostKeyChecking", "no"); // DON'T let the
															// hostKeyRepository
															// ask the questions
		session.setUserInfo(userInfo);

	}

	@Override
	protected JSch createDefaultJSch(FS fs) throws JSchException {
		final JSch jsch = new JSch();
		jsch.addIdentity(prvkey);
		return jsch;
	}

}
