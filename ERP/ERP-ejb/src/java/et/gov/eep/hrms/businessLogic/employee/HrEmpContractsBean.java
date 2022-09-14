/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.employee;

import et.gov.eep.hrms.entity.employee.HrEmpContracts;
import et.gov.eep.hrms.entity.employee.HrEmployees;

import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import et.gov.eep.hrms.mapper.employee.HrEmpContractsFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrEmpContractsBean implements HrEmpContractsBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    HrEmpContractsFacade hrEmpContractsFacade;
    @Override
    public void create(HrEmpContracts hrEmpContracts) {
        hrEmpContractsFacade.create(hrEmpContracts);
    }

    @Override
    public void edit(HrEmpContracts hrEmpContracts) {
        hrEmpContractsFacade.edit(hrEmpContracts);
    }

    @Override
    public void SaveOrUpdate(HrEmpContracts hrEmpContracts) {
        hrEmpContractsFacade.saveOrUpdate(hrEmpContracts);
    }
     @Override
   public int updatestate(HrEmployees hrEmployees){
   hrEmpContractsFacade.updatestate(hrEmployees);
        return 0;
   }
    @Override
    public ArrayList<HrEmpContracts>  searchbyempforarray(HrEmpContracts hrEmpContracts) {
        return hrEmpContractsFacade.searchbyempforarray(hrEmpContracts);
    }
     @Override
    public HrEmpContracts  searchByEmpSt(HrEmployees hrEmployees) {
        return hrEmpContractsFacade.searchByEmpSt(hrEmployees);
    }
    
   
           
    
             /**
//     *
//     * @param 
//     * @return
//     */
//    @Override
//    public ArrayList<HrEmpContracts> findbyempid(int id) {
//        return hrEmpContractsFacade.getContratlist(id);
//    }



}
