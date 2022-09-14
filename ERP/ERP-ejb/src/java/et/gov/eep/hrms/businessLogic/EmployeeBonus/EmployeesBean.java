/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.EmployeeBonus;

import et.gov.eep.hrms.entity.EmployeeBonus.HrEmployeesBonus;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.mapper.EmployeeBonus.HrEmployeesBonusFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Stateless
public class EmployeesBean implements EmployeesBeanLocal {

    @EJB
    HrEmployeesBonusFacade hrEmployeesBonusFacade;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void saveorupdate(HrEmployeesBonus hrEmployeesBonus) {
        hrEmployeesBonusFacade.create(hrEmployeesBonus);
    }

    @Override
    public HrEmployeesBonus findByBgtYear(String bgtYr) {
        return hrEmployeesBonusFacade.findByBgtYear(bgtYr);
    }

    @Override
    public List<HrEmployees> searchEmployee(Integer status2, Integer status, HrEmployees hremployees) {
        return hrEmployeesBonusFacade.searchEmployee(status2, status, hremployees);
    }

    @Override
    public void edit(HrEmployeesBonus hrEmployeesBonus) {
        hrEmployeesBonusFacade.edit(hrEmployeesBonus);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Requested List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("4"), "Load Rejected List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load all List"));
        return selectItems;
    }

    @Override
    public List<HrEmployeesBonus> loadFiltereddata(int status) {
        return hrEmployeesBonusFacade.loadFiltereddata(status);
    }

    @Override
    public void saveUpdate(HrEmployeesBonus hrEmployeesBonus) {
        hrEmployeesBonusFacade.saveOrUpdate(hrEmployeesBonus);
    }

    @Override
    public HrEmployeesBonus getSelectedRequest(BigDecimal id) {
        return hrEmployeesBonusFacade.getSelectedRequest(id);
    }
}
