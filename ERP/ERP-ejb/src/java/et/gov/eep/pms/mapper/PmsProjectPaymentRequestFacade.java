/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.pms.entity.PmsProjectPaymentRequest;
import java.util.ArrayList;
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
public class PmsProjectPaymentRequestFacade extends AbstractFacade<PmsProjectPaymentRequest> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmsProjectPaymentRequestFacade() {
        super(PmsProjectPaymentRequest.class);
    }

    public List<PmsProjectPaymentRequest> findNewItems() {
        Query query = em.createNativeQuery("SELECT * FROM PMS_PROJECT_PAYMENT_REQUEST WHERE PMS_PROJECT_PAYMENT_REQUEST.PAYMENT_NO NOT IN "
                + "(SELECT FMS_CHEQUE_PAYMENT_VOUCHER.REFERENCE_NUMBER FROM FMS_CHEQUE_PAYMENT_VOUCHER)", PmsProjectPaymentRequest.class);
        try {
            ArrayList<PmsProjectPaymentRequest> newItems = new ArrayList<>(query.getResultList());
            return newItems;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public PmsProjectPaymentRequest getSelectedRequest(String refeNo) {
        Query query = em.createNamedQuery("PmsProjectPaymentRequest.findByPaymentNo");
        query.setParameter("paymentNo", refeNo);
        try {
            PmsProjectPaymentRequest selectedRequest = (PmsProjectPaymentRequest) query.getResultList().get(0);
            return selectedRequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
