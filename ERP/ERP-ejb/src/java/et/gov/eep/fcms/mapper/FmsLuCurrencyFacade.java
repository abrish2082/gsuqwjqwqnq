/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mora
 */
@Stateless
public class FmsLuCurrencyFacade extends AbstractFacade<FmsLuCurrency> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public FmsLuCurrencyFacade() {
        super(FmsLuCurrency.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named Queries">
    /**
     *
     * @param currency
     * @return
     */
    public ArrayList<FmsLuCurrency> searchFmsLuCurrency(FmsLuCurrency currency) {
        Query query = em.createNamedQuery("FmsLuCurrency.findByNameLike");
        query.setParameter("currency", currency.getCurrency() + '%');
        try {
            ArrayList<FmsLuCurrency> bondTypeList = new ArrayList(query.getResultList());
            return bondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param currency
     * @return
     */
    public FmsLuCurrency getFmsLuCurrencyInfo(FmsLuCurrency currency) {
        Query query = em.createNamedQuery("FmsLuCurrency.findByCurrency");
        query.setParameter("currency", currency.getCurrency());
        if (!query.getResultList().isEmpty()) {
            FmsLuCurrency luCurrency = (FmsLuCurrency) query.getResultList().get(0);
            return luCurrency;
        } else {
            return null;
        }
    }

    public FmsLuCurrency getByCurrencyId(FmsLuCurrency freightCurrency) {
        Query queries = em.createNamedQuery("FmsLuCurrency.findByCurrencyId");
        queries.setParameter("currencyId", freightCurrency.getCurrency());
        try {
            FmsLuCurrency changingCurrencies = null;
            if (queries.getResultList().size() > 0) {
                changingCurrencies = (FmsLuCurrency) queries.getResultList().get(0);
            }
            return changingCurrencies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//getting Currency Name in Ascending order
    public List<FmsLuCurrency> currencyNames() {
        Query query = em.createNamedQuery("FmsLuCurrency.searchByCurrencyNameOrder");
        try {
            List<FmsLuCurrency> currency = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                currency = query.getResultList();
            }
            return currency;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//getting Currency Lists
    public ArrayList<FmsLuCurrency> getAllCurrencyList(FmsLuCurrency currency) {

        Query query = em.createNamedQuery("FmsLuCurrency.findAll");
        try {
            ArrayList<FmsLuCurrency> LubondTypeList = new ArrayList(query.getResultList());
            return LubondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<FmsLuCurrency> getCurrencyInBirr() {

        Query query = em.createNativeQuery("SELECT FMS_LU_CURRENCY.CURRENCY_ID,\n"
                + "  FMS_LU_CURRENCY.CURRENCY\n"
                + "FROM FMS_LU_CURRENCY\n"
                + "where  FMS_LU_CURRENCY.CURRENCY='ETB'", FmsLuCurrency.class);
        try {
            List<FmsLuCurrency> birr = new ArrayList<>();
            birr = new ArrayList<>(query.getResultList());
            return birr;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    //get FmsLuCurrency objects By ItemId
    public FmsLuCurrency getCurrenyByFrmEvalByItemId(MmsItemRegistration mmsItemRegistration) {
        Query query = em.createNativeQuery("SELECT FMS_LU_CURRENCY.CURRENCY,\n"
                + "  FMS_LU_CURRENCY.CURRENCY_ID\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_FINANCIAL_EVALUA_DETAIL.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FINANCIAL_EVALUATION_DTL_ID\n"
                + "INNER JOIN FMS_LU_CURRENCY\n"
                + "ON FMS_LU_CURRENCY.CURRENCY_ID = PRMS_FINANCIAL_EVALUA_DETAIL.CURRENCY_ID\n"
                + "WHERE MMS_ITEM_REGISTRATION.MATERIAL_ID = '" + mmsItemRegistration.getMaterialId() + "'", FmsLuCurrency.class);
        try {
            FmsLuCurrency fmsLuCurrencys = new FmsLuCurrency();
            if (query.getResultList().size() > 0) {
                fmsLuCurrencys = (FmsLuCurrency) (query.getResultList().get(0));
            }
            System.out.println("---currency Name---===----" + fmsLuCurrencys);
            return fmsLuCurrencys;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //get FmsLuCurrency objects By ServiceOrWorkRegId
    public FmsLuCurrency getCurrenyFrServiceByServiceOrWorkRegId(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        Query query = em.createNativeQuery("SELECT FMS_LU_CURRENCY.CURRENCY,\n"
                + "  FMS_LU_CURRENCY.CURRENCY_ID\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG \n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID  = PRMS_FINANCIAL_EVL_RESULTY_DTL.SERVICE_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_FINANCIAL_EVALUA_DETAIL.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FINANCIAL_EVALUATION_DTL_ID\n"
                + "INNER JOIN FMS_LU_CURRENCY\n"
                + "ON FMS_LU_CURRENCY.CURRENCY_ID = PRMS_FINANCIAL_EVALUA_DETAIL.CURRENCY_ID\n"
                + "WHERE PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID  = '" + prmsServiceAndWorkReg.getServAndWorkId() + "'", FmsLuCurrency.class);
        try {
            FmsLuCurrency fmsLuCurrencys = new FmsLuCurrency();
            if (query.getResultList().size() > 0) {
                fmsLuCurrencys = (FmsLuCurrency) (query.getResultList().get(0));
            }
            return fmsLuCurrencys;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
    // </editor-fold>
}
