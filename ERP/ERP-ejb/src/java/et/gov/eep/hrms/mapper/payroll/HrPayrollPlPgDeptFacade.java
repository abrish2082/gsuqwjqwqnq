/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPgDept;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author meles
 */
@Stateless
public class HrPayrollPlPgDeptFacade extends AbstractFacade<HrPayrollPlPgDept> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollPlPgDeptFacade() {
        super(HrPayrollPlPgDept.class);
    }

    public FmsCostcSystemJunction readSystemCostCenter(HrDepartments hrDepartments) {
        Query query = em.createNativeQuery("SELECT * from "
                + "FMS_COSTC_SYSTEM_JUNCTION "
                + "WHERE FMS_COSTC_SYSTEM_JUNCTION.DEP_ID = ? ", FmsCostcSystemJunction.class);
        try {
            query.setParameter(1, hrDepartments.getDepId());
            if (query.getResultList().size() > 0) {
                FmsCostcSystemJunction fmsCostCenter = (FmsCostcSystemJunction) query.getResultList().get(0);
                return fmsCostCenter;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrPayrollPlPgDept readPayLocationPayGroupDetail(HrDepartments hrDepartmentMain) {
        Query query = em.createNativeQuery("SELECT HR_DEPARTMENTS.DEP_ID, "
                + " FMS_SYSTEM.SYSTEM_CODE, "
                + " FMS_COST_CENTER.SYSTEM_NAME, "
                + " NVL(HR_PAYROLL_PL_PG_DEPT.ID, 0) ID, "
                + " HR_PAYROLL_PL_PG_DEPT.DEP_ID, "
                + " HR_PAYROLL_PL_PG_DEPT.PAY_LOCATION_PAY_GROUP_ID, "
                + " HR_PAYROLL_PL_PG_DEPT.REMARK, "
                + " HR_PAYROLL_PL_PG.PAY_LOCATION, "
                + " HR_PAYROLL_PL_PG.PAY_GROUP, "
                + " HR_PAYROLL_PL_PG.DESCRIPTION "
                + " FROM HR_PAYROLL_PL_PG_DEPT "
                + " INNER JOIN HR_PAYROLL_PL_PG "
                + " ON HR_PAYROLL_PL_PG_DEPT.PAY_LOCATION_PAY_GROUP_ID = HR_PAYROLL_PL_PG.ID "
                + " INNER JOIN HR_DEPARTMENTS "
                + " ON HR_PAYROLL_PL_PG_DEPT.DEP_ID = HR_DEPARTMENTS.DEP_ID "
                + " LEFT JOIN FMS_COSTC_SYSTEM_JUNCTION "
                + " ON HR_PAYROLL_PL_PG_DEPT.DEP_ID = FMS_COSTC_SYSTEM_JUNCTION.DEP_ID "
                + " LEFT JOIN FMS_SYSTEM "
                + " ON FMS_SYSTEM.SYSTEM_ID = FMS_COSTC_SYSTEM_JUNCTION.SYSTEM_ID "
                + " LEFT JOIN FMS_COST_CENTER "
                + " ON FMS_COSTC_SYSTEM_JUNCTION.COST_CENTER_ID = FMS_COST_CENTER.COST_CENTER_ID"
                + " WHERE HR_DEPARTMENTS.DEP_ID = ? ", HrPayrollPlPgDept.class);
        try {
            query.setParameter(1, hrDepartmentMain.getDepId());
            HrPayrollPlPgDept hrPayrollPlPgDept = (HrPayrollPlPgDept) query.getResultList().get(0);
            return hrPayrollPlPgDept;
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

    public HrPayrollPlPgDept findBydepOfPlPg(Integer departmentId) {
        Query query = em.createNamedQuery("HrPayrollPlPgDept.findByDeptId", HrPayrollPlPgDept.class);
        query.setParameter("depId", departmentId);
        try {
            if (query.getResultList().size() > 0) {
                HrPayrollPlPgDept hrPayrollPlPgDept = (HrPayrollPlPgDept) query.getResultList().get(0);
                return hrPayrollPlPgDept;
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

}
