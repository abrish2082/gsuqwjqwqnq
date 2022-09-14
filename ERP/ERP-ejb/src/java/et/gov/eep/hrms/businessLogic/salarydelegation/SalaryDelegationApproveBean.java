/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.salarydelegation;

import et.gov.eep.hrms.entity.employee.HrEmpFamilies;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.salarydelegation.HrSalaryDelegationRequest;
import et.gov.eep.hrms.mapper.salarydelegation.HrSalaryDelegationRequestFacade;
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
public class SalaryDelegationApproveBean implements SalaryDelegationApproveBeanLocal {

    @EJB
    HrSalaryDelegationRequestFacade hrSalaryDelegationRequestFacade;

    @Override
    public void edit(HrSalaryDelegationRequest hrSalaryDelegationRequest) {
        hrSalaryDelegationRequestFacade.edit(hrSalaryDelegationRequest);
    }

    @Override
    public List<String> searchAppReList(Integer hrSalaryDelegationRequest) {
        return hrSalaryDelegationRequestFacade.searchAppReList(hrSalaryDelegationRequest);
    }

    @Override
    public HrSalaryDelegationRequest getDeleationInfo(int hrSalaryDelegationRequest) {
        return hrSalaryDelegationRequestFacade.getDeleationInfo(hrSalaryDelegationRequest);
    }

    @Override
    public ArrayList<HrEmpFamilies> searchFamname(HrEmpFamilies hrEmpFamilies) {
        return hrSalaryDelegationRequestFacade.searchFamname(hrEmpFamilies);
    }

    @Override
    public HrEmpFamilies getFamname(HrEmpFamilies hrEmpFamilies) {
        return hrSalaryDelegationRequestFacade.getFamname(hrEmpFamilies);
    }

    @Override
    public List<HrSalaryDelegationRequest> findAll() {
        return hrSalaryDelegationRequestFacade.findAll();
    }

    @Override
    public List<HrSalaryDelegationRequest> findByAll(HrEmployees hrEmp, HrEmployees hrEmployees) {
        return hrSalaryDelegationRequestFacade.findByAll(hrEmp, hrEmployees);
    }

    @Override
    public List<HrSalaryDelegationRequest> findByFirstName(HrEmployees hrEmployees) {
        return hrSalaryDelegationRequestFacade.findByFirstName(hrEmployees);
    }

    @Override
    public List<HrSalaryDelegationRequest> findByEmpName(HrEmployees hrEmployees) {
        return hrSalaryDelegationRequestFacade.findByEmpName(hrEmployees);
    }

    @Override
    public HrSalaryDelegationRequest getSelectedRequest(int request) {
        return hrSalaryDelegationRequestFacade.getSelectedRequest(request);
    }
    
     @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Rejected List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load Resubmit List"));
        return selectItems;
    }

    @Override
    public List<HrSalaryDelegationRequest> loadSalaryDelegationList(int status) {
        return hrSalaryDelegationRequestFacade.loadSalaryDelegationList(status);
    }

}
