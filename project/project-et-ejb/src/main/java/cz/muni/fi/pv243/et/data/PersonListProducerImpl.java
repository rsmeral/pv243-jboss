package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Person;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;


public class PersonListProducerImpl implements PersonListProducer {

    @Inject
    private EntityManager em;

    @Override
    public Person getPerson(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        return em.find(Person.class, id);
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
        final Query query = qb.keyword().onFields("email").matching(email).createQuery();

        return ftem
                .createFullTextQuery(query, Person.class)
                .initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID)
                .getResultList();
    }

    @Override
    public Collection<Person> findByName(String firstName, String lastName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }



    private QueryBuilder getQueryBuilder(FullTextEntityManager ftem) {
        return ftem.getSearchFactory().buildQueryBuilder().forEntity(Person.class).get();
    }
}
