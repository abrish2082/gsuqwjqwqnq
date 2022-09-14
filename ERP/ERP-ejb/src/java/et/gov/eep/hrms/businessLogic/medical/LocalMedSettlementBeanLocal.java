/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.hrms.entity.medical.HrLocalMedSettlementDetail;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Me
 */
@Local
public interface LocalMedSettlementBeanLocal {
    public List<HrLocalMedSettlementDetail>fetchApprovedPayments();
    public HrLocalMedSettlementDetail fetchPayment(HrLocalMedSettlements hrLocalMedSettlements);
    public List<HrLocalMedSettlementDetail>fetchApprovedPaymentsForCashOrder();
}
