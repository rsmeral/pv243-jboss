package cz.muni.fi.pv243.et.configuration;

import org.apache.deltaspike.core.api.exclude.annotation.Exclude;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.config.IdentityConfiguration;
import org.picketlink.idm.credential.Credentials;
import org.picketlink.idm.model.*;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.query.RelationshipQuery;
import org.picketlink.idm.spi.IdentityStoreInvocationContextFactory;
import org.picketlink.idm.spi.StoreFactory;

import java.util.Date;
import java.util.Map;

@Exclude
public class CustomIdentityManager implements IdentityManager {

    private Map<String, Relationship> relationships;

    @Override
    public void bootstrap(IdentityConfiguration configuration, IdentityStoreInvocationContextFactory contextFactory) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setIdentityStoreFactory(StoreFactory factory) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void add(IdentityType value) {
        IdentityStore store = getIdentityStore(value);
        store.add(value);
    }

    @Override
    public void update(IdentityType value) {
        IdentityStore store = getIdentityStore(value);
        store.update(value);
    }

    @Override
    public void remove(IdentityType value) {
        IdentityStore store = getIdentityStore(value);
        store.remove(value);
    }

    @Override
    public void add(Relationship value) {
        relationships.put(value.getId(), value);
    }

    @Override
    public void update(Relationship value) {
        relationships.put(value.getId(), value);
    }

    @Override
    public void remove(Relationship value) {
        relationships.remove(value.getId());
    }

    @Override
    public Agent getAgent(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User getUser(String id) {
        return users.find(id);
    }

    @Override
    public Group getGroup(String groupPath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Group getGroup(String groupName, Group parent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isMember(IdentityType identityType, Group group) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addToGroup(IdentityType identityType, Group group) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeFromGroup(IdentityType identityType, Group group) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Role getRole(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasGroupRole(IdentityType identityType, Role role, Group group) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void grantGroupRole(IdentityType identityType, Role role, Group group) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void revokeGroupRole(IdentityType identityType, Role role, Group group) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasRole(IdentityType identityType, Role role) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void grantRole(IdentityType identityType, Role role) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void revokeRole(IdentityType identityType, Role role) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T extends IdentityType> T lookupIdentityById(Class<T> identityType, String value) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T extends IdentityType> IdentityQuery<T> createIdentityQuery(Class<T> identityType) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T extends Relationship> RelationshipQuery<T> createRelationshipQuery(Class<T> relationshipType) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void validateCredentials(Credentials credentials) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateCredential(Agent agent, Object value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateCredential(Agent agent, Object value, Date effectiveDate, Date expiryDate) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void loadAttribute(IdentityType identityType, String attributeName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void createRealm(Realm realm) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeRealm(Realm realm) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Realm getRealm(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void createTier(Tier tier) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeTier(Tier tier) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Tier getTier(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IdentityManager forRealm(Realm realm) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IdentityManager forTier(Tier tier) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private IdentityStore<User> users = new IdentityStore<User>();
    private IdentityStore<Group> groups = new IdentityStore<Group>();
    private IdentityStore<Role> roles = new IdentityStore<Role>();

    private IdentityStore getIdentityStore(IdentityType type) {
        if (User.class.isInstance(type)) {
            return users;
        } else if (Role.class.isInstance(type)) {
            return roles;
        } else if (Group.class.isInstance(type)) {
            return groups;
        }
        throw new IllegalArgumentException("object type not supported!");
    }
}
