/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rw.rab.service.implementation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import rw.rab.dao.UserDao;
import rw.rab.model.User;
import rw.rab.service.UserService;

/**
 *
 * @author nsumba
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService{
    
    UserDao dao = new UserDao();
    
    public UserServiceImpl() throws RemoteException{
        super();
    }

    @Override
    public String createUser(User user) throws RemoteException {
        return dao.createUser(user); 
    }

    @Override
    public String updateUser(User user) throws RemoteException {
        return dao.updateUser(user); 
    }

    @Override
    public String deleteUser(User user) throws RemoteException {
        return dao.deleteUser(user); 
    }

    @Override
    public List<User> getAllUsers() throws RemoteException {
        return dao.getAllUsers(); 
    }

    @Override
    public User getUserById(User user) throws RemoteException {
        return dao.getUserById(user); 
    }

    @Override
    public User login(User user) throws RemoteException {
        return dao.login(user); //To change body of generated methods, choose Tools | Templates.
    }
    
}
