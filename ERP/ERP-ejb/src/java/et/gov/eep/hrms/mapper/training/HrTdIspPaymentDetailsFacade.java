/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdIspPaymentDetails;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Me
 */
@Stateless
public class HrTdIspPaymentDetailsFacade extends AbstractFacade<HrTdIspPaymentDetails> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdIspPaymentDetailsFacade() {
        super(HrTdIspPaymentDetails.class);
    }

    public HrTdIspPaymentDetails getSelectedRequest(String refNo) {
        Query query = em.createNamedQuery("HrTdIspPaymentDetails.findByReferenceNo");
        query.setParameter("referenceNo", refNo);
        try {
            HrTdIspPaymentDetails selectrequest = (HrTdIspPaymentDetails) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Native query">

    public List<HrTdIspPaymentDetails> fetchCHPOInternshipPayments() {
        List<HrTdIspPaymentDetails> approvedInternshipPayments;
        Query query = em.createNativeQuery("select * from HR_TD_ISP_PAYMENT_DETAILS ISP where ISP.AMOUNT_PAID > 10000 AND\n"
                + "ISP.REFERNCE_NO not in (select FMS_CHEQUE_PAYMENT_VOUCHER.REFERENCE_NUMBER  from FMS_CHEQUE_PAYMENT_VOUCHER)", HrTdIspPaymentDetails.class);

        approvedInternshipPayments = (List<HrTdIspPaymentDetails>) (query.getResultList());
        return approvedInternshipPayments;
    }

    public List<HrTdIspPaymentDetails> fetchCPOInternshipPayments() {
        List<HrTdIspPaymentDetails> approvedInternshipPayments;
        Query query = em.createNativeQuery("select * from HR_TD_ISP_PAYMENT_DETAILS ISP where ISP.AMOUNT_PAID <= 10000 AND\n"
                + "ISP.REFERNCE_NO not in (select FMS_CASH_PAYMENT_ORDER.REFERENCE_NO from FMS_CASH_PAYMENT_ORDER)", HrTdIspPaymentDetails.class);

        approvedInternshipPayments = (List<HrTdIspPaymentDetails>) (query.getResultList());
        return approvedInternshipPayments;
    }
    //</editor-fold>
}
