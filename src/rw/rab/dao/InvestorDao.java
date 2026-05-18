
package rw.rab.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rw.rab.model.Investor;
import rw.rab.model.User;

/**
 *
 * @author nsumba
 */
public class InvestorDao {
    public String createInvestor(Investor invoice){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.save(invoice);
        tr.commit();
        ss.close();
        return "Investor Saved Successfully";
    }
    
    public String updateInvestor(Investor invoice){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.update(invoice);
        tr.commit();
        ss.close();
        return "Investor Updated Successfully";
    }
    
    public String deleteInvestor(Investor invoice){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.delete(invoice);
        tr.commit();
        ss.close();
        return "Investor Deleted Successfully";
    }
    
    public List<Investor> getAllInvestors(){
         Session ss = HibernateUtil.getSessionFactory().openSession();
         List<Investor> invoicesList = ss.createQuery("select invest from Investor invest").list();
         ss.close();
         return invoicesList;
    }
    
    public Investor getInvestorById(Investor invoice){
         Session ss = HibernateUtil.getSessionFactory().openSession();
         Investor invest= (Investor)ss.get(Investor.class, invoice.getInvestorId());
         ss.close();
         return invest;
    }

    public Investor getInvestorByUserId(User user) {
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Investor result = (Investor) ss.createQuery(
            "select i from Investor i where i.user.userId = :uid")
            .setParameter("uid", user.getUserId())
            .uniqueResult();
        ss.close();
        return result;
    }


}
