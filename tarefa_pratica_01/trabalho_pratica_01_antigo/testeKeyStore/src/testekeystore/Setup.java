package testekeystore;

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

import java.security.Security;

public class Setup {
    public static void installProvider() {
        int addProvider = Security.addProvider(
                new BouncyCastleFipsProvider());
    }
}
