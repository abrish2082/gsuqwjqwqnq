/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.integration;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.mapper.employee.HrEmployeesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author kimmyo
 */
@Stateless
public class HREmployeesBean implements HREmployeesBeanLocal {

    @EJB
    HrEmployeesFacade EmployeeFacade;

    /**
     *
     * @param employee
     * @return
     */
    @Override
    public ArrayList<HrEmployees> searchEmployeeInfo(HrEmployees employee) {
        return EmployeeFacade.searchEmployeeInfo(employee);
    }

    /**
     *
     * @param employee
     * @return
     */
    @Override
    public HrEmployees searchById(HrEmployees employee) {
        return EmployeeFacade.searchById(employee);

    }

    /**
     *
     * @param employee
     * @return
     */
    @Override
    public ArrayList<HrEmployees> searchEmployeeByName(HrEmployees employee) {
        return EmployeeFacade.searchEmployeeInfo(employee);
    }

    /**
     *
     * @param hrEmployees
     * @return
     */
    @Override
    public HrEmployees getByEmpId(HrEmployees hrEmployees) {
        return EmployeeFacade.getByEmpId(hrEmployees);
    }

    /**
     *
     * @param hrEmployees
     * @return
     */
    @Override
    public HrEmployees getByFirstName(HrEmployees hrEmployees) {
        return EmployeeFacade.getByFirstName(hrEmployees);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrEmployees> GetEmployees() {
        return EmployeeFacade.findAll();
    }

    @Override
    public ArrayList<HrEmployees> SearchByEmpId(HrEmployees hrEmployees) {
        return EmployeeFacade.SearchByEmpId(hrEmployees);
    }

}
