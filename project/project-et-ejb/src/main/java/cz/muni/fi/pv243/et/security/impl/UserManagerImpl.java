package cz.muni.fi.pv243.et.security.impl;

import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.data.PersonRepository;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.model.UserModel;
import cz.muni.fi.pv243.et.security.EventLog;
import cz.muni.fi.pv243.et.security.UserManager;
import cz.muni.fi.pv243.et.service.PersonService;
import org.jboss.ejb3.annotation.Clustered;import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.internal.Password;
import org.picketlink.idm.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;

@Clustered
@Stateless
public class UserManagerImpl implements UserManager, Serializable {

    @Inject
    private EventLog log;

    @Inject
    private IdentityManager identityManager;

    @Inject
    private PersonService personService;

    @Override
    public void add(UserModel model) {
        Person person = new Person();
        person.setId(model.getId());
        person.setBankAccount(model.getBankAccount());
        person.setEmail(model.getEmail());
        person.setFirstName(model.getFirstName());
        person.setLastName(model.getLastName());

        this.personService.save(person);
        model.setId(person.getId());

        log.logPersonCreated(person.getId());

        User user = new SimpleUser(model.getUserName());
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setEmail(model.getEmail());
        user.setAttribute(new Attribute<String>("personId", person.getId().toString()));

        this.identityManager.add(user);
        model.setIdentityId(user.getId());
    }

    @Override
    public void update(UserModel model) {
        Person person = new Person();
        person.setId(model.getId());
        person.setBankAccount(model.getBankAccount());
        person.setEmail(model.getEmail());
        person.setFirstName(model.getFirstName());
        person.setLastName(model.getLastName());

        this.personService.save(person);

        log.logPersonCreated(person.getId());

        User user;
        Collection<User> users = identityManager.createIdentityQuery(User.class).setParameter(User.ID, model.getIdentityId()).getResultList();
        if (users.isEmpty()) {
            throw new RuntimeException("shouldn't be empty");
        } else {
            user = users.iterator().next();
        }
        user.setLoginName(model.getUserName());
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setEmail(model.getEmail());
        user.setAttribute(new Attribute<String>("personId", person.getId().toString()));

        this.identityManager.update(user);
    }

    @Override
    public void remove(String username) {
        List<User> resultList = identityManager.createIdentityQuery(User.class).setParameter(Agent.LOGIN_NAME, username).getResultList();
        User u = resultList.get(0);
        u.setEnabled(false);
        identityManager.update(u);
    }

    @Override
    public void grantRole(String username, PersonRole role) {
        User u = this.getUser(username);
        if (u == null) {
            throw new IllegalArgumentException("user with given username (" + username + ") doesn't exist");
        }
        Role plRole = this.identityManager.getRole(role.toString());

        if (plRole == null) {
            plRole = new SimpleRole(role.toString());
            this.identityManager.add(plRole);
        }

        this.identityManager.grantRole(u, plRole);
    }

    @Override
    public void revokeRole(String username, PersonRole role) {
        User u = this.getUser(username);
        if (u == null) {
            throw new IllegalArgumentException("user with given username (" + username + ") doesn't exist");
        }
        Role plRole = this.identityManager.getRole(role.toString());

        if (plRole == null) {
            plRole = new SimpleRole(role.toString());
            this.identityManager.add(plRole);
        }

        this.identityManager.revokeRole(u, plRole);
    }

    @Override
    public void changePassword(String username, String password) {
        Password passwd = new Password(password.toCharArray());

        final User user = getUser(username);

        this.identityManager.updateCredential(user, passwd);
    }

    @Override
    public boolean hasRole(String username, PersonRole role) {
        if (username == null || role == null) {
            throw new IllegalArgumentException();
        }

        final User user = getUser(username);
        final Role sRole = this.identityManager.getRole(role.toString());
        if (sRole == null) {
            return false;
        }

        return this.identityManager.hasRole(user, sRole);
    }

    @Override
    public UserModel get(Long id) {
        throw new UnsupportedOperationException("implement");
    }

    @Override
    public UserModel get(String username) {
        User u = this.getUser(username);
        if (u == null) {
            return null;
        }
        Person p = personService.get(Long.valueOf(u.<String>getAttribute("personId").getValue()));
        if (p == null) {
            return null;
        }

        return toUserModel(p, u);
    }

    @Override
    public Collection<UserModel> findByEmail(String email) {
        List<User> resultList = this.identityManager
                .createIdentityQuery(User.class)
                .setParameter(User.EMAIL, email)
                .getResultList();
        Person p = this.personService.findByEmail(email);
        if (p == null) {
            return new ArrayList<UserModel>();
        }
        return join(resultList, Arrays.asList(new Person[]{p}));
    }

    @Override
    public Collection<UserModel> findAll() {
        Collection<Person> all = personService.findAll();
        List<User> users = identityManager.createIdentityQuery(User.class).getResultList();

        return join(users, all);
    }

    private UserModel toUserModel(Person p, User u) {
        UserModel um = new UserModel();
        um.setId(p.getId());
        um.setUserName(u.getLoginName());
        um.setFirstName(p.getFirstName());
        um.setLastName(p.getLastName());
        um.setEmail(p.getEmail());
        um.setBankAccount(p.getBankAccount());
        um.setIdentityId(u.getId());

        return um;
    }

    private Collection<UserModel> join(List<User> users, Collection<Person> people) {
        Collection<UserModel> allPersons = new ArrayList<UserModel>();
        Map<Long, Person> personMap = new HashMap<Long, Person>();
        for (Person p : people) {
            personMap.put(p.getId(), p);
        }
        for (User u : users) {
            Attribute<String> personId = u.<String>getAttribute("personId");
            Person p = personMap.get(Long.valueOf(personId.getValue()));
            if (p != null) {
                allPersons.add(toUserModel(p, u));
            }
        }
        return allPersons;
    }

    private User getUser(String username) {
        List<User> resultList = identityManager.createIdentityQuery(User.class).setParameter(Agent.LOGIN_NAME, username).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        User u = resultList.get(0);

        return u;
    }
}
