/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.businessLogic;

import et.gov.eep.pms.entity.PmsProjectPaymentRequest;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Me
 */
@Local
public interface PmsProjectPaymentReqBeanLocal {
    
    public List<PmsProjectPaymentRequest> fetchNewProjectPayments();
    
    public PmsProjectPaymentRequest getSelectePayment(String refeNo);
}
