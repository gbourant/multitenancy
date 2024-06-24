package gr;

import io.quarkiverse.renarde.Controller;
import io.quarkiverse.renarde.security.LoginPage;
import io.quarkus.qute.Qute;
import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.util.Set;

@Path("")
public class LoginResource extends Controller {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String jwtIssuer;

    @GET
    @LoginPage
    @Produces(MediaType.TEXT_HTML)
    public String login() {
        return Qute.fmt("""
                <form action="{uri:RenardeOidcController.loginUsingOidc('google')}" method="GET">
                  <input type="submit" value="Login with Google (q_session_google cookie)">
                </form>
                <form action="/login" method="POST">
                  <input type="submit" value="Login with JWT (QuarkusUser cookie)">
                </form>
                """).render();
    }

    @POST
    @Path("login")
    public Response loginForm(){
        String jwt = Jwt.issuer(jwtIssuer)
                .upn("root")
                .groups(Set.of("ROOT"))
                .claim("tid", Todo.TID)
                .innerSign().encrypt();
        return Response
                .seeOther(URI.create("/todo"))
                .cookie(new NewCookie("QuarkusUser",jwt)).build();
    }
}
