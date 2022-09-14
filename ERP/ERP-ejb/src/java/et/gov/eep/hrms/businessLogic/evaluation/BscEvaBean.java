/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrBsc;
import et.gov.eep.hrms.entity.evaluation.HrBscSessions;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.evaluation.HrBscFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author INSA
 */
@Stateless
public class BscEvaBean implements BscEvaBeanLocal {

    @EJB
    HrBscFacade hrBscFacade;

    @Override
    public void save(HrBsc hrBsc) {
        hrBscFacade.create(hrBsc);
    }

    @Override
    public void edit(HrBsc hrBsc) {
        hrBscFacade.edit(hrBsc);
    }

     public void saveOrUpdate(HrBsc hrBsc) {
        hrBscFacade.saveOrUpdate(hrBsc);
    }
    
    @Override
    public List<HrBscSessions> readActiveSession(String toDayInEC) {
        return hrBscFacade.readActiveSession(toDayInEC);
    }

    @Override
    public HrBscSessions findById(HrBscSessions hrBscSessions) {
        return hrBscFacade.findById(hrBscSessions);
    }

    @Override
    public List<HrEmployees> findEmployee(HrEmployees hrEmployees) {
        return hrBscFacade.findEmployee(hrEmployees);
    }

    @Override
     public HrDepartments findByDepartmentId(HrDepartments hrDepartments) {
       return hrBscFacade.findByDepartmentId(hrDepartments);
     }
    
    @Override
    public ArrayList<HrEmployees> searchEmp(String hrEmployee) {
        return hrBscFacade.searchEmp(hrEmployee);
    }

    @Override
    public HrEmployees getByFName(HrEmployees hrEmployees) {
        return hrBscFacade.getByFName(hrEmployees);
    }

    @Override
    public ArrayList<HrBsc> findAllBscResult() {
        return hrBscFacade.findAllBscResult();
    }

    @Override
    public List<HrBsc> findByDepName(HrDepartments hrDepartments) {
        return hrBscFacade.findByDepName(hrDepartments);
    }

    @Override
    public HrBsc getSelectedResult(int result) {
        return hrBscFacade.getSelectedBSCResult(result);
    }

}
