/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceForeign;
import et.gov.eep.fcms.entity.perDiem.FmsGoodWillingPayment;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.fcms.mapper.perDiem.FmsFieldAllowanceForeignFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author muller
 */
@Stateless
public class fmsFieldAllowansForeignBean implements fmsFieldAllowansForeignBeanLocal {

    @EJB
    FmsFieldAllowanceForeignFacade allowanceForeignFacade;

    @Override
    public void create(FmsFieldAllowanceForeign allowanceForeign) {
        allowanceForeignFacade.create(allowanceForeign);
    }

    @Override
    public void edit(FmsFieldAllowanceForeign allowanceForeign) {
        allowanceForeignFacade.edit(allowanceForeign);
    }

    @Override
    public FmsLuAdditionalAmount searchAdd(String levelId) {
        return allowanceForeignFacade.searchAdd(levelId); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FmsGoodWillingPayment searchGoodW(FmsGoodWillingPayment fmsGoodWillingPayment) {
        return allowanceForeignFacade.searchgoood(fmsGoodWillingPayment); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FmsLuPerdimeRate getPer(Integer levId) {
        return allowanceForeignFacade.search(levId);
    }

    @Override
    public FmsFieldAllowanceForeign findEmploye(FmsFieldAllowanceForeign allowanceForeign) {
        return allowanceForeignFacade.findEmployee(allowanceForeign); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FmsFieldAllowanceForeign> SearchEmpName(FmsFieldAllowanceForeign allowanceForeign) {
        return allowanceForeignFacade.SearchEmpName(allowanceForeign);
    }

    @Override
    public FmsFieldAllowanceForeign getAllEmployees(FmsFieldAllowanceForeign allowanceForeign) {
        return allowanceForeignFacade.getAllEmployees(allowanceForeign);
    }

    @Override
    public List<FmsFieldAllowanceForeign> searchEmpByEmpName(HrEmployees empEnty) {
        return allowanceForeignFacade.searchEmpByEmpName(empEnty);
    }

    @Override
    public List<FmsFieldAllowanceForeign> searchAll() {
        return allowanceForeignFacade.findAll();
    }

    @Override
    public FmsFieldAllowanceForeign getById(FmsFieldAllowanceForeign allowanceForeign) {
        return allowanceForeignFacade.getByID(allowanceForeign);
    }

    @Override
    public List<FmsFieldAllowanceForeign> searchEmployeeByEmpId(HrEmployees empEnty) {
        return allowanceForeignFacade.searchEmployeeByEmpId(empEnty);
    }

    @Override
    public List<FmsFieldAllowanceForeign> findFaByWfStatus(int status) {
        return allowanceForeignFacade.findFaByWfStatus(status);
    }

    @Override
    public Integer countRow() {
        return allowanceForeignFacade.countRow();
    }
}
