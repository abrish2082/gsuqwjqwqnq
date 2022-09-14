/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.medical;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlementDetail;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
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
public class HrLocalMedSettlementDetailFacade extends AbstractFacade<HrLocalMedSettlementDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLocalMedSettlementDetailFacade() {
        super(HrLocalMedSettlementDetail.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Native query">

    public List<HrLocalMedSettlementDetail> fetchApprovedPayments() {
        List<HrLocalMedSettlementDetail> approvedPayments;
        Query query = em.createNativeQuery("select HR_LOCAL_MED_SETTLEMENTS.ID , SUM(HR_LOCAL_MED_SETTLEMENT_DETAIL.AMOUNT)AMOUNT \n"
                + "from HR_LOCAL_MED_SETTLEMENTS join HR_LOCAL_MED_SETTLEMENT_DETAIL \n"
                + "on HR_LOCAL_MED_SETTLEMENT_DETAIL.SETTLEMENT_ID = HR_LOCAL_MED_SETTLEMENTS.ID \n"
                + "where HR_LOCAL_MED_SETTLEMENTS.REFERENCE_NO not in(select FMS_CHEQUE_PAYMENT_VOUCHER.REFERENCE_NUMBER from FMS_CHEQUE_PAYMENT_VOUCHER) \n"
                + "group by HR_LOCAL_MED_SETTLEMENTS.ID HAVING SUM(HR_LOCAL_MED_SETTLEMENT_DETAIL.AMOUNT)>10000", HrLocalMedSettlementDetail.class);
        approvedPayments = (List<HrLocalMedSettlementDetail>) (query.getResultList());
        return approvedPayments;
    }

    public HrLocalMedSettlementDetail fetchPayment(HrLocalMedSettlements hrLocalMedSettlements) {
        Query query = em.createNativeQuery("select HR_LOCAL_MED_SETTLEMENTS.ID , SUM(HR_LOCAL_MED_SETTLEMENT_DETAIL.AMOUNT)AMOUNT \n"
                + "from HR_LOCAL_MED_SETTLEMENTS join HR_LOCAL_MED_SETTLEMENT_DETAIL\n"
                + "on HR_LOCAL_MED_SETTLEMENT_DETAIL.SETTLEMENT_ID = HR_LOCAL_MED_SETTLEMENTS.ID\n"
                + "group by HR_LOCAL_MED_SETTLEMENTS.ID HAVING (HR_LOCAL_MED_SETTLEMENT_DETAIL.SETTLEMENT_ID = '" + hrLocalMedSettlements.getId() + "') ", HrLocalMedSettlementDetail.class);
        HrLocalMedSettlementDetail payment = (HrLocalMedSettlementDetail) query.getResultList().get(0);
        return payment;
    }

    public List<HrLocalMedSettlementDetail> fetchApprovedPaymentsForCashOrder() {
        List<HrLocalMedSettlementDetail> approvedPayments;
        Query query = em.createNativeQuery("select HR_LOCAL_MED_SETTLEMENTS.ID , SUM(HR_LOCAL_MED_SETTLEMENT_DETAIL.AMOUNT)AMOUNT\n"
                + "from HR_LOCAL_MED_SETTLEMENTS join HR_LOCAL_MED_SETTLEMENT_DETAIL\n"
                + "on HR_LOCAL_MED_SETTLEMENT_DETAIL.SETTLEMENT_ID = HR_LOCAL_MED_SETTLEMENTS.ID\n"
                + "where HR_LOCAL_MED_SETTLEMENTS.REFERENCE_NO not in(select FMS_CASH_PAYMENT_ORDER.REFERENCE_NO from FMS_CASH_PAYMENT_ORDER)\n"
                + "group by HR_LOCAL_MED_SETTLEMENTS.ID HAVING SUM(HR_LOCAL_MED_SETTLEMENT_DETAIL.AMOUNT)<=10000", HrLocalMedSettlementDetail.class);
        approvedPayments = (List<HrLocalMedSettlementDetail>) (query.getResultList());
        return approvedPayments;
    }
    //</editor-fold>
}
