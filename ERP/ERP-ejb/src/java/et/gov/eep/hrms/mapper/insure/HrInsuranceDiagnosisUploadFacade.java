/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.insure;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisResult;
import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisUpload;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author insa
 */
@Stateless
public class HrInsuranceDiagnosisUploadFacade extends AbstractFacade<HrInsuranceDiagnosisUpload> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrInsuranceDiagnosisUploadFacade() {
        super(HrInsuranceDiagnosisUpload.class);
    }


    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    public List<String> findDocumentId(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult) {
        System.out.println("===facade in ==="+hrInsuranceDiagnosisResult.getId());
        try {
            Query query = em.createNativeQuery("SELECT up.doc_id \n"
                    + "FROM hr_insurance_diagnosis_upload up\n"
                    + "INNER JOIN hr_insurance_diagnosis_result dr\n"
                    + "ON up.diagnosis_id =dr.id\n"
                    + "WHERE dr.id ='" + hrInsuranceDiagnosisResult.getId() + "' ", HrInsuranceDiagnosisUpload.class);
            System.out.println("===facede one==" + query.getResultList());
            return (List<String>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    public ArrayList<HrInsuranceDiagnosisUpload> findAllCourseName() {
        Query query = em.createNamedQuery("HrInsuranceDiagnosisResult.findAll");
        try {
            ArrayList<HrInsuranceDiagnosisUpload> courseName = new ArrayList(query.getResultList());
            return courseName;
        } catch (Exception ex) {
        }
        return null;
    }
//</editor-fold>
}
