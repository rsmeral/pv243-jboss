package cz.muni.fi.pv243.et.service;

import org.picketlink.authentication.AuthenticationException;

public interface LoginService {
    
   void login(String userName, String password) throws AuthenticationException;
    
}
