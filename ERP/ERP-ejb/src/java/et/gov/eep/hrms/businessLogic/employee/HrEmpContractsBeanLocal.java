/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.employee;

import et.gov.eep.hrms.entity.employee.HrEmpContracts;
import et.gov.eep.hrms.entity.employee.HrEmployees;
//import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrEmpContractsBeanLocal {

    public void create(HrEmpContracts hrEmpContracts);

    public void edit(HrEmpContracts hrEmpContracts);

    public void SaveOrUpdate(HrEmpContracts hrEmpContracts);

    public int updatestate(HrEmployees hrEmployees);

    public ArrayList<HrEmpContracts> searchbyempforarray(HrEmpContracts hrEmpContracts);
    
   public HrEmpContracts  searchByEmpSt(HrEmployees hrEmployees);

}
