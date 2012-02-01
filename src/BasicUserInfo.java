import com.jcraft.jsch.UserInfo;


public class BasicUserInfo implements UserInfo {

	public BasicUserInfo() {

	}

	@Override
	public String getPassphrase() {
		System.out.println("getPassphrase called");
		return null;
	}

	@Override
	public String getPassword() {
		System.out.println("getPassword called");
		return null;
	}

	@Override
	public boolean promptPassphrase(String arg0) {
		System.out.println("promptPassphrase called");
		return false;
	}

	@Override
	public boolean promptPassword(String arg0) {
		System.out.println("promptPassword called");
		return false;
	}

	@Override
	public boolean promptYesNo(String arg0) {
		System.out.println("promptYesNo called");
		return false;
	}

	@Override
	public void showMessage(String arg0) {
		System.out.println("getPassphrase called");
	}
}
