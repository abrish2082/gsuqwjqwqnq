/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.integration;

import et.gov.eep.hrms.entity.training.HrTdIspPaymentDetails;
import et.gov.eep.hrms.mapper.training.HrTdIspPaymentDetailsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Me
 */
@Stateless
public class HrIspDetailBean implements HrIspDetailBeanLocal {

    @EJB
    HrTdIspPaymentDetailsFacade hrTdIspPaymentDetailsFacade;

    @Override
    public List<HrTdIspPaymentDetails> fetchCHPOInternshipPayments() {
        return hrTdIspPaymentDetailsFacade.fetchCHPOInternshipPayments();
    }

    @Override
    public List<HrTdIspPaymentDetails> fetchCPOInternshipPayments() {
        return hrTdIspPaymentDetailsFacade.fetchCPOInternshipPayments();
    }

    @Override
    public HrTdIspPaymentDetails getSelectedPayment(String refNo) {
        return hrTdIspPaymentDetailsFacade.getSelectedRequest(refNo);
    }
    
    
}
