package introsde.rest.ehealth.resources;
import introsde.rest.ehealth.model.Person;
import introsde.rest.ehealth.model.HealthMeasureHistory;


import java.io.IOException;
import java.lang.Exception;
import java.util.List;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
public class PersonMeasureHistoryResourceById {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id, mid;
    String measureType;

    // will work only inside a Java EE application
    @PersistenceUnit(unitName="introsde-jpa")
    EntityManager entityManager;

    // will work only inside a Java EE application
    @PersistenceContext(unitName = "introsde-jpa",type=PersistenceContextType.TRANSACTION)
    private EntityManagerFactory entityManagerFactory;

    public PersonMeasureHistoryResourceById(UriInfo uriInfo, Request request, int id, String measureType, int mid, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.entityManager = em;
        this.id = id;
        this.measureType = measureType;
        this.mid = mid;
    }

    public PersonMeasureHistoryResourceById(UriInfo uriInfo, Request request, int id, String measureType, int mid) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.measureType = measureType;
        this.mid = mid;
    }

    // Getting all health measure history by person id, meaasure type and measure type id
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<HealthMeasureHistory> getAll() {
        List<HealthMeasureHistory> measureHistories = HealthMeasureHistory.getMeasureHistoryById(id, measureType, mid);
        if (measureHistories == null)
            throw new RuntimeException("Get: HealthMeasureHistory with " + id + " not found");
        return measureHistories;
    }
}
















