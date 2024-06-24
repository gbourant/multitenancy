package gr;

import io.quarkus.qute.Qute;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/todo")
@RolesAllowed("ROOT")
public class TodoResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Transactional
    public String todos() {
        System.out.println("Rendering the page");
        return Qute.fmt("""
                TODO RESOURCE <br/>
                {#each gr_Todo:todos}
                  id: {it.id} title: {it.title} tid: {it.tid}
                {#else}
                  No results!
                {/each}
                """).render();
    }
}
