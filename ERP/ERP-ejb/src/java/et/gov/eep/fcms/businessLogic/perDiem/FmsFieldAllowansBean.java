/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceForeign;
import et.gov.eep.fcms.mapper.perDiem.FmsFieldAllowanceFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author muller
 */
@Stateless
public class FmsFieldAllowansBean implements FmsFieldAllowansBeanLocal {

    @EJB
    FmsFieldAllowanceFacade allowanceFacade;

    @Override
    public void create(FmsFieldAllowance fmsFieldAllowanceEnty) {
        allowanceFacade.create(fmsFieldAllowanceEnty);
    }

    @Override
    public void edit(FmsFieldAllowance fmsFieldAllowanceEnty) {
        allowanceFacade.edit(fmsFieldAllowanceEnty);
    }

    @Override
    public List<FmsFieldAllowance> searchFieldAllowance(FmsFieldAllowance fmsFieldAllowanceEnty) {
        return allowanceFacade.searchFieldAllowance(fmsFieldAllowanceEnty);
    }

    public FmsFieldAllowance gethFieldAllowance(FmsFieldAllowance fmsFieldAllowanceEnty) {
        return allowanceFacade.gethFieldAllowance(fmsFieldAllowanceEnty);
    }

    @Override
    public ArrayList<HrEmployees> SearchByEmpId(HrEmployees employeeEnty) {
        return allowanceFacade.SearchName(employeeEnty);//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FmsFieldAllowance> getAllEmployee() {
        return allowanceFacade.findAll();
    }

    @Override
    public FmsFieldAllowance findEmploye(FmsFieldAllowance fmsFieldAllowanceEnty) {
        return allowanceFacade.getEmployeId(fmsFieldAllowanceEnty);

    }

    @Override
    public void create(FmsFieldAllowanceForeign allowanceForeign) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FmsFieldAllowance getById(FmsFieldAllowance fmsFieldAllowanceEnty) {
        return allowanceFacade.getById(fmsFieldAllowanceEnty);
    }

    @Override
    public List<FmsFieldAllowance> searchAllEmployee() {
        return allowanceFacade.findAll();
    }

    @Override
    public List<FmsFieldAllowance> searchEmpByEmpName(HrEmployees empEnty) {
        return allowanceFacade.searchEmpByEmpName(empEnty);
    }

    @Override
    public List<FmsFieldAllowance> searchEmployeeByEmpId(HrEmployees empEnty) {
        return allowanceFacade.searchEmployeeByEmpId(empEnty);
    }

    @Override
    public FmsFieldAllowance getByrequestno(FmsFieldAllowance fmsFieldAllowanceEnty) {
        return allowanceFacade.getByRequstno(fmsFieldAllowanceEnty);
    }

    @Override
    public Integer countRow() {
        return allowanceFacade.countRow();
    }

    @Override
    public List<FmsFieldAllowance> searchnotexitvocher() {
        return allowanceFacade.searchnotexitvocher();
    }

    @Override
    public List<FmsFieldAllowance> searchSetlementchekeVocher() {
        return allowanceFacade.searchSetlementchekeVocher();
    }

    @Override
    public List<FmsFieldAllowance> findFaByWfStatus(int status) {
        return allowanceFacade.findFaByWfStatus(status);
    }
}
