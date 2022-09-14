
package et.gov.eep.fcms.mapper.budget;

//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DataReadQuery;
    //</editor-fold>

@Stateless
public class FmsOperatingBudgetDetailFacade extends AbstractFacade<FmsOperatingBudgetDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
    
//<editor-fold defaultstate="collapsed" desc="other methods ">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsOperatingBudgetDetailFacade() {
        super(FmsOperatingBudgetDetail.class);
    }
      public FmsOperatingBudgetDetail budgetcomparison(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        int Sys = Integer.parseInt(hashMap.get("SYS").toString());
        int bugetyear = Integer.parseInt(hashMap.get("BGYEAR").toString());
        int Gl = Integer.parseInt(hashMap.get("GL").toString());
        String sql = "SELECT FMS_OPERATING_BUDGET_DETAIL.* FROM FMS_LU_BUDGET_YEAR "
                + "INNER JOIN FMS_OPERATING_BUDGET1 ON FMS_LU_BUDGET_YEAR.LU_BUDGET_YEAR_ID = FMS_OPERATING_BUDGET1.BUDGET_YEAR "
                + "INNER JOIN FMS_COST_CENTER ON FMS_COST_CENTER.COST_CENTER_ID = FMS_OPERATING_BUDGET1.COST_CENTER "
                + "INNER JOIN FMS_OPERATING_BUDGET_DETAIL ON FMS_OPERATING_BUDGET1.OPERATING_BUDGET_ID = FMS_OPERATING_BUDGET_DETAIL.OPERATING_BUDGET_ID "
                + "WHERE FMS_OPERATING_BUDGET_DETAIL.GENERAL_LEDGER_ID = " + Gl + " AND FMS_OPERATING_BUDGET1.BUDGET_YEAR= " + bugetyear + " AND FMS_COST_CENTER.SYSTEM_ID = " + Sys;
        Query query2 = em.createNativeQuery(sql);
        if (query2.getResultList().isEmpty() != true) {
            List<Object[]> results = query2.getResultList();
            Object[] result = results.get(0);
            System.err.println("buget comper--size-2-" + results.size());
            FmsOperatingBudgetDetail budgetDetail = new FmsOperatingBudgetDetail();
            budgetDetail.setApprovedAmount(BigDecimal.valueOf(Double.parseDouble(result[1].toString())));
            budgetDetail.setOBDetailId(Integer.parseInt(result[0].toString()));
            budgetDetail.setRemainingBalance(BigDecimal.valueOf(Double.parseDouble(result[3].toString())));
            System.out.println("buget comper----------------" + budgetDetail.getRemainingBalance());

            return budgetDetail;
        } else {
            return null;
        }
    }

    public Double budgetDf(HashMap hashMap) {
        System.out.println("welcome   ");
        DataReadQuery databaseQuery = new DataReadQuery();
        int Sys = Integer.parseInt(hashMap.get("SYS").toString());
        int bugetyear = Integer.parseInt(hashMap.get("BGYEAR").toString());
        int Gl = Integer.parseInt(hashMap.get("GL").toString());
        String sql = "SELECT FMS_OPERATING_BUDGET_DETAIL.* FROM FMS_LU_BUDGET_YEAR "
                + "INNER JOIN FMS_OPERATING_BUDGET1 ON FMS_LU_BUDGET_YEAR.LU_BUDGET_YEAR_ID = FMS_OPERATING_BUDGET1.BUDGET_YEAR "
                + "INNER JOIN FMS_COST_CENTER ON FMS_COST_CENTER.COST_CENTER_ID = FMS_OPERATING_BUDGET1.COST_CENTER "
                + "INNER JOIN FMS_OPERATING_BUDGET_DETAIL ON FMS_OPERATING_BUDGET1.OPERATING_BUDGET_ID = FMS_OPERATING_BUDGET_DETAIL.OPERATING_BUDGET_ID "
                + "WHERE FMS_OPERATING_BUDGET_DETAIL.GENERAL_LEDGER_ID = " + Gl + " AND FMS_OPERATING_BUDGET1.BUDGET_YEAR= " + bugetyear + " AND FMS_COST_CENTER.SYSTEM_ID = " + Sys;
        Query query2 = em.createNativeQuery(sql);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query2.getResultList().isEmpty() != true) {
            System.out.println("welcome   " + query.getMaxResults());
            List<Object[]> results = query2.getResultList();
            System.err.println("--size-2-" + results.size());
            FmsOperatingBudgetDetail budgetDetail = new FmsOperatingBudgetDetail();
            budgetDetail.setApprovedAmount(BigDecimal.valueOf(Double.parseDouble(Arrays.toString(results.get(0)))));
            budgetDetail.setOBDetailId(Integer.parseInt(Arrays.toString(results.get(0))));
            budgetDetail.setRemainingBalance(BigDecimal.valueOf(Double.parseDouble(Arrays.toString(results.get(0)))));
            Double Utilization = 0.0;
            Utilization = Double.parseDouble(Arrays.toString(results.get(1))) - Double.parseDouble(Arrays.toString(results.get(3)));
            return Utilization;
        } else {
            return null;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NamedQuery ">
    public List<FmsOperatingBudgetDetail> fetchSelectedOBRequest(FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNamedQuery("FmsOperatingBudgetDetail.findByOBId");
        query.setParameter("operatingBudgetId", fmsOperatingBudget1.getOperatingBudgetId());
        ArrayList<FmsOperatingBudgetDetail> operatingBudgetDetailList = new ArrayList(query.getResultList());
        return operatingBudgetDetailList;
    }

    public List<FmsOperatingBudgetDetail> fetchOBDetail(FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNamedQuery("FmsOperatingBudgetDetail.findByOBId");
        query.setParameter("operatingBudgetId", fmsOperatingBudget1);
        ArrayList<FmsOperatingBudgetDetail> operatingBudgetDetailList = new ArrayList(query.getResultList());
        return operatingBudgetDetailList;
    }

    public List<FmsOperatingBudgetDetail> fetchOBDetailByGL(FmsOperatingBudget1 fmsOperatingBudget1, FmsGeneralLedger fmsGeneralLedger) {
        Query query = em.createNamedQuery("FmsOperatingBudgetDetail.findByGeneralLedger");
        query.setParameter("requestCode", fmsOperatingBudget1.getRequestCode());
        query.setParameter("generalLedgerCode", fmsGeneralLedger.getGeneralLedgerCode());
        ArrayList<FmsOperatingBudgetDetail> operatingBudgetDetailList = new ArrayList(query.getResultList());
        return operatingBudgetDetailList;
    }
        public FmsOperatingBudgetDetail getOBDetail(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {

        Query query = em.createNamedQuery("FmsOperatingBudgetDetail.findByOBDetailId");
        query.setParameter("oBDetailId", fmsOperatingBudgetDetail.getOBDetailId());
        FmsOperatingBudgetDetail operatingBudget = (FmsOperatingBudgetDetail) query.getResultList().get(0);
        return operatingBudget;
    }
    
    public FmsOperatingBudgetDetail fetchOB(FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNamedQuery("FmsOperatingBudgetDetail.findByOBId");
        query.setParameter("operatingBudgetId", fmsOperatingBudget1);
        FmsOperatingBudgetDetail operatingBudget = (FmsOperatingBudgetDetail) query.getResultList().get(0);
        return operatingBudget;
    }
    
    public FmsOperatingBudgetDetail fetchOBDetailByGLOB1(FmsOperatingBudget1 fmsOperatingBudget1,FmsGeneralLedger fmsGeneralLedger) {
        Query query = em.createNamedQuery("FmsOperatingBudgetDetail.findByGeneralLedger");
        query.setParameter("requestCode", fmsOperatingBudget1.getRequestCode());
        query.setParameter("generalLedgerCode", fmsGeneralLedger.getGeneralLedgerCode());
        FmsOperatingBudgetDetail operatingBudget = (FmsOperatingBudgetDetail) query.getResultList().get(0);
        return operatingBudget;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NativeQuery ">
     public ArrayList<FmsOperatingBudgetDetail> fetchByDepartmentandCurrenPerid(HrDepartments departments, FmsAccountingPeriod currentPeriod) {
        try {

            if (departments.getDepId() != null) {
                Query query = em.createNativeQuery("SELECT * FROM fms_operating_budget_detail obd\n"
                        + "inner join fms_general_ledger gl\n"
                        + "inner join prms_service_and_work_reg sw\n"
                        + "on sw.general_ledger_id=gl.general_ledger_id\n"
                        + "on gl.general_ledger_id=obd.general_ledger_id\n"
                        + "where obd.operating_budget_id in(SELECT ob1.operating_budget_id FROM fms_operating_budget1 ob1 \n"
                        + "where ob1.status='10' and  ob1.budget_year=" + currentPeriod.getLuBudgetYearId().getLuBudgetYearId() + " and ob1.cc_ss_junction in(SELECT csj.id FROM fms_costc_system_junction csj where csj.dep_id='" + departments.getDepId() + "')) \n"
                        + "and sw.registeration_type='service'", FmsOperatingBudgetDetail.class);
                System.out.println("Dep id====" + departments.getDepId());
                System.out.println("current Period====" + currentPeriod.getLuBudgetYearId().getBudgetYear());
                ArrayList<FmsOperatingBudgetDetail> operatingBudgetList = new ArrayList(query.getResultList());
                System.out.println("---Size--" + operatingBudgetList.size());
                return operatingBudgetList;

            } else {
                return null;
            }

        } catch (NullPointerException nullex) {
            throw nullex;
        }
    }

    public List<FmsOperatingBudgetDetail> getBudgetCodeListsByDepIdWhenWork(HrDepartments departments, FmsAccountingPeriod currentPeriod) {
        System.out.println("fetch budget code for work");
        try {
            if (departments.getDepId() != null) {
                Query qu = em.createNativeQuery("SELECT * FROM fms_operating_budget_detail obd\n"
                        + "inner join fms_general_ledger gl\n"
                        + "inner join prms_service_and_work_reg sw\n"
                        + "on sw.general_ledger_id=gl.general_ledger_id\n"
                        + "on gl.general_ledger_id=obd.general_ledger_id\n"
                        + "where obd.operating_budget_id in(SELECT ob1.operating_budget_id FROM fms_operating_budget1 ob1 \n"
                        + "where ob1.status='10' and  ob1.budget_year=" + currentPeriod.getLuBudgetYearId().getLuBudgetYearId() + " and ob1.cc_ss_junction in(SELECT csj.id FROM fms_costc_system_junction csj where csj.dep_id='" + departments.getDepId() + "')) \n"
                        + "and sw.registeration_type='work'", FmsOperatingBudgetDetail.class);

                ArrayList<FmsOperatingBudgetDetail> operatingBudgetListWork = null;
                if (qu.getResultList().size() > 0) {
                    operatingBudgetListWork = new ArrayList(qu.getResultList());
                    System.out.println("---Size of work--" + operatingBudgetListWork.size());

                }
                return operatingBudgetListWork;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.getMessage();
            return null;
        }

    }

    public ArrayList<FmsOperatingBudgetDetail> fetchByDepartment(HrDepartments departments) {
        try {
            if (departments.getDepId() != null) {
                Query query = em.createNativeQuery("select * from FMS_OPERATING_BUDGET_DETAIL DTL where \n"
                        + "DTL.OPERATING_BUDGET_ID in (select OPERATING_BUDGET_ID from FMS_OPERATING_BUDGET1 where COST_CENTER = (select COST_CENTER_ID from FMS_COST_CENTER where DEP_ID = '" + departments.getDepId() + "') \n"
                        + " AND STATUS = 10)",
                        FmsOperatingBudgetDetail.class);
                ArrayList<FmsOperatingBudgetDetail> operatingBudgetList = new ArrayList(query.getResultList());
                System.out.println("---Size--" + operatingBudgetList.size());
                return operatingBudgetList;

            } else {
                return null;
            }
        } catch (NullPointerException nullex) {
            throw nullex;
        }
    }

    public FmsOperatingBudgetDetail fetchUsingDepartmentAndGL(HrDepartments hrDepartments, FmsGeneralLedger fmsGeneralLedger) {
        System.out.println("=========HR dep==========" + hrDepartments.getDepId());
        System.out.println("=========GL id==========" + fmsGeneralLedger.getGeneralLedgerId());
        Query query = em.createNativeQuery("select * from FMS_OPERATING_BUDGET_DETAIL DTL where \n"
                + "DTL.OPERATING_BUDGET_ID in (select OPERATING_BUDGET_ID from FMS_OPERATING_BUDGET1 where COST_CENTER = (select COST_CENTER_ID from FMS_COST_CENTER where DEP_ID = '" + hrDepartments.getDepId() + "') \n"
                + "AND BUDGET_YEAR = (select BYR.LU_BUDGET_YEAR_ID from FMS_LU_BUDGET_YEAR BYR WHERE BYR.LU_BUDGET_YEAR_ID = (select AP.LU_BUDGET_YEAR_ID from FMS_ACCOUNTING_PERIOD AP where AP.STATUS = 1)) AND STATUS = 10) AND DTL.GENERAL_LEDGER_ID = '" + fmsGeneralLedger.getGeneralLedgerId() + "'",
                FmsOperatingBudgetDetail.class);
        FmsOperatingBudgetDetail operatingBudget = (FmsOperatingBudgetDetail) query.getResultList().get(0);
        return operatingBudget;
    }
      public List<FmsGeneralLedger> fetchGLfromOBDetail(FmsOperatingBudget1 fmsOperatingBudget1) {

        Query query = em.createNativeQuery("select * from FMS_GENERAL_LEDGER GL where \n"
                + "GL.GENERAL_LEDGER_ID in (select OBD.GENERAL_LEDGER_ID from FMS_OPERATING_BUDGET_DETAIL OBD inner join FMS_OPERATING_BUDGET1 OB\n"
                + "on OBD.OPERATING_BUDGET_ID = (select O.OPERATING_BUDGET_ID from FMS_OPERATING_BUDGET1 O where O.REQUEST_CODE = '" + fmsOperatingBudget1.getRequestCode() + "'))", FmsGeneralLedger.class);
        ArrayList<FmsGeneralLedger> gLOBDList = new ArrayList(query.getResultList());
        return gLOBDList;
    }

    public FmsOperatingBudgetDetail fetchByGLfromOBDetail(FmsGeneralLedger fmsGeneralLedger, FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNativeQuery("SELECT * FROM FMS_OPERATING_BUDGET_DETAIL DTL where DTL.GENERAL_LEDGER_ID = ' " + fmsGeneralLedger.getGeneralLedgerId().toString() + " ' \n"
                + "AND DTL.OPERATING_BUDGET_ID in (select OB.OPERATING_BUDGET_ID from FMS_OPERATING_BUDGET1 OB where OB.REQUEST_CODE = '" + fmsOperatingBudget1.getRequestCode() + "' )", FmsOperatingBudgetDetail.class);
        FmsOperatingBudgetDetail oBD = (FmsOperatingBudgetDetail) query.getResultList().get(0);   
        return oBD;
        
        
    }
    public ArrayList<FmsOperatingBudgetDetail> fetchByDepartmentID(FmsLuBudgetYear fmsLuBudgetYear, HrDepartments hrDepartments, FmsGeneralLedger fmsGeneralLedger) {

        Query query = em.createNativeQuery("select * from FMS_OPERATING_BUDGET_DETAIL DTL where \n"
                + "DTL.OPERATING_BUDGET_ID in (select OPERATING_BUDGET_ID from FMS_OPERATING_BUDGET1 where COST_CENTER = (select COST_CENTER_ID from FMS_COST_CENTER where DEP_ID = '" + hrDepartments.getDepId() + "') \n"
                + "AND BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + "' AND STATUS = 10) AND DTL.GENERAL_LEDGER_ID = '" + fmsGeneralLedger.getGeneralLedgerId() + "'",
                FmsOperatingBudgetDetail.class);
        ArrayList<FmsOperatingBudgetDetail> operatingBudgetList = new ArrayList(query.getResultList());
        return operatingBudgetList;
    }

    public FmsOperatingBudgetDetail fetchUsingDepartmentID(FmsLuBudgetYear fmsLuBudgetYear, HrDepartments hrDepartments, FmsGeneralLedger fmsGeneralLedger) {

        Query query = em.createNativeQuery("select * from FMS_OPERATING_BUDGET_DETAIL DTL where \n"
                + "DTL.OPERATING_BUDGET_ID in (select OPERATING_BUDGET_ID from FMS_OPERATING_BUDGET1 where COST_CENTER = (select COST_CENTER_ID from FMS_COST_CENTER where DEP_ID = '" + hrDepartments.getDepId() + "') \n"
                + "AND BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + "' AND STATUS = 10) AND DTL.GENERAL_LEDGER_ID = '" + fmsGeneralLedger.getGeneralLedgerId() + "'",
                FmsOperatingBudgetDetail.class);
        FmsOperatingBudgetDetail operatingBudget = (FmsOperatingBudgetDetail) query.getResultList().get(0);
        return operatingBudget;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="other methods ">
    //</editor-fold>
    
}
