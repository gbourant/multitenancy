package gr;

import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.security.Principal;

@RequestScoped
@PersistenceUnitExtension
public class CustomTenantResolver implements TenantResolver {

    public static final ThreadLocal<String> threadId = new ThreadLocal<>();

    @Inject
    SecurityIdentity securityIdentity;

    @Override
    public String getDefaultTenantId() {
        System.out.println("getDefaultTenantId");
        var uuid = threadId.get();
        Principal principal = securityIdentity.getPrincipal();
        if (principal instanceof DefaultJWTCallerPrincipal) {
            var tid = ((DefaultJWTCallerPrincipal) principal).getClaim("tid");
            if (tid != null) {
                return tid.toString();
            }
        }
        return uuid == null ? "base" : uuid;
    }

    @Override
    public String resolveTenantId() {
        return getDefaultTenantId();
    }
}
