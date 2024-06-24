package gr;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

public class Startup {

    @Transactional
    void init(@Observes StartupEvent ev){
        CustomTenantResolver.threadId.set(Todo.TID);
        Todo todo = new Todo();
        todo.title = "Quarkus is awesome!";
        todo.persistAndFlush();
        CustomTenantResolver.threadId.remove();
    }
}
