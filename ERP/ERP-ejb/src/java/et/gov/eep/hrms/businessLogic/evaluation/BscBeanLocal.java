/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrBsc;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author INSA
 */
@Local
public interface BscBeanLocal {

    public void edit(HrBsc hrBsc);

    public void save(HrBsc hrBsc);

    public ArrayList<HrEmployees> searchEmp(String hrEmployee);

    public HrEmployees getByFName(HrEmployees hrEmployees);
    
}
