/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.organization;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class HrSalaryScaleRangesFacade extends AbstractFacade<HrSalaryScaleRanges> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSalaryScaleRangesFacade() {
        super(HrSalaryScaleRanges.class);
    }

    public HrSalaryScaleRanges findSalaryScaleRange(BigDecimal id) {
        Query query = em.createNamedQuery("HrSalaryScaleRanges.findGrades");
        query.setParameter("id", id);
        if (query.getResultList().isEmpty()) {
            return null;
        } else {
            return (HrSalaryScaleRanges) query.getSingleResult();
        }
    }

    public List<HrSalaryScaleRanges> findAllSalaryScaleRanges(BigDecimal id) {
        Query query = em.createNamedQuery("HrSalaryScaleRanges.findGrades");
        query.setParameter("id", id);
        return query.getResultList();

    }

    public List<HrSalaryScaleRanges> allJobGrades() {
        Query query = em.createNamedQuery("HrSalaryScaleRanges.findAll");
        try {
            return (List<HrSalaryScaleRanges>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrSalaryScaleRanges findSalaryScale(HrSalaryScaleRanges hrSalaryScaleRanges) {
        Query query = em.createNamedQuery("HrSalaryScaleRanges.findByDescription");
        query.setParameter("description", hrSalaryScaleRanges.getDescription());
        try {
            HrSalaryScaleRanges salaryScaleRanges = (HrSalaryScaleRanges) query.getResultList().get(0);
            return salaryScaleRanges;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrSalaryScaleRanges findSalaryScaleById(HrSalaryScaleRanges hrSalaryScaleRanges) {
        Query query = em.createNamedQuery("HrSalaryScaleRanges.findById");
        query.setParameter("id", hrSalaryScaleRanges.getId());
        System.out.print("facede" + hrSalaryScaleRanges.getId());
        try {
            return (HrSalaryScaleRanges) query.getResultList().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    public HrSalaryScaleRanges findByGrade(HrLuGrades hrLuGrades) {
        try {
            Query query = em.createNamedQuery("HrSalaryScaleRanges.findByGrade", HrSalaryScaleRanges.class);
            query.setParameter("hrLuGrades", hrLuGrades);
            return (HrSalaryScaleRanges) query.getResultList().get(0);
        } catch (Exception ex) {
            return null;
        }
    }
}
