/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsContractCurrDetailFacade extends AbstractFacade<PrmsContractCurrencyDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsContractCurrDetailFacade() {
        super(PrmsContractCurrencyDetail.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public PrmsContractCurrencyDetail findInfoByCurrId(FmsLuCurrency fmsLuCurrency) {

        Query query = em.createNamedQuery("PrmsContractCurrencyDetail.findByCurrency", PrmsContractCurrencyDetail.class);
        query.setParameter("currencyId", fmsLuCurrency);

        try {
            PrmsContractCurrencyDetail importationInfo = (PrmsContractCurrencyDetail) query.getResultList().get(0);
            return importationInfo;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    public List<PrmsContractCurrencyDetail> getByYear(PrmsContract selectYear) {
        List<PrmsContractCurrencyDetail> budgetYearList = null;
        Query query = em.createNativeQuery("select * from prms_contract inner joing "
                + " PRMS_CONTRACT_CURRENCY_DETAIL on n.CONTRACT_ID = d.CONTRACT_ID "
                + " where n.CONTRACT_ID="
                + selectYear, PrmsContractCurrencyDetail.class);
        budgetYearList = (List<PrmsContractCurrencyDetail>) query.getResultList();

        return budgetYearList;
    }
    // </editor-fold>
}
