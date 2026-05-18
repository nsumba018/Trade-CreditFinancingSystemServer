/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rw.rab.service.implementation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import rw.rab.dao.InvestorDao;
import rw.rab.model.Investor;
import rw.rab.model.User;
import rw.rab.service.InvestorService;

/**
 *
 * @author nsumba
 */
public class InvestorServiceImpl extends UnicastRemoteObject implements InvestorService{
    
    InvestorDao dao = new InvestorDao();
    
    public InvestorServiceImpl() throws RemoteException{
        super();
    }
            

    @Override
    public String createInvestor(Investor investor) throws RemoteException {
        return dao.createInvestor(investor); 
    }

    @Override
    public String updateInvestor(Investor investor) throws RemoteException {
        return dao.updateInvestor(investor); 
    }

    @Override
    public String deleteInvestor(Investor investor) throws RemoteException {
        return dao.deleteInvestor(investor); 
    }

    @Override
    public List<Investor> getAllInvestors() throws RemoteException {
        return dao.getAllInvestors(); 
    }

    @Override
    public Investor getInvestorById(Investor investor) throws RemoteException {
        return dao.getInvestorById(investor);
    }

    @Override
    public Investor getInvestorByUserId(User user) throws RemoteException {
        return dao.getInvestorByUserId(user);
    }

}
