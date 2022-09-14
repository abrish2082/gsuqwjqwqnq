/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.hrms.entity.medical.HrLocalMedSettlementDetail;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import et.gov.eep.hrms.mapper.medical.HrLocalMedSettlementDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Me
 */
@Stateless
public class LocalMedSettlementBean implements LocalMedSettlementBeanLocal {

    @EJB
    HrLocalMedSettlementDetailFacade hrLocalMedSettlementDetailFacade;
    
    @Override
    public List<HrLocalMedSettlementDetail> fetchApprovedPayments(){
        return hrLocalMedSettlementDetailFacade.fetchApprovedPayments();
    }
    
    @Override
    public HrLocalMedSettlementDetail fetchPayment(HrLocalMedSettlements hrLocalMedSettlements){
        return hrLocalMedSettlementDetailFacade.fetchPayment(hrLocalMedSettlements);
    }
    
    @Override
    public List<HrLocalMedSettlementDetail> fetchApprovedPaymentsForCashOrder() {
        return hrLocalMedSettlementDetailFacade.fetchApprovedPaymentsForCashOrder();
    }
}
