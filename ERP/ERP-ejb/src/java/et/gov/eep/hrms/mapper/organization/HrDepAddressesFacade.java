/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.organization;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepAddresses;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class HrDepAddressesFacade extends AbstractFacade<HrDepAddresses> {

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
    public HrDepAddressesFacade() {
        super(HrDepAddresses.class);
    }

    /**
     *
     * @param hrDepartments
     * @return
     * @throws RuntimeException
     */
    public HrDepAddresses findDepartmentAddress(HrDepartments hrDepartments) throws RuntimeException {
        try {
            Query q = em.createNamedQuery("HrDepAddresses.findByDeptId");
            q.setParameter("depId", hrDepartments.getDepId());
            Object obj = q.getResultList();
            if (obj == null) {
                return null;
            } else {
                q.setMaxResults(1);
                return (HrDepAddresses) q.getSingleResult();
            }

        } catch (NoResultException ex) {
            ex.printStackTrace();
            return null;
        }

    }

//    public HrDepAddresses findByDeptId2(HrDepartments hrDepartments) {
//        try {
//            Query q = em.createNamedQuery("HrDepAddresses.findByDeptId");
//            q.setParameter("depId", hrDepartments.getDepId());
//            Object obj = q.getResultList();
//            if (obj == null) {
//                return null;
//            } else {
//                q.setMaxResults(1);
//                return (HrDepAddresses) q.getSingleResult();
//            }
//
//        } catch (NoResultException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//
//    }
    public List<HrDepAddresses> findByDeptId(int deptId) {
        try {
            Query query = em.createNamedQuery("HrDepAddresses.findByDeptId");
            query.setParameter("depId", deptId);
            List<HrDepAddresses> depAddress = new ArrayList(query.getResultList());
            return depAddress;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
