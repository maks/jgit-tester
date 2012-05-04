import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;


public class SimpleCredentialsProvider extends CredentialsProvider {
    
    private final String username;
    private final String password;
    
    public SimpleCredentialsProvider(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean get(URIish uri, CredentialItem... items)
                    throws UnsupportedCredentialItem {
        
        for (CredentialItem ci : items) {
            if (ci instanceof CredentialItem.YesNoType) {
                // ignore these
            } else if (ci instanceof CredentialItem.StringType) {
                if (ci instanceof CredentialItem.Username) {
                    ((CredentialItem.StringType) ci).setValue(username);
                }
                if (ci instanceof CredentialItem.Password) {
                    ((CredentialItem.StringType) ci).setValue(password);
                }
            }
        }
        return true; // support everything
    }
    
    @Override
    public boolean isInteractive() {
        return false;
    }
    
    @Override
    public boolean supports(CredentialItem... items) {
        return true; // support everything
    }
}
