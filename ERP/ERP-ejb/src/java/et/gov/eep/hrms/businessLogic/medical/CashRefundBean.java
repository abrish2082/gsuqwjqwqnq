/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import et.gov.eep.hrms.mapper.medical.HrLocalMedInstitutionsFacade;
import et.gov.eep.hrms.mapper.medical.HrLocalMedSettlementsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.Query;

/**
 *
 * @author INSA
 */
@Stateless
public class CashRefundBean implements CashRefundBeanLocal {

    @EJB
    HrLocalMedSettlementsFacade hrLocalMedSettlementsFacade;
    @EJB
    HrLocalMedInstitutionsFacade hrLocalMedInstitutionsFacade;

    @Override
    public void save(HrLocalMedSettlements hrLocalMedSettlements) {
        hrLocalMedSettlementsFacade.create(hrLocalMedSettlements);
    }

    @Override
    public void edit(HrLocalMedSettlements HrLocalMedSettlements) {
        hrLocalMedSettlementsFacade.edit(HrLocalMedSettlements);
    }

    @Override
    public void saveOrUpdate(HrLocalMedSettlements hrLocalMedSettlements) {
        hrLocalMedSettlementsFacade.saveOrUpdate(hrLocalMedSettlements);
    }

    @Override
    public List<HrLocalMedInstitutions> findAll() {
        return hrLocalMedInstitutionsFacade.findAll();
    }

    @Override
    public ArrayList<HrLocalMedSettlements> findAllRequest() {
        return hrLocalMedSettlementsFacade.findAll();
    }

    @Override
    public ArrayList<HrLocalMedSettlements> findByName(HrEmployees empName) {
        return hrLocalMedSettlementsFacade.findByName(empName);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load request list"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load approved list"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load all list"));
        return selectItems;
    }

    @Override
    public List<HrLocalMedSettlements> loadMedSettlementList(Status status, int userId) {
        return hrLocalMedSettlementsFacade.loadMedSettlementList(status, userId);
    }

    @Override
    public List<HrLocalMedSettlements> loadSettlementList(Status status, int userId) {
        return hrLocalMedSettlementsFacade.loadSettlementList(status, userId);
    }

    @Override
    public List<HrLocalMedSettlements> loadMedicaCashRefund(int status) {
        return hrLocalMedSettlementsFacade.loadMedicaCashRefund(status);
    }

    @Override
    public HrLocalMedSettlements getSelectedRequest(int request) {
        return hrLocalMedSettlementsFacade.getSelectedRequest(request);
    }

    @Override
    public List<HrLocalMedSettlements> loadPrepaMedSettlementList(HrLocalMedSettlements hrLocalMedSettlements) {
        return hrLocalMedSettlementsFacade.loadPrepaMedSettlementList(hrLocalMedSettlements);
    }

    @Override
    public List<HrLocalMedSettlements> loadReqMedSettList(Status status, int userId) {
        return hrLocalMedSettlementsFacade.loadReqMedSettList(status, userId);
    }

    @Override
    public List<HrLocalMedSettlements> loadApproveMedSettList(Status status, int userId) {
        return hrLocalMedSettlementsFacade.loadApproveMedSettList(status, userId);
    }

}
