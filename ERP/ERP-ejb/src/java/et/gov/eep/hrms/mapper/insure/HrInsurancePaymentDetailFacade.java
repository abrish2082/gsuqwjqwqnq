/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.insure;

import et.gov.eep.hrms.entity.insurance.HrInsurancePaymentDetail;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author meles
 */
@Stateless
public class HrInsurancePaymentDetailFacade extends AbstractFacade<HrInsurancePaymentDetail> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrInsurancePaymentDetailFacade() {
        super(HrInsurancePaymentDetail.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    public HrInsurancePaymentDetail findMaximumRef() {
        Query query = em.createNamedQuery("HrInsurancePaymentDetail.findByMaximumId");
        HrInsurancePaymentDetail result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (HrInsurancePaymentDetail) query.getResultList().get(0);
            } else {
                return result;
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<HrInsurancePaymentDetail> fetchNewInsurancePayments() {
        List<HrInsurancePaymentDetail> approvedInsurancePayments;
        Query query = em.createNativeQuery("select * from HR_INSURANCE_PAYMENT_DETAIL inner join HR_INSURANCE_PAYMENT on \n"
                + "HR_INSURANCE_PAYMENT_DETAIL.PAYMENT_REQUEST_ID = HR_INSURANCE_PAYMENT.ID where HR_INSURANCE_PAYMENT.STATUS = 1"
                + "and HR_INSURANCE_PAYMENT_DETAIL.MEDICAL_EXPENCE > 10000"
                + "and HR_INSURANCE_PAYMENT_DETAIL.REFERENCE_NO not in (select FMS_CHEQUE_PAYMENT_VOUCHER.REFERENCE_NUMBER  from FMS_CHEQUE_PAYMENT_VOUCHER)", HrInsurancePaymentDetail.class);
        
        approvedInsurancePayments = (List<HrInsurancePaymentDetail>) (query.getResultList());
        return approvedInsurancePayments;
    }
    
    public HrInsurancePaymentDetail getSelectedPayment(String refNo) {
        Query query = em.createNamedQuery("HrInsurancePaymentDetail.findByReferenceNo");
        query.setParameter("referenceNo", refNo);
        try {
            HrInsurancePaymentDetail result = (HrInsurancePaymentDetail) query.getResultList().get(0);
            return result;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public List<HrInsurancePaymentDetail> fetchCPOInsurancePayments() {
        List<HrInsurancePaymentDetail> approvedInsurancePayments;
        Query query = em.createNativeQuery("select * from HR_INSURANCE_PAYMENT_DETAIL inner join HR_INSURANCE_PAYMENT on \n"
                + "HR_INSURANCE_PAYMENT_DETAIL.PAYMENT_REQUEST_ID = HR_INSURANCE_PAYMENT.ID where HR_INSURANCE_PAYMENT.STATUS = 1"
                + "and HR_INSURANCE_PAYMENT_DETAIL.MEDICAL_EXPENCE <= 10000"
                + "and HR_INSURANCE_PAYMENT_DETAIL.REFERENCE_NO not in (select FMS_CASH_PAYMENT_ORDER.REFERENCE_NO from FMS_CASH_PAYMENT_ORDER)", HrInsurancePaymentDetail.class);
        
        approvedInsurancePayments = (List<HrInsurancePaymentDetail>) (query.getResultList());
        return approvedInsurancePayments;
    }
    
//</editor-fold>
}
