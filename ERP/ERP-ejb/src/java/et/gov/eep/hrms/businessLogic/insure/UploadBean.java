/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisResult;
import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisUpload;
import et.gov.eep.hrms.mapper.insure.HrInsuranceDiagnosisResultFacade;
import et.gov.eep.hrms.mapper.insure.HrInsuranceDiagnosisUploadFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class UploadBean implements UploadBeanLocal {

    @EJB
    HrInsuranceDiagnosisUploadFacade hrDiagnosisUploadFacade;

    @EJB
    HrInsuranceDiagnosisResultFacade hrInsuranceDiagnosisResultFacade;

    @Override
    public void save(HrInsuranceDiagnosisUpload hrDiagnosisUpload) {
        hrDiagnosisUploadFacade.saveOrUpdate(hrDiagnosisUpload);
    }

//    @Override
//    public List<HrInsuranceDiagnosisUpload> findDocumenId(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult) {
//        return hrDiagnosisUploadFacade.findDocumentId(hrInsuranceDiagnosisResult);
//    }

    @Override
    public void Remove(HrInsuranceDiagnosisUpload hrDiagnosisUpload) {
       hrDiagnosisUploadFacade.remove(hrDiagnosisUpload);
    }

    @Override
    public List<String> findByDocId(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult) {
        return hrDiagnosisUploadFacade.findDocumentId(hrInsuranceDiagnosisResult);
    }

    @Override
    public List<HrInsuranceDiagnosisUpload> findall() {
       return hrDiagnosisUploadFacade.findAllCourseName();
    }
}
