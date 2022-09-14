package et.gov.eep.fcms.mapper.budget;

//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//</editor-fold>

@Stateless
public class FmsBudgetCodeFacade extends AbstractFacade<FmsBudgetCode> {

    //<editor-fold defaultstate="collapsed" desc="NamedQuery ">

    public FmsBudgetCode getBudgetCode(FmsBudgetCode bgtCode) {
        Query query = em.createNamedQuery("FmsBudgetCode.findByBudgetCode");
        query.setParameter("budgetCode", bgtCode.getBudgetCode());
        try {
            FmsBudgetCode glList = (FmsBudgetCode) query.getResultList().get(0);
            return glList;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param budgetCode
     * @return
     */
    public ArrayList<FmsBudgetCode> searchBudgetCode(FmsBudgetCode budgetCode) {
        //accessing e 
        Query query = em.createNamedQuery("FmsBudgetCode.findByBudgetCodeLike");
        query.setParameter("budgetCode", budgetCode.getBudgetCode());
        try {
            ArrayList<FmsBudgetCode> BudgetCodeList = new ArrayList(query.getResultList());
            return BudgetCodeList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public FmsBudgetCode searchBgtCode(FmsBudgetCode bgtCode) {
        Query query = em.createNamedQuery("FmsBudgetCode.findByBudgetCode");
        query.setParameter("budgetCode", bgtCode.getBudgetCode());
        try {
            FmsBudgetCode glList = (FmsBudgetCode) query.getResultList().get(0);
            if (query.getResultList().isEmpty()) {
            } else if (query.getResultList().size() > 0) {
                return glList;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public FmsBudgetCode getSelectedRequest(int request) {
        Query query = em.createNamedQuery("FmsBudgetCode.findByBudgetId");
        query.setParameter("budgetId", request);
        try {
            FmsBudgetCode selectrequest = (FmsBudgetCode) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public FmsBudgetCode findBudgetCode(FmsBudgetCode budgetCode) {
        Query query = em.createNamedQuery("FmsBudgetCode.findByBudgetCode");
        query.setParameter("budgetCode", budgetCode.getBudgetCode());
        try {
            FmsBudgetCode selectrequest = (FmsBudgetCode) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<FmsBudgetCode> findAllCapital() {
        Query query = em.createNamedQuery("FmsBudgetCode.findByType");
        query.setParameter("type", "Capital");
        List<FmsBudgetCode> fmsBudgetCodeList = new ArrayList<>();
        fmsBudgetCodeList = query.getResultList();
        return fmsBudgetCodeList;
    }
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="other methods ">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBudgetCodeFacade() {
        super(FmsBudgetCode.class);
    }
    //</editor-fold>

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    public List<FmsBudgetCode> getFmsBudgetCodeSearchingParameterList() {  Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('FMS_BUDGET_CODE')\n"
                + "and column_name NOT IN('GENERAL_LEDGER' ,'BUDGET_ID') ORDER BY column_name ASC");
        try {
            List<FmsBudgetCode> searchParamLists = new ArrayList<>();
            searchParamLists = query.getResultList();
            return searchParamLists;
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
    }
    
//       public List<FmsVoucher> getFmsVoucherSearchingParameterList() {
//        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
//                + "WHERE table_name = UPPER('FMS_VOUCHER')\n"
//                + "and column_name NOT IN('VOUCHER_ID' ,'SystemNo','STATUS' ,'TYPE_','REASON' , 'ProcessedBy' , 'PREPARED_DATE' , 'PREPARED_BY' , 'PREPARED_REMARK' , 'VAT_WITHOLD_STATUS' , 'PROCESSED_DATE' ) ORDER BY column_name ASC");
//        try {
//            List<FmsVoucher> searchParamLists = new ArrayList<>();
//            searchParamLists = query.getResultList();
//            return searchParamLists;
//        } catch (Exception ex) {
//            ex.getMessage();
//            return null;
//        }
//    }


}
