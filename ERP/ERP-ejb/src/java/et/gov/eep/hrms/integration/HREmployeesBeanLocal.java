/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.integration;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kimmyo
 */
@Local
public interface HREmployeesBeanLocal {

    /**
     *
     * @param employee
     * @return
     */
    ArrayList<HrEmployees>searchEmployeeInfo(HrEmployees employee);

    /**
     *
     * @param employee
     * @return
     */
    HrEmployees searchById(HrEmployees employee);

    /**
     *
     * @param employee
     * @return
     */
    ArrayList<HrEmployees>searchEmployeeByName(HrEmployees employee);

    /**
     *
     * @param hrEmployees
     * @return
     */
    public HrEmployees getByFirstName(HrEmployees hrEmployees);

    /**
     *
     * @param hrEmployees
     * @return
     */
    public HrEmployees getByEmpId(HrEmployees hrEmployees);

    /**
     *
     * @return
     */
    public List<HrEmployees>GetEmployees();
    
    
    public ArrayList<HrEmployees> SearchByEmpId(HrEmployees hrEmployees);

   
}
