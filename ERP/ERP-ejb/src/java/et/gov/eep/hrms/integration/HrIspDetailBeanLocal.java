/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.integration;

import et.gov.eep.hrms.entity.training.HrTdIspPaymentDetails;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Me
 */
@Local
public interface HrIspDetailBeanLocal {
    public List<HrTdIspPaymentDetails> fetchCHPOInternshipPayments();
    public List<HrTdIspPaymentDetails> fetchCPOInternshipPayments();
    public HrTdIspPaymentDetails getSelectedPayment(String refNo);
}
