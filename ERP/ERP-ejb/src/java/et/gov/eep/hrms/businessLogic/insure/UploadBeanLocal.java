/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisResult;
import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisUpload;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface UploadBeanLocal {

    public void save(HrInsuranceDiagnosisUpload hrDiagnosisUpload);

//    public List<HrInsuranceDiagnosisUpload> findDocumenId(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult);

    public void Remove(HrInsuranceDiagnosisUpload hrDiagnosisUpload);

    public List<String> findByDocId(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult);

    public List<HrInsuranceDiagnosisUpload> findall();
   
}
