package gr;

import io.quarkiverse.renarde.oidc.RenardeOidcHandler;
import io.quarkiverse.renarde.util.RedirectException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@ApplicationScoped
public class OidcHandler implements RenardeOidcHandler {
    @Override
    public void oidcSuccess(String tenantId, String authId) {
        System.out.println("oidc success");
        throw new RedirectException(Response.seeOther(URI.create("/todo")).build());
    }

    @Override
    public void loginWithOidcSession(String tenantId, String authId) {
        System.out.println("loginWithOidcSession");
        throw new RedirectException(Response.seeOther(URI.create("/todo")).build());
    }
}
