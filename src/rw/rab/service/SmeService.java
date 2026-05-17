/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rw.rab.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import rw.rab.model.Sme;
import rw.rab.model.User;

/**
 *
 * @author nsumba
 */
public interface SmeService extends Remote{
    public String createSme(Sme sme) throws RemoteException;
    public String updateSme(Sme sme) throws RemoteException;
    public String deleteSme(Sme sme) throws RemoteException;
    public List<Sme> getAllSmes() throws RemoteException;
    public Sme getSmeById(Sme sme) throws RemoteException;
    public Sme getSmeByUserId(User user) throws RemoteException;
}
