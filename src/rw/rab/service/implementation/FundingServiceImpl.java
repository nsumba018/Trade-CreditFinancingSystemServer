/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rw.rab.service.implementation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import rw.rab.dao.FundingDao;
import rw.rab.model.Funding;
import rw.rab.model.Investor;
import rw.rab.service.FundingService;

/**
 *
 * @author nsumba
 */
public class FundingServiceImpl extends UnicastRemoteObject implements FundingService{
    
    FundingDao dao = new FundingDao();
    
    public FundingServiceImpl() throws RemoteException{
        super();
    }
    
    @Override
    public String createFunding(Funding funding) throws RemoteException {
        return dao.createFunding(funding); 
    }

    @Override
    public String updateFunding(Funding funding) throws RemoteException {
        return dao.updateFunding(funding); 
    }

    @Override
    public String deleteFunding(Funding funding) throws RemoteException {
        return dao.deleteFunding(funding); 
    }

    @Override
    public List<Funding> getAllFundings() throws RemoteException {
        return dao.getAllFundings(); 
    }

    @Override
    public Funding getFundingById(Funding funding) throws RemoteException {
        return dao.getFundingById(funding);
    }

    @Override
    public List<Funding> getFundingsByInvestorId(Investor investor) throws RemoteException {
        return dao.getFundingsByInvestorId(investor);
    }

}
