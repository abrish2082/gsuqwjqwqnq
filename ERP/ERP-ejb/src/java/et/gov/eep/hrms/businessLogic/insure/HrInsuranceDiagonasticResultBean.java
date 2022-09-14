/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisResult;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import et.gov.eep.hrms.mapper.insure.HrInsuranceDiagnosisResultFacade;
import et.gov.eep.hrms.mapper.insure.HrInsuranceInjuredEmployeeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class HrInsuranceDiagonasticResultBean implements HrInsuranceDiagonasticResultBeanLocal {

    @EJB
    HrInsuranceDiagnosisResultFacade hrInsuranceDiagnosisResultFacade;

    @EJB
    HrInsuranceInjuredEmployeeFacade hrInsuranceInjuredEmployeeFacade;

    @Override
    public void saveOrUpdate(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult) {
        hrInsuranceDiagnosisResultFacade.saveOrUpdate(hrInsuranceDiagnosisResult);
    }

    @Override
    public HrInsuranceInjuredEmployee getSelectedRequest(int request) {
        return hrInsuranceDiagnosisResultFacade.getSelectedRequest(request);
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findAll() {
        return hrInsuranceInjuredEmployeeFacade.findAll();
    }

    @Override
    public List<HrInsuranceDiagnosisResult> findByInsuranceId(HrInsuranceDiagnosisResult hrLuInsurances) {
        return hrInsuranceDiagnosisResultFacade.findByInsuranceId(hrLuInsurances);
    }

    @Override
    public HrInsuranceInjuredEmployee findById(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        return hrInsuranceDiagnosisResultFacade.findById(hrInsuranceInjuredEmployee);
    }

    @Override
    public List<HrInsuranceDiagnosisResult> findall(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult) {
        return hrInsuranceDiagnosisResultFacade.findAll();
    }

    @Override
    public void edit(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult) {
        hrInsuranceDiagnosisResultFacade.edit(hrInsuranceDiagnosisResult);
    }

    @Override
    public void create(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult) {
        hrInsuranceDiagnosisResultFacade.create(hrInsuranceDiagnosisResult);
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findInjuredEmp(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee, String statusinjury) {
        return hrInsuranceDiagnosisResultFacade.findInjuredEmp(hrInsuranceInjuredEmployee, statusinjury);
    }

    @Override
    public List<HrInsuranceDiagnosisResult> findall() {
       return hrInsuranceDiagnosisResultFacade.findAll();
    }

    @Override
    public HrInsuranceDiagnosisResult getSelectedPlanRequest(int request) {
      return  hrInsuranceDiagnosisResultFacade.getSelectedPlanRequest(request);
    }
}
