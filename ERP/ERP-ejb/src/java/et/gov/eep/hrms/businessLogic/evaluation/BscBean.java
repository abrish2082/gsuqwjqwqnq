/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrBsc;
import et.gov.eep.hrms.mapper.evaluation.HrBscFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author INSA
 */
@Stateless
public class BscBean implements BscBeanLocal {

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
    
     @Override
    public ArrayList<HrEmployees> searchEmp(String hrEmployee) {
       return hrBscFacade.searchEmp(hrEmployee);         
    }
    
     @Override
    public HrEmployees getByFName(HrEmployees hrEmployees) {
         return hrBscFacade.getByFName(hrEmployees);
    }
    
}
