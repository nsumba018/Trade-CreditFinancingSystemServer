/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rw.rab.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import rw.rab.model.Funding;
import rw.rab.model.Investor;

/**
 *
 * @author nsumba
 */
public interface FundingService extends Remote{
    public String createFunding(Funding funding) throws RemoteException;
    public String updateFunding(Funding funding) throws RemoteException;
    public String deleteFunding(Funding funding) throws RemoteException;
    public List<Funding> getAllFundings() throws RemoteException;
    public Funding getFundingById(Funding funding) throws RemoteException;
    public List<Funding> getFundingsByInvestorId(Investor investor) throws RemoteException;
}
