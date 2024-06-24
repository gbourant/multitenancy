package gr;

import io.quarkus.oidc.runtime.OidcUtils;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;

@ApplicationScoped
public class Augmentor implements SecurityIdentityAugmentor {

    @Override
    @ActivateRequestContext
    public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context) {
        String tenantId = identity.getAttribute(OidcUtils.TENANT_ID_ATTRIBUTE);
        if (identity.isAnonymous() || tenantId == null) return Uni.createFrom().item(identity);

        DefaultJWTCallerPrincipal oldPrincipal = (DefaultJWTCallerPrincipal) identity.getPrincipal();
        String rawToken = oldPrincipal.getClaim(Claims.raw_token);
        JwtClaims claims = new JwtClaims();

        for (String claim : oldPrincipal.getClaimNames()) {
            claims.setClaim(claim, oldPrincipal.getClaim(claim));
        }

        Uni<SecurityIdentity> securityIdentityUni = context.runBlocking(() -> {
            System.out.println("trying to find TODO");
            claims.setClaim("tid", ((Todo) Todo.findById(1)).tid);
//        claims.setClaim("tid", Todo.TID);
            QuarkusSecurityIdentity newIdentity = QuarkusSecurityIdentity.builder(identity)
                    .addRole("ROOT")
                    .setPrincipal(new DefaultJWTCallerPrincipal(rawToken, "JWT", claims))
                    .build();

            return newIdentity;
        });

        return securityIdentityUni;
    }


}
