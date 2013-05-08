package cz.muni.fi.pv243.et.dao;

import cz.muni.fi.pv243.et.model.Person;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

@Stateless
public class PersonDAOImpl implements PersonDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Person load(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        return em.find(Person.class, id);
    }

    @Override
    public void create(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("person is null");
        }
        if (person.getId() != null) {
            throw new IllegalArgumentException("person id is not null");
        }
        em.persist(person);
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

    @Override
    public Collection<Person> findAll() {
        FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        // match all
        final QueryBuilder qb = getQueryBuilder(ftem);
        final Query query = qb.all().createQuery();
        //
        FullTextQuery ftq = ftem.createFullTextQuery(query, Person.class);
        ftq.initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID);

        return ftq.getResultList();
    }

    @Override
    public Collection<Person> findByEmail(String email) {
        FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        //
        final QueryBuilder qb = getQueryBuilder(ftem);
        final Query query = qb.keyword().onField("email").matching(email).createQuery();

        return ftem
                .createFullTextQuery(query, Person.class)
                .initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID)
                .getResultList();
    }

    private QueryBuilder getQueryBuilder(FullTextEntityManager ftem) {
        return ftem.getSearchFactory().buildQueryBuilder().forEntity(Person.class).get();
    }
}
