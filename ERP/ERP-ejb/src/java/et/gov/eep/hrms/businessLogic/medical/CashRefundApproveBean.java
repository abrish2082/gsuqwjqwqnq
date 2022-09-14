/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import et.gov.eep.hrms.mapper.medical.HrLocalMedSettlementsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Stateless
public class CashRefundApproveBean implements CashRefundApproveBeanLocal {

    @EJB
    HrLocalMedSettlementsFacade hrLocalMedSettlementsFacade;

    @Override
    public void saveOrUpdate(HrLocalMedSettlements hrLocalMedSettlements) {
        hrLocalMedSettlementsFacade.saveOrUpdate(hrLocalMedSettlements);
    }

    @Override
    public ArrayList<HrLocalMedSettlements> findAllRequest() {
        return hrLocalMedSettlementsFacade.findAll();
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf(0), "Load request list"));
        selectItems.add(new SelectItem(String.valueOf(1), "Load approved list"));
        selectItems.add(new SelectItem(String.valueOf(2), "Load rejected list"));
        return selectItems;
    }

    @Override
    public List<HrLocalMedSettlements> loadMedicaCashRefund(int status) {
        return hrLocalMedSettlementsFacade.loadMedicaCashRefund(status);
    }

    @Override
    public List<HrLocalMedSettlements> loadMedSettlementList(Status status, int userId) {
        return hrLocalMedSettlementsFacade.loadMedSettlementList(status, userId);
    }

    @Override
    public HrLocalMedSettlements getSelectedRequest(int request) {
        return hrLocalMedSettlementsFacade.getSelectedRequest(request);
    }

    @Override
    public HrLocalMedSettlements fetchSettlement(String refNo) {
        return hrLocalMedSettlementsFacade.fetchSettlement(refNo);
    }

    @Override
    public List<HrLocalMedSettlements> findPreparedList() {
        return hrLocalMedSettlementsFacade.findPreparedList();
    }

}
