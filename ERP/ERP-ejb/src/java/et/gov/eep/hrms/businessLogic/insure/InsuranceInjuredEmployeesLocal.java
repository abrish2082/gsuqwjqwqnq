/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface InsuranceInjuredEmployeesLocal {

    public void saveorupdate(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee);

    public List<HrInsuranceInjuredEmployee> findbyname(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee);

    public List<HrInsuranceInjuredEmployee> findtype(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee);

    public List<HrInsuranceInjuredEmployee> findakkll(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee);

    public List<HrInsuranceInjuredEmployee> findakkll();

    public HrInsuranceInjuredEmployee getSelectedRequest(int id);

    public List<HrInsuranceInjuredEmployee> findByActingType(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee);

    public List<HrInsuranceInjuredEmployee> findByfullname(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee);

    public List<HrInsuranceInjuredEmployee> findByempname(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee);

    public List<HrInsuranceInjuredEmployee> findByempname(HrEmployees hrEmployees);

    public List<HrInsuranceInjuredEmployee> findall();

    public List<ColumnNameResolver> findColumns();

    public List<HrInsuranceInjuredEmployee> findInjuredEmployee(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee, String col_Name_FromTable);

    
}
