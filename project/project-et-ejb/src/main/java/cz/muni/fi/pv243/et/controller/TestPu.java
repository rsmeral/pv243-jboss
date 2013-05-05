package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.model.Person;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;


@Singleton
@Startup
public class TestPu {

    private EntityManager em;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("et-pu");
//    private TransactionManager tm = getTransactionManager();

    TransactionManager transactionManager = (TransactionManager) com.arjuna.ats.jta.TransactionManager.transactionManager();
    
    
//    public static TransactionManager getTransactionManager() throws Exception  {        
//       Class<?> tmClass = getClass().getClassLoader().loadClass(JBOSS_TM_CLASS_NAME);
//        return (TransactionManager) tmClass.getMethod("transactionManager").invoke(null);
//    }

    @PostConstruct
    public void addPerson() {
        try {
            transactionManager.begin();
        } catch (NotSupportedException ex) {
            Logger.getLogger(TestPu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(TestPu.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        
        
        Person person = new Person();

        person.setFirstName("Test");
        person.setLastName("Test");
        person.setEmail("test@test.com");
        person.setBankAccount("123456789");


        em = emf.createEntityManager();

        em.persist(person);
        em.flush();         

        Person person2 = em.find(Person.class, person.getId());

        try {
            transactionManager.commit();
        } catch (RollbackException ex) {
            Logger.getLogger(TestPu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(TestPu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(TestPu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(TestPu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(TestPu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(TestPu.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        
        System.out.println(person2);

        em.close();
    }

}
