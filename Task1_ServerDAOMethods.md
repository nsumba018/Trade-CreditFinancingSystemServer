# TASK 1 — Server Side Missing DAO Methods
# Paste these methods into the correct server-side DAO files
# NEVER touch initComponents()
# Keep simple beginner style

========================================================
FILE: SmeTradeCreditFinancingSystemsServer28028
PACKAGE: rw.rab.dao
CLASS: InvoiceDao.java
ACTION: REPLACE the existing updateInvoice method with this
========================================================

public String updateInvoice(Invoice invoice) {
    Session ss = HibernateUtil.getSessionFactory().openSession();
    Transaction tr = ss.beginTransaction();
    Invoice existing = (Invoice) ss.get(Invoice.class, invoice.getInvoiceId());
    if (invoice.getStatus() != null) {
        existing.setStatus(invoice.getStatus());
    }
    if (invoice.getInvoiceNumber() != null) {
        existing.setInvoiceNumber(invoice.getInvoiceNumber());
    }
    if (invoice.getAmount() > 0) {
        existing.setAmount(invoice.getAmount());
    }
    if (invoice.getIssueDate() != null) {
        existing.setIssueDate(invoice.getIssueDate());
    }
    if (invoice.getDueDate() != null) {
        existing.setDueDate(invoice.getDueDate());
    }
    ss.update(existing);
    tr.commit();
    ss.close();
    return "Invoice Updated Successfully";
}

========================================================
FILE: SmeTradeCreditFinancingSystemsServer28028
PACKAGE: rw.rab.dao
CLASS: FundingDao.java
ACTION: ADD this new method
========================================================

public List<Funding> getFundingsByInvestorId(Investor investor) {
    Session ss = HibernateUtil.getSessionFactory().openSession();
    List<Funding> list = ss.createQuery(
        "select f from Funding f where f.investor.investorId = :iid")
        .setParameter("iid", investor.getInvestorId())
        .list();
    ss.close();
    return list;
}

========================================================
FILE: SmeTradeCreditFinancingSystemsServer28028
PACKAGE: rw.rab.dao
CLASS: InvestorDao.java
ACTION: ADD this new method
========================================================

public Investor getInvestorByUserId(User user) {
    Session ss = HibernateUtil.getSessionFactory().openSession();
    Investor result = (Investor) ss.createQuery(
        "select i from Investor i where i.user.userId = :uid")
        .setParameter("uid", user.getUserId())
        .uniqueResult();
    ss.close();
    return result;
}

========================================================
FILE: SmeTradeCreditFinancingSystemsServer28028
PACKAGE: rw.rab.service
CLASS: FundingService.java (SERVER)
ACTION: ADD this method to the interface
========================================================

public List<Funding> getFundingsByInvestorId(Investor investor) throws RemoteException;

========================================================
FILE: SmeTradeCreditFinancingSystemsServer28028
PACKAGE: rw.rab.service
CLASS: InvestorService.java (SERVER)
ACTION: ADD this method to the interface
========================================================

public Investor getInvestorByUserId(User user) throws RemoteException;

========================================================
FILE: SmeTradeCreditFinancingSystemsServer28028
PACKAGE: rw.rab.service.implementation
CLASS: FundingServiceImpl.java
ACTION: ADD this method
========================================================

@Override
public List<Funding> getFundingsByInvestorId(Investor investor) throws RemoteException {
    return dao.getFundingsByInvestorId(investor);
}

========================================================
FILE: SmeTradeCreditFinancingSystemsServer28028
PACKAGE: rw.rab.service.implementation
CLASS: InvestorServiceImpl.java
ACTION: ADD this method
========================================================

@Override
public Investor getInvestorByUserId(User user) throws RemoteException {
    return dao.getInvestorByUserId(user);
}

========================================================
FILE: SmeTradeCreditFinancingSystemClient28028
PACKAGE: rw.rab.service
CLASS: FundingService.java (CLIENT)
ACTION: ADD this method to the interface
========================================================

public List<Funding> getFundingsByInvestorId(Investor investor) throws RemoteException;

========================================================
FILE: SmeTradeCreditFinancingSystemClient28028
PACKAGE: rw.rab.service
CLASS: InvestorService.java (CLIENT)
ACTION: ADD this method to the interface
========================================================

public Investor getInvestorByUserId(User user) throws RemoteException;

========================================================
ALSO: Make sure CLIENT Funding.java has these fields (no annotations):
========================================================

private rw.rab.model.Invoice invoice;
private rw.rab.model.Investor investor;

public rw.rab.model.Invoice getInvoice() { return invoice; }
public void setInvoice(rw.rab.model.Invoice invoice) { this.invoice = invoice; }
public rw.rab.model.Investor getInvestor() { return investor; }
public void setInvestor(rw.rab.model.Investor investor) { this.investor = investor; }

========================================================
ALSO: Make sure CLIENT Investor.java has these fields (no annotations):
========================================================

private rw.rab.model.User user;

public rw.rab.model.User getUser() { return user; }
public void setUser(rw.rab.model.User user) { this.user = user; }
