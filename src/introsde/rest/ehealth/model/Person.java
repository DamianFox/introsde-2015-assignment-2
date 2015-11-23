package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;
import introsde.rest.ehealth.model.HealthProfile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
@Entity  // indicates that this class is an entity to persist in DB
@Table(name="Person") // to whole table must be persisted 
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
//@XmlType(propOrder = {"idPerson", "firstname", "lastname", "birthdate", "HealthProfile"})
@XmlRootElement
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
        pkColumnName="name", valueColumnName="seq",
        pkColumnValue="Person")
    @Column(name="idPerson")
    private int idPerson;
    @Column(name="lastname")
    private String lastname;
    @Column(name="firstname")
    private String firstname;
    @Column(name="birthdate")
    private String birthdate;
    
    // mappedBy must be equal to the name of the attribute in MeasureType that maps this relation
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<HealthProfile> healthProfile;
    
    @XmlElementWrapper(name = "healthProfiles")
    public List<HealthProfile> getHealthProfile() {
        return healthProfile;
    }
    // add below all the getters and setters of all the private attributes
    
    // getters
    public int getIdPerson(){
        return idPerson;
    }

    public String getLastname(){
        return lastname;
    }
    public String getFirstname(){
        return firstname;
    }
    public String getBirthdate(){
        return this.birthdate;
    }
    
    // setters
    public void setIdPerson(int idPerson){
        this.idPerson = idPerson;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public void setBirthdate(String birthdate){
        this.birthdate = birthdate;
    }
    public void setHealthProfile(List<HealthProfile> healthProfile) {
        this.healthProfile = healthProfile;
    }

    public void addPersonToHealthProfile(){
        for(int i=0; i<healthProfile.size(); i++){
            this.healthProfile.get(i).setPerson(this);
        }
    }
    
    public static Person getPersonById(int personId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static List<Person> getAll() {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    public Person savePerson(Person p) {
        if(p.healthProfile != null){
            p.addPersonToHealthProfile();
        }
        /*if(p.birthdate != null){
            this.setBirthdate(p.birthdate);
        }
        if(p.lastname != null){
            this.setLastname(p.lastname);
        }
        if(p.firstname != null){
            this.setFirstname(p.firstname);
        }*/
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    } 

    public Person updatePerson(Person p) {
        if(p.birthdate != null){
            this.setBirthdate(p.birthdate);
        }
        if(p.lastname != null){
            this.setLastname(p.lastname);
        }
        if(p.firstname != null){
            this.setFirstname(p.firstname);
        }


        EntityManager em = LifeCoachDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(this);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }
    
}