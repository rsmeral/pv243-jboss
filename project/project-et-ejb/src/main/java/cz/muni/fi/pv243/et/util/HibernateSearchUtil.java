package cz.muni.fi.pv243.et.util;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

public abstract class HibernateSearchUtil {

    // Is it possible to create as Bean? More Java EE stylish...
    public static QueryBuilder getQueryBuilder(FullTextEntityManager ftem, Class clazz) {
        return ftem.getSearchFactory().buildQueryBuilder().forEntity(clazz).get();
    }
}
