/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import et.gov.eep.hrms.mapper.insure.HrInsuranceInjuredEmployeeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author meles
 */
@Stateless
public class InsuranceInjuredEmployees implements InsuranceInjuredEmployeesLocal {

    @EJB
    HrInsuranceInjuredEmployeeFacade hrInsuranceInjuredEmployeeFacade;

    @Override
    public void saveorupdate(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        hrInsuranceInjuredEmployeeFacade.saveOrUpdate(hrInsuranceInjuredEmployee);
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findbyname(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        return hrInsuranceInjuredEmployeeFacade.findAll(hrInsuranceInjuredEmployee);
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findtype(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        return hrInsuranceInjuredEmployeeFacade.findtype(hrInsuranceInjuredEmployee);
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findakkll(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        return hrInsuranceInjuredEmployeeFacade.findakkll(hrInsuranceInjuredEmployee);
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findakkll() {
        return hrInsuranceInjuredEmployeeFacade.findAll();
    }

    @Override
    public HrInsuranceInjuredEmployee getSelectedRequest(int id) {
        return hrInsuranceInjuredEmployeeFacade.getSelectedRequest(id);
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findByActingType(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findByfullname(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        return hrInsuranceInjuredEmployeeFacade.findByfullname(hrInsuranceInjuredEmployee);
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findByempname(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        return hrInsuranceInjuredEmployeeFacade.findByempname(hrInsuranceInjuredEmployee);
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findByempname(HrEmployees hrEmployees) {
        return hrInsuranceInjuredEmployeeFacade.findByempname(hrEmployees);
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findall() {
        return hrInsuranceInjuredEmployeeFacade.findAll();
    }

    @Override
    public List<ColumnNameResolver> findColumns() {
        return hrInsuranceInjuredEmployeeFacade.findColumns();
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findInjuredEmployee(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee, String col_Name_FromTable) {
        return hrInsuranceInjuredEmployeeFacade.findInjuredEmployee(hrInsuranceInjuredEmployee,col_Name_FromTable);
    }
}
