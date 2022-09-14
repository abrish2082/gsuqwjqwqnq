/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.Voucher;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//</editor-fold>

@Stateless
public class FmsCashReceiptVoucherFacade extends AbstractFacade<FmsCashReceiptVoucher> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    //<editor-fold defaultstate="collapsed" desc="other methods ">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public FmsCashReceiptVoucherFacade() {
        super(FmsCashReceiptVoucher.class);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Named Queries">
    /**
     *
     * @param approveStatus
     * @return
     */
    public List<String> getPrepareList(int approveStatus) {

        List<String> ChequePaymentList = null;
        try {
            Query query = em.createNamedQuery("FmsCashPaymentOrder.findByStatus", FmsCashReceiptVoucher.class);
            query.setParameter("status", approveStatus);
            ChequePaymentList = (List<String>) query.getResultList();
            return ChequePaymentList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

}
