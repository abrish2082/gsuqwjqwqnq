/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.organization;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmKmp;
import java.util.ArrayList;
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
public class HrDeptJobsFacade extends AbstractFacade<HrDeptJobs> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public HrDeptJobsFacade() {
        super(HrDeptJobs.class);
    }

    /**
     *
     * @param toString
     * @return
     */
    public List<HrDeptJobs> getListOfJobs(String toString) {
        System.out.print("dep id2" + toString);
        List<HrDeptJobs> deptJobs = null;
        try {
            System.out.print("dep id 2" + toString);
            Query query = em.createNamedQuery("HrDeptJobs.findByDeptId", HrDeptJobs.class);
            query.setParameter("deptId", toString);
            deptJobs = (List<HrDeptJobs>) query.getResultList();
            System.out.print("dep id 3" + deptJobs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deptJobs;
    }

    /**
     *
     * @return
     */
    public List<HrDeptJobs> returnNumJobs() {
        try {
            Query query = em.createNamedQuery("HrDeptJobs.count");
            return (List<HrDeptJobs>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer numEmployeeNeeded(HrDepartments hrDepartments, HrJobTypes hrJobTypes) {
        try {
            Query query = em.createNamedQuery("HrDeptJobs.findByDeptIdJobId");
            query.setParameter("depId", hrDepartments.getDepId());
            query.setParameter("jobId", hrJobTypes.getId());
            HrDeptJobs depJob = (HrDeptJobs) query.getResultList().get(0);
            return Integer.parseInt(depJob.getNoEmpNeeded().toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HrDeptJobs findrDeptJobs(HrDepartments hrDepartments, HrJobTypes hrJobTypes) {
        try {
            Query query = em.createNamedQuery("HrDeptJobs.findByDeptIdJobId");
            query.setParameter("depId", hrDepartments.getDepId());
            query.setParameter("jobId", hrJobTypes.getId());
            HrDeptJobs depJob = (HrDeptJobs) query.getResultList().get(0);
            return depJob;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int findByJobId(HrJobTypes hrJobTypes) {
        try {
            Query query = em.createNamedQuery("HrDeptJobs.findByJobId");
            query.setParameter("jobId", hrJobTypes.getId());
            List<HrDeptJobs> depJobList = query.getResultList();
            int countEmpInJob = 0;
            for (HrDeptJobs depJob : depJobList) {
                countEmpInJob = countEmpInJob + Integer.parseInt(depJob.getNoEmpNeeded().toString());
            }
            return countEmpInJob;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<HrDeptJobs> findDebJobsByDept(int deptId) {
        try {
            Query query = em.createNamedQuery("HrDeptJobs.findByDepartmentIdLike");
            query.setParameter("deptId", deptId);
            List<HrDeptJobs> depJobList = new ArrayList(query.getResultList());
            return depJobList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrDeptJobs> checkJobDeblication(HrDeptJobs hrDeptJobs) {
        try {
            Query query = em.createNamedQuery("HrDeptJobs.findByDeptIdandJobId", HrDeptJobs.class);
            query.setParameter("deptId", hrDeptJobs.getDeptId().getDepId());
            query.setParameter("jobId", hrDeptJobs.getJobId().getId());
            System.out.println("jobId-----------------------------------" + hrDeptJobs.getJobId().getId() + "---------deptId----------------" + hrDeptJobs.getDeptId().getDepId());
            List<HrDeptJobs> deptJobs = new ArrayList(query.getResultList());
            return deptJobs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
