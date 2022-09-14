/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.EmployeeBonus;

import et.gov.eep.hrms.entity.EmployeeBonus.HrEmployeesBonus;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Local
public interface EmployeesBeanLocal {

    public void saveorupdate(HrEmployeesBonus hrEmployeesBonus);

    public HrEmployeesBonus findByBgtYear(String bgtYr);

    public List<HrEmployees> searchEmployee(Integer status2, Integer status, HrEmployees hremployees);

    public void edit(HrEmployeesBonus hrEmployeesBonus);

    public List<SelectItem> filterByStatus();

    public List<HrEmployeesBonus> loadFiltereddata(int status);

    public void saveUpdate(HrEmployeesBonus hrEmployeesBonus);

    public HrEmployeesBonus getSelectedRequest(BigDecimal id);
    
}
