/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudentPlacement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Benin
 */
@Stateless
public class HrTdIspStudentPlacementFacade extends AbstractFacade<HrTdIspStudentPlacement> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdIspStudentPlacementFacade() {
        super(HrTdIspStudentPlacement.class);
    }
    //<editor-fold defaultstate="collapsed" desc="Named query">

    public List<HrTdIspStudentDetails> findAllStudentsName() {
        Query query = em.createNamedQuery("HrTdIspStudentDetails.findAll");
        try {
            ArrayList<HrTdIspStudentDetails> studentsname = new ArrayList(query.getResultList());
            return studentsname;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrDepartments> findAllDepartmentsName() {
        Query query = em.createNamedQuery("HrDepartments.findAll");
        try {
            ArrayList<HrDepartments> departmentname = new ArrayList(query.getResultList());
            return departmentname;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrTdIspStudentPlacement findbyStdId(HrTdIspStudentPlacement hrTdIspStudentPlacement) {
        Query query = em.createNamedQuery("HrTdIspStudentPlacement.findByInternshipStudentDetailsId");
        query.setParameter("internshipStudentDetailsId", hrTdIspStudentPlacement.getInternshipStudentDetailsId().getId());
        try {
            HrTdIspStudentPlacement stdid = (HrTdIspStudentPlacement) query.getResultList().get(0);
            return stdid;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrTdIspStudentPlacement getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrTdIspStudentPlacement.findById");
        query.setParameter("id", request);
        try {
            HrTdIspStudentPlacement selectedRequest = (HrTdIspStudentPlacement) query.getResultList().get(0);
            return selectedRequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

}
