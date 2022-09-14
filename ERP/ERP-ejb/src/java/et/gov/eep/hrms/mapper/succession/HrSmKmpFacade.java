/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.succession;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.succession.HrSmKmp;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrSmKmpFacade extends AbstractFacade<HrSmKmp> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSmKmpFacade() {
        super(HrSmKmp.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    @Override
    public List<HrSmKmp> findAll() {
        Query query = em.createNamedQuery("HrSmKmp.findAll");
        List<HrSmKmp> getAllKMP = (List<HrSmKmp>) query.getResultList();
        return getAllKMP;
    }

    public HrSmKmp getById(HrSmKmp hrSmKmp) {
        Query query = em.createNamedQuery("HrSmKmp.findById", HrSmKmp.class);
        query.setParameter("Id", hrSmKmp.getId());
        HrSmKmp kmp = (HrSmKmp) (query.getResultList().get(0));
        return kmp;

    }

    public List<HrSmKmp> findAllkmp() {
        Query query = em.createNativeQuery("SELECT * FROM HrSmKmp", HrSmKmp.class);
        return (List<HrSmKmp>) query.getResultList();
    }

    public HrSmKmp readkmpDetail(Integer id) {

        Query query = em.createNamedQuery("HrSmKmp.findById");
        query.setParameter("id", id);
        HrSmKmp selectedSession = (HrSmKmp) query.getResultList().get(0);
        return selectedSession;

    }

    public boolean searchbyJobAndDep(HrJobTypes hrJobTypes, HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrSmKmp.findByDeptIdJobId", HrSmKmp.class);
        query.setParameter("jobId", hrJobTypes.getId());
        query.setParameter("deptId", hrDepartments.getDepId());
        return query.getResultList().isEmpty();

    }

    public List<HrSmKmp> findJobTitle(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrSmKmp.findBykmpId", HrSmKmp.class);
        query.setParameter("jobTitle", hrJobTypes.getJobTitle().toUpperCase() + '%');
        return (List<HrSmKmp>) query.getResultList();
    }

    public List<HrSmKmp> findDepartmentName(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrSmKmp.findByDeptName", HrSmKmp.class);
        query.setParameter("detName", hrDepartments.getDepName().toUpperCase() + '%');
        return (List<HrSmKmp>) query.getResultList();

    }

    public List<HrDeptJobs> findByDeptId(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrDeptJobs.findByDepartmentId", HrDeptJobs.class);
        query.setParameter("deptId", hrDepartments.getDepId());
        return (List<HrDeptJobs>) query.getResultList();

    }

    public List<HrSmKmp> findbyposition(HrSmKmp hrSmKmp) {
        Query query = em.createNamedQuery("HrSmKmp.findById", HrSmKmp.class);
        query.setParameter("id", hrSmKmp.getId());
        return (List<HrSmKmp>) query.getResultList();

    }
    //</editor-fold>

}
