/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rw.rab.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import rw.rab.model.Investor;
import rw.rab.model.User;

/**
 *
 * @author nsumba
 */
public interface InvestorService extends Remote{
    public String createInvestor(Investor investor) throws RemoteException;
    public String updateInvestor(Investor investor) throws RemoteException;
    public String deleteInvestor(Investor investor) throws RemoteException;
    public List<Investor> getAllInvestors() throws RemoteException;
    public Investor getInvestorById(Investor investor) throws RemoteException;
    public Investor getInvestorByUserId(User user) throws RemoteException;
}
