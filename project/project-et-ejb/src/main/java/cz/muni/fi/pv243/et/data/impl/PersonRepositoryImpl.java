package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PersonRepository;
import cz.muni.fi.pv243.et.model.Person;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;

@Stateless
public class PersonRepositoryImpl implements PersonRepository {

    @Inject
    private EntityManager em;

    @Override
    public void create(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("person is null");
        }
        if (person.getId() != null) {
            throw new IllegalArgumentException("person id is not null");
        }
        em.persist(person);
//        em.flush();
        System.out.println("persons flushed");
    }

    @Override
    public void update(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("person is null");
        }
        if (person.getId() == null) {
            throw new IllegalArgumentException("person id is null");
        }
        em.merge(person);
    }

    @Override
    public void remove(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("person is null");
        }
        if (person.getId() == null) {
            throw new IllegalArgumentException("person id is null");
        }
        em.remove(em.find(Person.class, person.getId()));
    }


}
