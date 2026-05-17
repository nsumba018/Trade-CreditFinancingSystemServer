/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rw.rab.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rw.rab.model.Sme;
import rw.rab.model.User;

/**
 *
 * @author nsumba
 */
public class SmeDao {
    public String createSme(Sme sme){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.save(sme);
        tr.commit();
        ss.close();
        return "Sme create successfully";
    }
    
    public String updateSme(Sme sme){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.update(sme);
        tr.commit();
        ss.close();
        return "Sme updated successfully";
    }
    
    public String deleteSme(Sme sme){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.delete(sme);
        tr.commit();
        ss.close();
        return "Sme deleted successfully";
    }
    
    public List<Sme> getAllSmes(){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        List<Sme> smeList = ss.createQuery("select sm from Sme sm").list();
        ss.close();
        return smeList;
    }
    
    public Sme getSmeById(Sme sme){
         Session ss = HibernateUtil.getSessionFactory().openSession();
         Sme sm = (Sme)ss.get(Sme.class, sme.getSmeId());
         ss.close();
         return sm;
        
    }
    
    public Sme getSmeByUserId(User user) {
    Session ss = HibernateUtil.getSessionFactory().openSession();
    Sme sme = (Sme) ss.createQuery(
        "select s from Sme s where s.user.userId = :uid")
        .setParameter("uid", user.getUserId())
        .uniqueResult();
    ss.close();
    return sme;
}
    
    
    
}
