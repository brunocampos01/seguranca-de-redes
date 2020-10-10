package testekeystore;

import java.security.Security;

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

public class Setup
{
    public static void installProvider()
    {
        int addProvider = Security.addProvider(new BouncyCastleFipsProvider());
    }
}
