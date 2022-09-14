/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceSettlement;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.fcms.mapper.perDiem.FmsFieldAllowanceSettlementFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author muller
 */
@Stateless
public class FmsFieldAllowanceSettlementBean implements FmsFieldAllowanceSettlementBeanLocal {

    @EJB
    FmsFieldAllowanceSettlementFacade allowanceSettlementFacade;

    @Override
    public List<et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceSettlement> findEmpId(et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceSettlement allowanceSettlement) {
        return allowanceSettlementFacade.listOfEmp(allowanceSettlement);
    }

    @Override
    public List<FmsFieldAllowance> findByWfStatusApproved(FmsFieldAllowance fmsFieldAllowanceEnty, int wfStatusApproved) {
        return allowanceSettlementFacade.findByWfStatusApproved(fmsFieldAllowanceEnty, wfStatusApproved);
    }

    @Override
    public FmsFieldAllowance findByempIdAndStatus(FmsFieldAllowance fmsFieldAllowanceEnty) {
        return allowanceSettlementFacade.findByempIdAndStatus(fmsFieldAllowanceEnty);
    }

    @Override
    public FmsLuPerdimeRate getPer(FmsLuPerdimeRate fmsLuPerdimeRate) {
        return allowanceSettlementFacade.search(fmsLuPerdimeRate);
    }

    @Override
    public void create(FmsFieldAllowanceSettlement allowanceSettlement) {
        allowanceSettlementFacade.create(allowanceSettlement);
    }

    @Override
    public void edit(FmsFieldAllowanceSettlement allowanceSettlement) {
        allowanceSettlementFacade.edit(allowanceSettlement);
    }

    @Override
    public List<FmsFieldAllowanceSettlement> getEmplloyeId(FmsFieldAllowanceSettlement allowanceSettlement) {
        return allowanceSettlementFacade.getEmplloyeId(allowanceSettlement);
    }

    @Override
    public List<FmsFieldAllowanceSettlement> searchSettlementByParameter(FmsFieldAllowanceSettlement allowanceSettlement) {
        return allowanceSettlementFacade.searchSettlementByParameter(allowanceSettlement);
    }

    @Override
    public List<FmsFieldAllowanceSettlement> searchAll() {
        return allowanceSettlementFacade.findAll();
    }

    @Override
    public FmsFieldAllowanceSettlement getById(FmsFieldAllowanceSettlement allowanceSettlement) {
        return allowanceSettlementFacade.getDataById(allowanceSettlement);
    }

    @Override
    public List<FmsFieldAllowanceSettlement> searchAllEmployee() {
        return allowanceSettlementFacade.findAll();
    }

    @Override
    public List<FmsFieldAllowanceSettlement> searchEmpByEmpName(HrEmployees empEnty) {
        return allowanceSettlementFacade.searchEmpByEmpName(empEnty);
    }

    @Override
    public List<FmsFieldAllowanceSettlement> searchEmployeeByEmpId(HrEmployees empEnty) {
        return allowanceSettlementFacade.searchEmployeeByEmpId(empEnty);
    }

    @Override
    public List<FmsFieldAllowanceSettlement> searchSettlementtVocher() {
        return allowanceSettlementFacade.searchSettlementtVocher();
    }

    @Override
    public FmsFieldAllowanceSettlement searchSetlementchekeVocher(int id) {
        return allowanceSettlementFacade.searchSetlementchekeVocher(id);
    }

    @Override
    public List<FmsFieldAllowanceSettlement> findFaByWfStatus(int status) {
        return allowanceSettlementFacade.findFaByWfStatus(status);
    }
}
