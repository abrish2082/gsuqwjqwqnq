/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.salarydelegation;

import et.gov.eep.hrms.entity.employee.HrEmpFamilies;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.salarydelegation.HrSalaryDelegationRequest;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Local
public interface HrSalaryDelegationRequestBeanLocal {

    public void create(HrSalaryDelegationRequest hrSalaryDelegationRequest);

    public void edit(HrSalaryDelegationRequest hrSalaryDelegationRequest);

    public void remove(HrSalaryDelegationRequest hrSalaryDelegationRequest);

    public HrSalaryDelegationRequest findSalaryDelById(HrEmployees id);

    public ArrayList<HrSalaryDelegationRequest> searchByFirstName(HrSalaryDelegationRequest hrSalaryDelegationRequest);

    public HrSalaryDelegationRequest getSalaryInfo(HrSalaryDelegationRequest hrSalaryDelegationRequest);

    public ArrayList<HrSalaryDelegationRequest> SearchByEmpId(HrSalaryDelegationRequest hrSalaryDelegationRequest);

    public List<HrSalaryDelegationRequest> requestListByStatus(HrSalaryDelegationRequest hrSalaryDelegationRequest);

    public List<String> searchByName(Integer hrSalaryDelegationRequest);

    public List<String> searchAppReList(Integer hrSalaryDelegationRequest);
    
    public HrSalaryDelegationRequest getDeleationInfo(int hrSalaryDelegationRequest);

    public void save(HrSalaryDelegationRequest hrSalaryDelegationRequest);

    public ArrayList<HrEmpFamilies> searchFamname(HrEmpFamilies hrEmpFamilies);

    public HrEmpFamilies getFamname(HrEmpFamilies hrEmpFamilies);

    public List<HrSalaryDelegationRequest> findAll();

    public List<HrSalaryDelegationRequest> findByAll(HrEmployees hrEmp, HrEmployees hrEmployees);

    public List<HrSalaryDelegationRequest> findByFirstName(HrEmployees hrEmployees);

    public List<HrSalaryDelegationRequest> findByEmpName(HrEmployees hrEmployees);

    public HrSalaryDelegationRequest getSelectedRequest(int request);

    public List<SelectItem> filterByStatus();

    public List<HrSalaryDelegationRequest> loadSalaryDelegationList(int status);

}
