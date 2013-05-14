package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.model.Person;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

@Stateless
public class PersonListProducerImpl implements PersonListProducer {

    @Inject
    private EntityManager em;
    @Inject
    private Session session;

    @Override
    public Person getPerson(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return em.find(Person.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Person> findAll() {
       return session.createQuery("select p from Person p").list();
    }

    @Override
    public Person findByEmail(String email) {
        List<Person> result = session.createQuery("SELECT p from Person p where p.email = :email")
                .setParameter("email", email).list();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public Collection<Person> findByName(String firstName, String lastName) {
        List<Person> result = session.createQuery(
                "SELECT p from Person p where p.firstName like :firstName and p.lastName like :lastName")
                .setParameter("firstName", firstName).setParameter("lastName", lastName).list();
        return result;
    }

}
