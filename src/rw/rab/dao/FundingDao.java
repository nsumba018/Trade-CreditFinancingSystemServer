
package rw.rab.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rw.rab.model.Funding;
import rw.rab.model.Investor;

/**
 *
 * @author nsumba
 */
public class FundingDao {
    public String createFunding(Funding funding){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.save(funding);
        tr.commit();
        ss.close();
        return "Funding Saved Successfully";
    }
    
    public String updateFunding(Funding funding){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.update(funding);
        tr.commit();
        ss.close();
        return "Funding Updated Successfully";
    }
    
    public String deleteFunding(Funding funding){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.delete(funding);
        tr.commit();
        ss.close();
        return "Funding Deleted Successfully";
    }
    
    public List<Funding> getAllFundings(){
         Session ss = HibernateUtil.getSessionFactory().openSession();
         List<Funding> fundingsList = ss.createQuery("select fund from Funding fund").list();
         ss.close();
         return fundingsList;
    }
    
    public Funding getFundingById(Funding funding){
         Session ss = HibernateUtil.getSessionFactory().openSession();
         Funding fund= (Funding)ss.get(Funding.class, funding.getFundingId());
         ss.close();
         return fund;
    }

    public List<Funding> getFundingsByInvestorId(Investor investor) {
        Session ss = HibernateUtil.getSessionFactory().openSession();
        List<Funding> list = ss.createQuery(
            "select f from Funding f where f.investor.investorId = :iid")
            .setParameter("iid", investor.getInvestorId())
            .list();
        ss.close();
        return list;
    }


}
