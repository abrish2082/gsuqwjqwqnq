

package et.gov.eep.fcms.mapper.budget;
//<editor-fold defaultstate="collapsed" desc="import">
    import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
    //</editor-fold>


@Stateless
public class FmsCapitalBudget1Facade extends AbstractFacade<FmsCapitalBudget1> {
//<editor-fold defaultstate="collapsed" desc="other methods ">
      @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsCapitalBudget1Facade() {
        super(FmsCapitalBudget1.class);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NamedQuery">
    public ArrayList<FmsCapitalBudget1> findAllRequest() {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findAll");
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> findByBudgetYearSystem(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findByBudgetYearSystem");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("fmsLuSystem", fmsCostcSystemJunction.getFmsLuSystem());
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> findByBudgetYearSystemCons(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findByBudgetYearSystemCons");
        System.out.println("budgetYearbudgetYear   " + budgetYear);
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("fmsLuSystem", fmsCostcSystemJunction.getFmsLuSystem());
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> findRequestForApproval() {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findRequestForApproval", FmsCapitalBudget1.class);
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> findRequestForConsApproval() {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findRequestForConsApproval", FmsCapitalBudget1.class);
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> findByBudgetYear(FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findByBudgetYear");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> findByBudgetYearAndSystem(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {
        System.out.println("fmsCostcSystemJunction    " + fmsCostcSystemJunction);

        Query query = em.createNamedQuery("FmsCapitalBudget1.findByBudgetYearAndSystem");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("fmsLuSystem", fmsCostcSystemJunction.getFmsLuSystem());
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> findByBudgetYearAndCostCenter(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findByBudgetYearAndCostCenter");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("fmsCostCenter", fmsCostcSystemJunction.getFmsCostCenter());
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> findBySystem(FmsCostcSystemJunction fmsCostcSystemJunction) {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findBySystem");
        query.setParameter("fmsLuSystem", fmsCostcSystemJunction.getFmsLuSystem());
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> findBySystemAndCostCenter(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findBySystemAndCostCenter");
        query.setParameter("costCenter", fmsCostCenter.getCostCenterId());
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NativeQuery ">
       public ArrayList<FmsCapitalBudget1> fetchCBforProcessOnReq(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNativeQuery("SELECT *  from FMS_CAPITAL_BUDGET1\n"
                + "where FMS_CAPITAL_BUDGET1.COST_CENTER in "
                + "(select  FMS_COST_CENTER.COST_CENTER_ID from FMS_COST_CENTER where FMS_COST_CENTER.SYSTEM_ID = ' " + fmsCostCenter.getSystemId().getSystemId() + " ')\n"
                + "AND FMS_CAPITAL_BUDGET1.STATUS = 17 OR FMS_CAPITAL_BUDGET1.STATUS = 18 AND FMS_CAPITAL_BUDGET1.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + " '",
                FmsCapitalBudget1.class);
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> fetchCBforProcessPrepared(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNativeQuery("SELECT *  from FMS_CAPITAL_BUDGET1\n"
                + "where FMS_CAPITAL_BUDGET1.COST_CENTER in "
                + "(select  FMS_COST_CENTER.COST_CENTER_ID from FMS_COST_CENTER where FMS_COST_CENTER.SYSTEM_ID = ' " + fmsCostCenter.getSystemId().getSystemId() + " ')\n"
                + "AND FMS_CAPITAL_BUDGET1.STATUS = 0 OR FMS_CAPITAL_BUDGET1.STATUS = 2 AND FMS_CAPITAL_BUDGET1.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + " '",
                FmsCapitalBudget1.class);
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> fetchCBsChecked(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNativeQuery("SELECT *  from FMS_CAPITAL_BUDGET1\n"
                + "where FMS_CAPITAL_BUDGET1.COST_CENTER in "
                + "(select  FMS_COST_CENTER.COST_CENTER_ID from FMS_COST_CENTER where FMS_COST_CENTER.SYSTEM_ID = ' " + fmsCostCenter.getSystemId().getSystemId() + " ')\n"
                + "AND FMS_CAPITAL_BUDGET1.STATUS = 1 OR FMS_CAPITAL_BUDGET1.STATUS = 4 AND FMS_CAPITAL_BUDGET1.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + " '",
                FmsCapitalBudget1.class);
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> fetchCBsApproved(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNativeQuery("SELECT *  from FMS_CAPITAL_BUDGET1\n"
                + "where FMS_CAPITAL_BUDGET1.COST_CENTER in "
                + "(select  FMS_COST_CENTER.COST_CENTER_ID from FMS_COST_CENTER where FMS_COST_CENTER.SYSTEM_ID = ' " + fmsCostCenter.getSystemId().getSystemId() + " ')\n"
                + "AND FMS_CAPITAL_BUDGET1.STATUS = 3 OR FMS_CAPITAL_BUDGET1.STATUS = 11 AND FMS_CAPITAL_BUDGET1.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + " '",
                FmsCapitalBudget1.class);
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> fetchCBsAuthorized(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNativeQuery("SELECT *  from FMS_CAPITAL_BUDGET1\n"
                + "where FMS_CAPITAL_BUDGET1.COST_CENTER in "
                + "(select  FMS_COST_CENTER.COST_CENTER_ID from FMS_COST_CENTER where FMS_COST_CENTER.SYSTEM_ID = ' " + fmsCostCenter.getSystemId().getSystemId() + " ')\n"
                + "AND FMS_CAPITAL_BUDGET1.STATUS = 10 AND FMS_CAPITAL_BUDGET1.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + " '",
                FmsCapitalBudget1.class);
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public ArrayList<FmsCapitalBudget1> findCBSystemAndCostCenterAuthorized(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findBySystemAndCostCenterAuthorized");
        query.setParameter("costCenter", fmsCostCenter);
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }
    
    public ArrayList<FmsCapitalBudget1> findByCCSSJandPMSandBYRAuthorized(FmsLuBudgetYear fmsLuBudgetYear, FmsCostcSystemJunction fmsCostcSystemJunction, PmsWorkAuthorization authorization) {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findByCCSSJPMSBYRAuthorized");
        query.setParameter("budgetYear", fmsLuBudgetYear);
        query.setParameter("ccSsJunction", fmsCostcSystemJunction);
        query.setParameter("jobNo", authorization);
        ArrayList<FmsCapitalBudget1> capitalBudgetList = new ArrayList(query.getResultList());
        return capitalBudgetList;
    }

    public Integer CostCenterDuplicationChecker(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction, PmsWorkAuthorization authorization) {
        Query query = em.createNativeQuery("SELECT COUNT (*) from FMS_CAPITAL_BUDGET1 \n"
                + "WHERE  FMS_CAPITAL_BUDGET1.CC_SS_JUNCTION = '" + fmsCostcSystemJunction.getFmsCostCenter() + "' \n"
                + " AND FMS_CAPITAL_BUDGET1.BUDGET_YEAR = '" + budgetYear.getLuBudgetYearId() + "' AND FMS_CAPITAL_BUDGET1.JOB_NO = '" + authorization.getJobNo()+ "'");

        BigDecimal count = (BigDecimal) query.getSingleResult();
        return count.intValue();
    }

    public FmsCapitalBudget1 findByRequestCodeOnRequest(FmsCapitalBudget1 capitalBudget1) {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findByRequestCodeOnRequest");
        query.setParameter("requestCode", capitalBudget1.getRequestCode());
        FmsCapitalBudget1 capitalBudget = (FmsCapitalBudget1) query.getResultList().get(0);
        return capitalBudget;
    }
    
    public Integer RowCount() {
        Query query = em.createNativeQuery("SELECT COUNT (*) FROM FMS_CAPITAL_BUDGET1");
        BigDecimal count = (BigDecimal) query.getSingleResult();
        return count.intValue();
    }
    
    
    public ArrayList<FmsCapitalBudget1> findByBudgetYearAndCostCenterAuthorized(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction ccSsJunction) {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findByBudgetYearAndCostCenterAuthorized");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("ccSsJunction", ccSsJunction);
        ArrayList<FmsCapitalBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }
    //</editor-fold>
    
    
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

  

    

 
}
