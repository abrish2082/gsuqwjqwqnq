/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisResult;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface HrInsuranceDiagonasticResultBeanLocal {

    public HrInsuranceInjuredEmployee getSelectedRequest(int request);

    public void saveOrUpdate(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult);

    public List<HrInsuranceInjuredEmployee> findAll();

    public List<HrInsuranceDiagnosisResult> findByInsuranceId(HrInsuranceDiagnosisResult hrLuInsurances);

    public HrInsuranceInjuredEmployee findById(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee);

    public List<HrInsuranceDiagnosisResult> findall(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult);

    public void edit(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult);

    public void create(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult);

    public List<HrInsuranceInjuredEmployee> findInjuredEmp(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee, String statusinjury);

    public List<HrInsuranceDiagnosisResult> findall();

    public HrInsuranceDiagnosisResult getSelectedPlanRequest(int request);
}
