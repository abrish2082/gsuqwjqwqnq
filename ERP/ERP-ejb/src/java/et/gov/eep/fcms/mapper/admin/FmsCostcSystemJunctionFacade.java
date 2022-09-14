/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.admin;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.entity.organization.HrDepartments;

/**
 *
 * @author user
 */
@Stateless
public class FmsCostcSystemJunctionFacade extends AbstractFacade<FmsCostcSystemJunction> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsCostcSystemJunctionFacade() {
        super(FmsCostcSystemJunction.class);
    }

    /**
     *
     * @param fmsLuSystem
     * @return system List
     */
    public ArrayList<FmsCostcSystemJunction> fetchMappedSystem(FmsLuSystem fmsLuSystem) {
        try {
            Query query = em.createNamedQuery("FmsCostcSystemJunction.findBySystemId");
            query.setParameter("fmsLuSystem", fmsLuSystem);
            ArrayList<FmsCostcSystemJunction> sysList = new ArrayList(query.getResultList());
            return sysList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param costcSystemJunction
     * @return id
     */
    public FmsCostcSystemJunction findById(FmsCostcSystemJunction costcSystemJunction) {
        Query query = em.createNamedQuery("FmsCostcSystemJunction.findById", FmsCostcSystemJunction.class);
        clearCach();
        query.setParameter("id", costcSystemJunction.getId());

        try {

            FmsCostcSystemJunction result = (FmsCostcSystemJunction) query.getSingleResult();
            return result;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param departmentId
     * @return cost Center
     */
    public FmsCostcSystemJunction findbyDepId(HrDepartments departmentId) {
        Query query = em.createNamedQuery("FmsCostcSystemJunction.findByDepId", FmsCostcSystemJunction.class);
        query.setParameter("depId", departmentId);
        if (query.getResultList().size() > 0) {
            FmsCostcSystemJunction costCenter = (FmsCostcSystemJunction) query.getResultList().get(0);
            return costCenter;
        } else {
            FmsCostcSystemJunction costCenter = new FmsCostcSystemJunction();
            return costCenter;
        }
    }

    /**
     *
     * @param departmentId
     * @return cost Center
     */
    public FmsCostcSystemJunction findbyDeptId(Integer departmentId) {
        Query query = em.createNamedQuery("FmsCostcSystemJunction.findByDeptId", FmsCostcSystemJunction.class);
        query.setParameter("deptId", departmentId);
        if (query.getResultList().size() > 0) {
            FmsCostcSystemJunction costCenter = (FmsCostcSystemJunction) query.getResultList().get(0);
            return costCenter;
        } else {
            FmsCostcSystemJunction costCenter = new FmsCostcSystemJunction();
            return costCenter;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @param fmsCostCenter
     * @return CostcSystemJunction
     */
    public FmsCostcSystemJunction findByCCandSS(FmsLuSystem fmsLuSystem, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsCostcSystemJunction.findByCCandSS", FmsCostcSystemJunction.class);
        query.setParameter("fmsLuSystem", fmsLuSystem);
        query.setParameter("fmsCostCenter", fmsCostCenter);
        if (query.getResultList().size() > 0) {
            FmsCostcSystemJunction costcSystemJunction = (FmsCostcSystemJunction) query.getResultList().get(0);
            return costcSystemJunction;
        } else {
            FmsCostcSystemJunction costcSystemJunction = new FmsCostcSystemJunction();
            return costcSystemJunction;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @param fmsCostCenter
     * @return CostcSystemJunction
     */
    public FmsCostcSystemJunction findByCCandSSId(FmsLuSystem fmsLuSystem, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsCostcSystemJunction.findByCCandSSId", FmsCostcSystemJunction.class);
        query.setParameter("systemId", fmsLuSystem.getSystemId());
        query.setParameter("costCenterId", fmsCostCenter.getCostCenterId());
        if (query.getResultList().size() > 0) {
            FmsCostcSystemJunction costcSystemJunction = (FmsCostcSystemJunction) query.getResultList().get(0);
            return costcSystemJunction;
        } else {
            FmsCostcSystemJunction costcSystemJunction = new FmsCostcSystemJunction();
            return costcSystemJunction;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @param fmsCostCenter
     * @return fmsCostcSystemJunction
     */
    public FmsCostcSystemJunction findBySystemCodeAndCostCenterCode(FmsLuSystem fmsLuSystem, FmsCostCenter fmsCostCenter) {
        Query query = em.createNativeQuery("SELECT * \n "
                + "FROM FMS_COSTC_SYSTEM_JUNCTION FCSJ\n "
                + "INNER JOIN FMS_SYSTEM FSS \n"
                + "ON FSS.SYSTEM_ID = FCSJ.SYSTEM_ID \n"
                + "INNER JOIN FMS_COST_CENTER FCC \n"
                + "ON FCC.COST_CENTER_ID =FCSJ.COST_CENTER_ID \n"
                + "WHERE FSS.SYSTEM_CODE ='" + fmsLuSystem.getSystemCode() + "'"
                + "AND FCC.SYSTEM_NAME='" + fmsCostCenter.getSystemName() + "'", FmsCostcSystemJunction.class);
        FmsCostcSystemJunction fmsCostcSystemJunction = new FmsCostcSystemJunction();
        if (query.getResultList().size() > 0) {
            fmsCostcSystemJunction = (FmsCostcSystemJunction) query.getResultList().get(0);
        }
        return fmsCostcSystemJunction;
    }

    /**
     *
     * @param systemId
     * @return cost center list
     */
    public List<FmsCostcSystemJunction> findUnmappedCostCenterName(FmsLuSystem systemId) {
        List<FmsCostcSystemJunction> ccList;
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT(scj.COST_CENTER_ID) FROM "
                    + "FMS_COSTC_SYSTEM_JUNCTION scj "
                    + "WHERE scj.SYSTEM_ID != '" + systemId + "'", FmsCostcSystemJunction.class);
            ccList = (List<FmsCostcSystemJunction>) query.getResultList();
            return ccList;
        } catch (Exception e) {
            return null;
        }
    }

}
