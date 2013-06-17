package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.model.UserModel;

import java.util.Collection;

public interface UserManager {
    void add(UserModel model);
    void update(UserModel model);
    void remove(String username);
    void grantRole(String username, PersonRole role);
    void revokeRole(String username, PersonRole role);
    UserModel get(Long id);
    UserModel get(String username);
    Collection<UserModel> findByEmail(String email);
    Collection<UserModel> findAll();
}
