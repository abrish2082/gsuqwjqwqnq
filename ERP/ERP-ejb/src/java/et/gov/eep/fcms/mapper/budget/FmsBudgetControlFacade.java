
package et.gov.eep.fcms.mapper.budget;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FMSOBTasksBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCBTasksBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOBDisbursementBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetDetailBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.budget.FmsBudgetControl;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//</editor-fold>

@Stateless
public class FmsBudgetControlFacade extends AbstractFacade<FmsBudgetControl> {
//<editor-fold defaultstate="collapsed" desc="EJB ">

    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FmsOperatingBudgetDetailBeanLocal fmsOperatingBudgetDetailBeanLocal;
    @EJB
    FmsOBDisbursementBeanLocal fmsOBDisbursementBeanLocal;
    @EJB
    FmsCapitalBudgetDetailBeanLocal fmsCapitalBudgetDetailBeanLocal;
    @EJB
    FmsCBTasksBeanLocal tasksBeanLocal;
    @EJB
    FMSOBTasksBeanLocal obtasksBeanLocal;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="NativeQuery ">
    public List<FmsBudgetControl> fetchOBComparison(FmsLuBudgetYear fmsLuBudgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {
        try {
            Query query = em.createNativeQuery("select BC.OB_TASK_ID ,BC.COST_CENTER_ID, BC.GENERAL_LEDGER_ID ,SUM(BC.PAID_AMOUNT) from FMS_BUDGET_CONTROL BC inner join FMS_OB_DISBURSEMENT DISB \n"
                    + "ON DISB.OB_TASK_ID = BC.OB_TASK_ID WHERE BC.CB_TASK_ID is NUll  AND BC.OB_TASK_ID in \n"
                    + "( select DISB.OB_TASK_ID from FMS_OB_DISBURSEMENT DISB WHERE DISB.OB_TASK_ID in \n"
                    + "( select TASKS.O_B_TASK_ID from FMS_OPERATING_BUDGET_TASKS TASKS WHERE TASKS.O_B_DETAIL_FK in \n"
                    + "( select DTL.O_B_DETAIL_ID from FMS_OPERATING_BUDGET_DETAIL DTL WHERE DTL.OPERATING_BUDGET_ID in \n"
                    + "(select OB.OPERATING_BUDGET_ID from FMS_OPERATING_BUDGET1 OB where OB.CC_SS_JUNCTION = '" + fmsCostcSystemJunction.getId() + "' \n"
                    + "AND OB.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + "' ) ) ) ) GROUP BY BC.OB_TASK_ID , BC.COST_CENTER_ID,BC.SUBSIDIARY_ID,BC.PAID_AMOUNT,BC.GENERAL_LEDGER_ID");

            List<Object[]> results = query.getResultList();
            FmsBudgetControl budgetControl;
            ArrayList<FmsBudgetControl> obCompList = new ArrayList();
            for (Object[] result : results) {

                budgetControl = new FmsBudgetControl();

                FmsCostCenter costCenter = new FmsCostCenter();
                FmsGeneralLedger generalLedger = new FmsGeneralLedger();
                FmsOperatingBudgetTasks operatingBudgetTasks = new FmsOperatingBudgetTasks();
                operatingBudgetTasks.setOBTaskId(Integer.parseInt(result[0].toString()));
                operatingBudgetTasks = obtasksBeanLocal.fetchOBTask(operatingBudgetTasks);
                costCenter.setCostCenterId(Integer.parseInt(result[1].toString()));
                costCenter = fmsCostCenterBeanLocal.getCostCenterId(costCenter);
                generalLedger.setGeneralLedgerId(Integer.parseInt(result[2].toString()));
                generalLedger = fmsGeneralLedgerBeanLocal.getByGlId(generalLedger);
                budgetControl.setObTaskId(operatingBudgetTasks);
                budgetControl.setCostCenterId(costCenter);
                budgetControl.setGeneralLedgerId(generalLedger);
                budgetControl.setTotalPaidAmount(new BigDecimal(result[3].toString()));
                budgetControl.setUtilization((budgetControl.getTotalPaidAmount().divide(operatingBudgetTasks.getApprovedAmount(), 2, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(100)));
                obCompList.add(budgetControl);
            }
            return obCompList;
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return null;
        }

    }

    public List<FmsBudgetControl> fetchCBComparison(FmsLuBudgetYear fmsLuBudgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {

        Query query = em.createNativeQuery("select BC.CB_TASK_ID ,BC.COST_CENTER_ID , BC.GENERAL_LEDGER_ID, SUM(BC.PAID_AMOUNT) from FMS_BUDGET_CONTROL BC inner join FMS_CB_DISBURSEMENT DISB\n"
                + "ON DISB.CB_TASK_ID = BC.CB_TASK_ID WHERE BC.OB_TASK_ID is NUll  AND BC.CB_TASK_ID in \n"
                + "( select DISB.CB_TASK_ID from FMS_CB_DISBURSEMENT DISB WHERE DISB.CB_TASK_ID in \n"
                + "( select TASKS.C_B_TASKS_ID from FMS_CAPITAL_BUDGET_TASKS TASKS WHERE TASKS.C_B_DETAIL_FK in \n"
                + "( select DTL.CB_DETAIL_ID from FMS_CAPITAL_BUDGET_DETAIL DTL WHERE DTL.CAPITAL_BUDGET_ID in \n"
                + "(select CB.CAPITAL_BUDGET_ID from FMS_CAPITAL_BUDGET1 CB where CB.CC_SS_JUNCTION ='" + fmsCostcSystemJunction.getId() + "' \n"
                + "AND CB.BUDGET_YEAR = '" + fmsLuBudgetYear.getLuBudgetYearId() + "' ) ) ) ) GROUP BY BC.CB_TASK_ID , BC.COST_CENTER_ID,BC.GENERAL_LEDGER_ID, BC.SUBSIDIARY_ID");

        List<Object[]> results = query.getResultList();
        System.out.println(" cost center ID:" + fmsCostcSystemJunction.getId());
        System.out.println(" fmsLuBudgetYear:" + fmsLuBudgetYear.getLuBudgetYearId());
        System.out.println(" query result" + query.getResultList().size());
        FmsBudgetControl budgetControl;
        ArrayList<FmsBudgetControl> cbCompList = new ArrayList();
        for (Object[] result : results) {

            budgetControl = new FmsBudgetControl();

            FmsCostCenter costCenter = new FmsCostCenter();
            FmsGeneralLedger generalLedger = new FmsGeneralLedger();
            FmsCapitalBudgetTasks capitalBudgetTasks = new FmsCapitalBudgetTasks();

            capitalBudgetTasks.setCBTasksId(Integer.parseInt(result[0].toString()));
            capitalBudgetTasks = tasksBeanLocal.fetchCBTask(capitalBudgetTasks);
            System.out.println(" capitalBudgetTasks" + capitalBudgetTasks);
            costCenter.setCostCenterId(Integer.parseInt(result[1].toString()));
            costCenter = fmsCostCenterBeanLocal.getCostCenterId(costCenter);

            generalLedger.setGeneralLedgerId(Integer.parseInt(result[2].toString()));
            generalLedger = fmsGeneralLedgerBeanLocal.getByGlId(generalLedger);

            budgetControl.setCbTaskId(capitalBudgetTasks);
            budgetControl.setCostCenterId(costCenter);
            budgetControl.setGeneralLedgerId(generalLedger);

            System.out.println(" CbTaskId" + budgetControl.getCbTaskId());
            System.out.println(" CostCenterId" + budgetControl.getCostCenterId());
            System.out.println(" GeneralLedgerId" + budgetControl.getGeneralLedgerId());

            budgetControl.setTotalPaidAmount(new BigDecimal(result[3].toString()));
            budgetControl.setUtilization((budgetControl.getTotalPaidAmount().divide(capitalBudgetTasks.getApprovedAmount(), 2, RoundingMode.HALF_EVEN)).multiply((new BigDecimal(100))));
            cbCompList.add(budgetControl);
        }
        return cbCompList;

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="other methods ">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBudgetControlFacade() {
        super(FmsBudgetControl.class);
    }
    //</editor-fold>

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
}
