/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;

/**
 *
 * @author munir
 */
@Stateless
public class HrPayrollPlPgFacade extends AbstractFacade<HrPayrollPlPg> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollPlPgFacade() {
        super(HrPayrollPlPg.class);
    }

    public FmsCostCenter readSystemCostCenter(HrDepartments hrDepartments) {
        Query query = em.createNativeQuery("SELECT FMS_COST_CENTER.* "
                + " FROM FMS_SYSTEM "
                + " INNER JOIN FMS_COST_CENTER "
                + " ON FMS_SYSTEM.SYSTEM_ID = FMS_COST_CENTER.SYSTEM_ID "
                + " WHERE FMS_COST_CENTER.DEP_ID = ? ", FmsCostCenter.class);
        try {
            query.setParameter(1, hrDepartments.getDepId());
            if (query.getResultList().size() > 0) {
                FmsCostCenter fmsCostCenter = (FmsCostCenter) query.getResultList().get(0);
                return fmsCostCenter;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

//How to get the primary key of any JPA entity?
//    Object id = entityManagerFactory.getPersistenceUnitUtil().getIdentifier(entity);
    public HrPayrollPlPg readPayLocationPayGroupDetail(HrDepartments hrDepartments) {
        //IT QUERIES DEPARTMENTS THAT ARE ASSIGNED (SYSTEM/COST CENTER)
        Query query = em.createNativeQuery("SELECT HR_DEPARTMENTS.DEP_ID, "
                + " FMS_SYSTEM.SYSTEM_CODE, "
                + " FMS_COST_CENTER.SYSTEM_NAME, "
                + " NVL(HR_PAYROLL_PL_PG.ID, 0) ID, "
                + " HR_PAYROLL_PL_PG.DEPT_ID, "
                + " HR_PAYROLL_PL_PG.PAY_LOCATION, "
                + " HR_PAYROLL_PL_PG.PAY_GROUP, "
                + " HR_PAYROLL_PL_PG.REMARK  "
                + " FROM FMS_COST_CENTER "
                + " LEFT JOIN FMS_SYSTEM "
                + " ON FMS_SYSTEM.SYSTEM_ID = FMS_COST_CENTER.SYSTEM_ID "
                + " RIGHT JOIN HR_DEPARTMENTS "
                + " ON FMS_COST_CENTER.DEP_ID = HR_DEPARTMENTS.DEP_ID "
                + " LEFT JOIN HR_PAYROLL_PL_PG "
                + " ON FMS_COST_CENTER.DEP_ID = HR_PAYROLL_PL_PG.DEPT_ID "
                + " WHERE HR_DEPARTMENTS.DEP_ID = ? ", HrPayrollPlPg.class);

        //IT QUERIES DEPARTMENTS WHETHER THAT ARE ASSIGNED (SYSTEM/COST CENTER) OR NOT
//        Query query = em.createNativeQuery("SELECT HR_DEPARTMENTS.DEP_ID, "
//                + " FMS_SYSTEM.SYSTEM_CODE, "
//                + " FMS_COST_CENTER.SYSTEM_NAME, "
//                + " NVL(HR_PAYROLL_PL_PG.ID, 0) ID, "
//                + " HR_PAYROLL_PL_PG.DEPT_ID, "
//                + " HR_PAYROLL_PL_PG.PAY_LOCATION, "
//                + " HR_PAYROLL_PL_PG.PAY_GROUP, "
//                + " HR_PAYROLL_PL_PG.REMARK  "
//                + " FROM FMS_COST_CENTER "
//                + " RIGHT JOIN HR_DEPARTMENTS "
//                + " ON FMS_COST_CENTER.DEP_ID = HR_DEPARTMENTS.DEP_ID "
//                + " LEFT JOIN FMS_SYSTEM "
//                + " ON FMS_SYSTEM.SYSTEM_ID = FMS_COST_CENTER.SYSTEM_ID "
//                + " LEFT JOIN HR_PAYROLL_PL_PG "
//                + " ON HR_DEPARTMENTS.DEP_ID = HR_PAYROLL_PL_PG.DEPT_ID "
//                + " WHERE HR_DEPARTMENTS.DEP_ID = ? ", HrPayrollPlPg.class);
        try {
            query.setParameter(1, hrDepartments.getDepId());
            HrPayrollPlPg hrPayrollPlPg = (HrPayrollPlPg) query.getResultList().get(0);
            return hrPayrollPlPg;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Object[]> readPayLocPayGroup(int isLeft) {
        try {
            StoredProcedureQuery query = em.createStoredProcedureQuery("HR_PAYROLL_PL_PG_SELECT_PRC");
            query.registerStoredProcedureParameter(1, int.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(2, Object.class, ParameterMode.REF_CURSOR);
            query.setParameter(1, isLeft);
            query.execute();
            List<Object[]> result = (List<Object[]>) query.getOutputParameterValue(2);
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Object[]> readPayLocPayGroup(HrDepartments hrDepartments) {
        Query query = em.createNativeQuery("SELECT HR_DEPARTMENTS.DEP_ID, "
                + " HR_DEPARTMENTS.DEP_NAME, "
                + " FMS_LU_SYSTEM.SYSTEM_CODE, "
                + " FMS_COST_CENTER.SYSTEM_NAME, "
                + " NVL(HR_PAYROLL_PL_PG.ID, 0) ID, "
                + " HR_PAYROLL_PL_PG.PAY_LOCATION, "
                + " HR_PAYROLL_PL_PG.PAY_GROUP, "
                + " HR_PAYROLL_PL_PG.REMARK  "
                + " FROM FMS_COST_CENTER "
                + " INNER JOIN FMS_LU_SYSTEM "
                + " ON FMS_LU_SYSTEM.SYSTEM_ID = FMS_COST_CENTER.SYSTEM_ID "
                + " INNER JOIN HR_DEPARTMENTS "
                + " ON FMS_COST_CENTER.DEP_ID = HR_DEPARTMENTS.DEP_ID "
                + " INNER JOIN HR_PAYROLL_PL_PG "
                + " ON FMS_COST_CENTER.DEP_ID = HR_PAYROLL_PL_PG.DEPT_ID "
                + " WHERE FMS_COST_CENTER.DEP_ID = ? ");
        try {
            query.setParameter(1, hrDepartments.getDepId());
            return (List<Object[]>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrPayrollPlPg findBydepOfPlPg(HrDepartments departmentId) {
        Query query = em.createNamedQuery("HrPayrollPgPl.findByDeptId", HrPayrollPlPg.class);
        query.setParameter("deptId", departmentId);
        try {
            if (query.getResultList().size() > 0) {
                HrPayrollPlPg hrPayrollPlPg = (HrPayrollPlPg) query.getResultList().get(0);
                return hrPayrollPlPg;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    public List<HrPayrollPlPg> findAllPLPG() {
        Query query = em.createNamedQuery("HrPayrollPgPl.findAll", HrPayrollPlPg.class);
        try {
            if (query.getResultList().size() > 0) {
                return query.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    public FmsCostCenter findByDepartmentID(HrDepartments departmentId) {
        Query query = em.createNamedQuery("FmsCostCenter.findByDepIde", FmsCostCenter.class);
        query.setParameter("depId", departmentId);
        try {
            if (query.getResultList().size() > 0) {
                FmsCostCenter costCenter = (FmsCostCenter) query.getResultList().get(0);
                return costCenter;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    public HrPayrollPlPg findByDeptID(HrDepartments departmentId) {
        Query query = em.createNamedQuery("HrPayrollPgPl.findByDepId", HrPayrollPlPg.class);
        query.setParameter("depId", departmentId);
        try {
            if (query.getResultList().size() > 0) {
                HrPayrollPlPg pgpl = (HrPayrollPlPg) query.getResultList().get(0);
                return pgpl;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    public HrPayrollPlPg getSelectedRequest(Integer id) {
        Query query = em.createNamedQuery("HrPayrollPgPl.findById");
        query.setParameter("id", id);
        try {
            HrPayrollPlPg selectrequest = (HrPayrollPlPg) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrPayrollPlPg> findAllPlPg() {
        Query query = em.createNamedQuery("HrPayrollPgPl.findAll");
        try {
            List<HrPayrollPlPg> findallPlPg = (List<HrPayrollPlPg>) query.getResultList();
            return findallPlPg;
        } catch (Exception ex) {
            return null;
        }
    }
}
