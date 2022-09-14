
package et.gov.eep.fcms.mapper.budget;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
    //</editor-fold>

@Stateless
public class FmsOperatingBudget1Facade extends AbstractFacade<FmsOperatingBudget1> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
    
    //<editor-fold defaultstate="collapsed" desc="other methods ">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsOperatingBudget1Facade() {
        super(FmsOperatingBudget1.class);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NamedQuery ">
    public ArrayList<FmsOperatingBudget1> findAllRequest(Integer requestedBy) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findAll");
        query.setParameter("requestedBy", requestedBy);
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findRequestForApproval() {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findRequestForApproval");
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findRequestForConsApproval() {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findRequestForConsApproval", FmsOperatingBudget1.class);
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYear(FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYear");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystem(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearAndSystem");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("systemId", fmsCostCenter.getSystemId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearSystem(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction costcSystemJunction) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearSystem");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("ccSsJunction", costcSystemJunction);
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearSystemCons(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction ccSsJunction) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearSystemCons");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("ccSsJunction", ccSsJunction);
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenter(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearAndCostCenter");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("costCenter", fmsCostCenter.getCostCenterId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findBySystem(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findBySystem");
        query.setParameter("systemId", fmsCostCenter.getSystemId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenter(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findBySystemAndCostCenter");
        query.setParameter("costCenter", fmsCostCenter.getCostCenterId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByRequestCode(FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByRequestCode");
        query.setParameter("requestCode", fmsOperatingBudget1.getRequestCode());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findAllRequestPrepared() {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findAllPrepared");
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearPrepared(FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearPrepared");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemPrepared(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearAndSystemPrepared");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("systemId", fmsCostCenter.getSystemId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenterPrepared(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearAndCostCenterPrepared");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("costCenter", fmsCostCenter.getCostCenterId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findBySystemPrepared(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findBySystemPrepared");
        query.setParameter("systemId", fmsCostCenter.getSystemId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterPrepared(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findBySystemAndCostCenterPrepared");
        query.setParameter("costCenter", fmsCostCenter.getCostCenterId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByRequestCodePrepared(FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByRequestCodePrepared");
        query.setParameter("requestCode", fmsOperatingBudget1.getRequestCode());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findAllRequestChecked() {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findAllChecked");
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearChecked(FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearChecked");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemChecked(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearAndSystemChecked");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("systemId", fmsCostCenter.getSystemId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenterChecked(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearAndCostCenterChecked");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("costCenter", fmsCostCenter.getCostCenterId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findBySystemChecked(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findBySystemChecked");
        query.setParameter("systemId", fmsCostCenter.getSystemId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterChecked(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findBySystemAndCostCenterChecked");
        query.setParameter("costCenter", fmsCostCenter.getCostCenterId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByRequestCodeChecked(FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByRequestCodeChecked");
        query.setParameter("requestCode", fmsOperatingBudget1.getRequestCode());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findAllRequestApproved() {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findAllApproved");
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearApproved(FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearApproved");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemApproved(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearAndSystemApproved");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("systemId", fmsCostCenter.getSystemId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenterApproved(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearAndCostCenterApproved");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("costCenter", fmsCostCenter.getCostCenterId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findBySystemApproved(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findBySystemApproved");
        query.setParameter("systemId", fmsCostCenter.getSystemId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterApproved(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findBySystemAndCostCenterApproved");
        query.setParameter("costCenter", fmsCostCenter.getCostCenterId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByRequestCodeApproved(FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByRequestCodeApproved");
        query.setParameter("requestCode", fmsOperatingBudget1.getRequestCode());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findAllRequestAuthorized() {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findAllAuthorized");
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAuthorized(FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearAuthorized");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemAuthorized(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearAndSystemAuthorized");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("systemId", fmsCostCenter.getSystemId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public List<FmsOperatingBudget1> findByBudgetYearAndCostCenterAuthorized(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction ccSsJunction) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByBudgetYearAndCostCenterAuthorized");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("ccSsJunction", ccSsJunction);
        List<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        if (operatingBudgetList.size() > 0) {
            operatingBudgetList=query.getResultList();
            System.out.println("Req Code Size" + operatingBudgetList.size());
        }

        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findBySystemAuthorized(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findBySystemAuthorized");
        query.setParameter("systemId", fmsCostCenter.getSystemId());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterAuthorized(FmsCostcSystemJunction ccSsJunction) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findBySystemAndCostCenterAuthorized");
        query.setParameter("ccSsJunction", ccSsJunction);
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> findByRequestCodeAuthorized(FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByRequestCodeAuthorized");
        query.setParameter("requestCode", fmsOperatingBudget1.getRequestCode());
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }
      public FmsOperatingBudget1 fetchByRequestCode(FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.fetchByRequestCode");
        query.setParameter("requestCode", fmsOperatingBudget1.getRequestCode());
        FmsOperatingBudget1 operatingBudget = (FmsOperatingBudget1) query.getResultList().get(0);
        return operatingBudget;
    }

    public FmsOperatingBudget1 findByRequestCodeOnRequest(FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNamedQuery("FmsOperatingBudget1.findByRequestCodeOnRequest");
        query.setParameter("requestCode", fmsOperatingBudget1.getRequestCode());
        FmsOperatingBudget1 operatingBudget = (FmsOperatingBudget1) query.getResultList().get(0);
        return operatingBudget;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NativeQuery">
     public ArrayList<FmsOperatingBudget1> fetchByOBforProcessOnReq(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNativeQuery("SELECT *  from FMS_OPERATING_BUDGET1\n"
                + "where FMS_OPERATING_BUDGET1.COST_CENTER in "
                + "(select  FMS_COST_CENTER.COST_CENTER_ID from FMS_COST_CENTER where FMS_COST_CENTER.SYSTEM_ID = ' " + fmsCostCenter.getSystemId().getSystemId() + " ')\n"
                + "AND FMS_OPERATING_BUDGET1.STATUS = 17 OR FMS_OPERATING_BUDGET1.STATUS = 18 AND FMS_OPERATING_BUDGET1.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + " '",
                FmsOperatingBudget1.class);
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> fetchByOBforProcessPrepared(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNativeQuery("SELECT *  from FMS_OPERATING_BUDGET1\n"
                + "where FMS_OPERATING_BUDGET1.COST_CENTER in "
                + "(select  FMS_COST_CENTER.COST_CENTER_ID from FMS_COST_CENTER where FMS_COST_CENTER.SYSTEM_ID = ' " + fmsCostCenter.getSystemId().getSystemId() + " ')\n"
                + "AND FMS_OPERATING_BUDGET1.STATUS = 0 FMS_OPERATING_BUDGET1.STATUS = 2 AND FMS_OPERATING_BUDGET1.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + " '",
                FmsOperatingBudget1.class);
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> fetchByOBsChecked(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNativeQuery("SELECT *  from FMS_OPERATING_BUDGET1\n"
                + "WHERE FMS_OPERATING_BUDGET1.STATUS = 1 FMS_OPERATING_BUDGET1.STATUS = 4 AND FMS_OPERATING_BUDGET1.COST_CENTER in "
                + "(select  FMS_COST_CENTER.COST_CENTER_ID from FMS_COST_CENTER where FMS_COST_CENTER.SYSTEM_ID = ' " + fmsCostCenter.getSystemId().getSystemId() + " ')\n"
                + " AND FMS_OPERATING_BUDGET1.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + " '",
                FmsOperatingBudget1.class);
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> fetchByOBsApproved(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNativeQuery("SELECT *  from FMS_OPERATING_BUDGET1\n"
                + "WHERE FMS_OPERATING_BUDGET1.STATUS = 3 FMS_OPERATING_BUDGET1.STATUS = 11 AND FMS_OPERATING_BUDGET1.COST_CENTER in "
                + "(select  FMS_COST_CENTER.COST_CENTER_ID from FMS_COST_CENTER where FMS_COST_CENTER.SYSTEM_ID = ' " + fmsCostCenter.getSystemId().getSystemId() + " ')\n"
                + "AND FMS_OPERATING_BUDGET1.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + " '",
                FmsOperatingBudget1.class);
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public ArrayList<FmsOperatingBudget1> fetchByOBsAuthorized(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNativeQuery("SELECT *  from FMS_OPERATING_BUDGET1\n"
                + "WHERE FMS_OPERATING_BUDGET1.STATUS = 10 AND FMS_OPERATING_BUDGET1.COST_CENTER in "
                + "(select  FMS_COST_CENTER.COST_CENTER_ID from FMS_COST_CENTER where FMS_COST_CENTER.SYSTEM_ID = ' " + fmsCostCenter.getSystemId().getSystemId() + " ')\n"
                + " AND FMS_OPERATING_BUDGET1.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + " '",
                FmsOperatingBudget1.class);
        ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }
      public ArrayList<FmsOperatingBudget1> findByCostCenterAuthorized(FmsCostCenter fmsCostCenter) {
        if (null != fmsCostCenter.getCostCenterId()) {
            Query query = em.createNativeQuery("SELECT * FROM FMS_OPERATING_BUDGET1 WHERE FMS_OPERATING_BUDGET1.COST_CENTER = '" + fmsCostCenter.getCostCenterId() + "' AND FMS_OPERATING_BUDGET1.STATUS = 10", FmsOperatingBudget1.class);
            ArrayList<FmsOperatingBudget1> operatingBudgetList = new ArrayList(query.getResultList());
            return operatingBudgetList;
        } else if (null == fmsCostCenter.getCostCenterId()) {
            return null;
        } else {
            return null;
        }
    }

    public Integer RowCount() {
        Query query = em.createNativeQuery("SELECT COUNT (*) FROM FMS_OPERATING_BUDGET1");
        BigDecimal count = (BigDecimal) query.getSingleResult();
        return count.intValue();
    }

    public Integer CCSSJuncDuplicationChecker(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction costcSystemJunction) {
        Query query = em.createNativeQuery("SELECT COUNT (*) from FMS_OPERATING_BUDGET1 \n"
                + "WHERE  FMS_OPERATING_BUDGET1.CC_SS_JUNCTION = '" + costcSystemJunction.getId() + "' \n"
                + " AND FMS_OPERATING_BUDGET1.BUDGET_YEAR = '" + budgetYear.getLuBudgetYearId() + "'");

        BigDecimal count = (BigDecimal) query.getSingleResult();
        return count.intValue();
    }
    //</editor-fold>
    
}
