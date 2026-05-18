# Task — Add 2 Missing RMI Methods on the Server
# Project: SmeTradeCreditFinancingSystemsServer28028
# NEVER modify initComponents(), NEVER change existing method signatures
# NEVER rewrite existing methods — only ADD what is missing

========================================================
WHAT AND WHY
========================================================

The client calls two methods that do not yet exist on the server.
Until these are added the server will crash with a RemoteException
when the Investor Dashboard and Fund Invoice pages open.

Method 1: getInvestorByUserId(User user)
  - Called by: InvestorDashboard, FundInvoice
  - Purpose: find the Investor profile linked to a logged-in User

Method 2: getFundingsByInvestorId(Investor investor)
  - Called by: InvestorDashboard, FundInvoice
  - Purpose: get all Funding records made by a specific Investor

========================================================
STEP 1 — Read these 4 files first before changing anything
========================================================

- src/rw/rab/service/InvestorService.java
- src/rw/rab/service/implementation/InvestorServiceImpl.java
- src/rw/rab/service/FundingService.java
- src/rw/rab/service/implementation/FundingServiceImpl.java

Confirm the exact package names, imports, and HibernateUtil path
used in the existing methods, then apply the steps below.

========================================================
STEP 2 — Add to InvestorService.java interface
========================================================

Add this one line inside the interface body:

    public Investor getInvestorByUserId(User user) throws RemoteException;

========================================================
STEP 3 — Add to InvestorServiceImpl.java
========================================================

Add this method outside any existing method:

    @Override
    public Investor getInvestorByUserId(User user) throws RemoteException {
        try {
            org.hibernate.Session session = rw.rab.util.HibernateUtil.getSessionFactory().openSession();
            Investor investor = (Investor) session.createQuery(
                "FROM Investor WHERE user.userId = :userId")
                .setParameter("userId", user.getUserId())
                .uniqueResult();
            session.close();
            return investor;
        } catch (Exception e) {
            throw new RemoteException("Error finding investor by user: " + e.getMessage());
        }
    }

========================================================
STEP 4 — Add to FundingService.java interface
========================================================

Add this one line inside the interface body:

    public List<Funding> getFundingsByInvestorId(Investor investor) throws RemoteException;

========================================================
STEP 5 — Add to FundingServiceImpl.java
========================================================

Add this method outside any existing method:

    @Override
    public List<Funding> getFundingsByInvestorId(Investor investor) throws RemoteException {
        try {
            org.hibernate.Session session = rw.rab.util.HibernateUtil.getSessionFactory().openSession();
            List<Funding> fundings = session.createQuery(
                "FROM Funding WHERE investor.investorId = :investorId")
                .setParameter("investorId", investor.getInvestorId())
                .list();
            session.close();
            return fundings;
        } catch (Exception e) {
            throw new RemoteException("Error finding fundings by investor: " + e.getMessage());
        }
    }

========================================================
STEP 6 — No new RMI binding needed
========================================================

The "investor" and "funding" services are already bound in the
registry by existing code. These new methods are added to the
same existing service objects — no new registry.bind() is needed.

========================================================
STEP 7 — After adding the methods
========================================================

1. Clean and Build the SERVER project
2. Restart the server
3. Clean and Build the CLIENT project
4. Run the client and log in as an Investor
5. Verify the Investor Dashboard loads stats correctly
6. Verify the Fund Invoice page loads history correctly
