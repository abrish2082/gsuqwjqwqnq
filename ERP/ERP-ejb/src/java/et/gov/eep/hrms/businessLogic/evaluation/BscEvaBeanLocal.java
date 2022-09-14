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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author INSA
 */
@Local
public interface BscEvaBeanLocal {

    public void save(HrBsc hrBsc);

    public void edit(HrBsc hrBsc);

    public void saveOrUpdate(HrBsc hrBsc);

    public List<HrBscSessions> readActiveSession(String toDayInEC);

    public HrBscSessions findById(HrBscSessions hrBscSessions);

    public List<HrEmployees> findEmployee(HrEmployees hrEmployees);

    public HrDepartments findByDepartmentId(HrDepartments hrDepartments);

    public ArrayList<HrEmployees> searchEmp(String hrEmployee);

    public HrEmployees getByFName(HrEmployees hrEmployees);

    public ArrayList<HrBsc> findAllBscResult();

    public List<HrBsc> findByDepName(HrDepartments hrDepartments);

    public HrBsc getSelectedResult(int result);

}
