/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.businessLogic;

import et.gov.eep.pms.entity.PmsProjectPaymentRequest;
import et.gov.eep.pms.mapper.PmsProjectPaymentRequestFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Me
 */
@Stateless
public class PmsProjectPaymentReqBean implements PmsProjectPaymentReqBeanLocal {

    @EJB
    PmsProjectPaymentRequestFacade pmsProjectPaymentReqFacade;
    
    @Override
    public List<PmsProjectPaymentRequest> fetchNewProjectPayments() {
        return pmsProjectPaymentReqFacade.findNewItems();
    }

    @Override
    public PmsProjectPaymentRequest getSelectePayment(String refeNo) {
        return pmsProjectPaymentReqFacade.getSelectedRequest(refeNo);
    }
}
