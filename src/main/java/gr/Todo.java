package gr;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.qute.TemplateData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import org.hibernate.annotations.TenantId;

import java.util.List;

@Entity
@TemplateData
public class Todo extends PanacheEntity {

    public static String TID = "0a82c0ed-8536-4af6-ac12-ec5bf78a6dfd";

    public String title;

    @TenantId
    @Column(name = "TID")
    public String tid;

    @Version
    @Column(name = "version", nullable = false)
    public Long version;

    public static List<Todo> todos() {
        return Todo.findAll().list();
    }

}
