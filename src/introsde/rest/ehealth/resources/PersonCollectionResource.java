package introsde.rest.ehealth.resources;
import introsde.rest.ehealth.model.Person;
import introsde.rest.ehealth.model.HealthMeasureHistory;
import introsde.rest.ehealth.model.HealthProfile;


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
@Path("/person")
public class PersonCollectionResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // will work only inside a Java EE application
    @PersistenceUnit(unitName="introsde-jpa")
    EntityManager entityManager;

    // will work only inside a Java EE application
    @PersistenceContext(unitName = "introsde-jpa",type=PersistenceContextType.TRANSACTION)
    private EntityManagerFactory entityManagerFactory;

    // Return the list of people to the user in the browser
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<Person> getPersonsBrowser() {
        System.out.println("Getting list of people...");
        List<Person> people = Person.getAll();
        System.out.println(people);
        return people;
    }

    // retuns the number of people
    // to get the total number of records
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        System.out.println("Getting count...");
        List<Person> people = Person.getAll();
        int count = people.size();
        return String.valueOf(count);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Person newPerson(Person person) throws IOException {
        System.out.println("Creating new person...123");
        System.out.println("Person in resources: " + person);
        Person p = new Person();
        Person p_old = new Person();

        try {
            p = p_old.savePerson(person);
        } catch (Exception e){
            System.out.println("ERROR!!!! --> " + e);
        }
        return p;
    }
    

    // Defines that the next path parameter after the base url is
    // treated as a parameter and passed to the PersonResources
    // Allows to type http://localhost:599/base_url/1
    // 1 will be treaded as parameter todo and passed to PersonResource
    @Path("{personId}")
    public PersonResource getPerson(@PathParam("personId") int id) {
        return new PersonResource(uriInfo, request, id);
    }

    // Defines that the next path parameter after the base url is
    // treated as a parameter and passed to the PersonMeasureHistoryResource
    // Allows to type http://localhost:599/base_url/1/weight
    // 1, weight will be treaded as parameters todo and passed to PersonMeasureHistoryResource
    @Path("{personId}/{measureType}")
    public PersonMeasureHistoryResource getPerson(@PathParam("personId") int id, @PathParam("measureType") String name) {
        return new PersonMeasureHistoryResource(uriInfo, request, id, name);
    }

    // Defines that the next path parameter after the base url is
    // treated as a parameter and passed to the PersonMeasureHistoryResourceById
    // Allows to type http://localhost:599/base_url/1/weight/2
    // 1, weight and 2 will be treaded as parameters todo and passed to PersonMeasureHistoryResourceById
    @Path("{personId}/{measureType}/{mid}")
    public PersonMeasureHistoryResourceById getPerson(@PathParam("personId") int id, @PathParam("measureType") String name, @PathParam("mid") int mid) {
        return new PersonMeasureHistoryResourceById(uriInfo, request, id, name, mid);
    }
}
















