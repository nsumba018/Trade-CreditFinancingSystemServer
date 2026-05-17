/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rw.rab.service.implementation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import rw.rab.dao.SmeDao;
import rw.rab.model.Sme;
import rw.rab.model.User;
import rw.rab.service.SmeService;

/**
 *
 * @author nsumba
 */
public class SmeServiceImpl extends UnicastRemoteObject implements SmeService{
    
    SmeDao dao = new SmeDao();
    public SmeServiceImpl() throws RemoteException{
        super();
    }

    @Override
    public String createSme(Sme sme) throws RemoteException {
        return dao.createSme(sme); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String updateSme(Sme sme) throws RemoteException {
        return dao.updateSme(sme); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String deleteSme(Sme sme) throws RemoteException {
        return dao.deleteSme(sme); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sme> getAllSmes() throws RemoteException {
        return dao.getAllSmes(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sme getSmeById(Sme sme) throws RemoteException {
        return dao.getSmeById(sme); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Sme getSmeByUserId(User user) throws RemoteException {
        return dao.getSmeByUserId(user);
    }
    
}
