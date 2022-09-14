/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.admin;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.util.ArrayList;

/**
 *
 * @author user
 */
@Stateless
public class FmsCostCenterFacade extends AbstractFacade<FmsCostCenter> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    public FmsCostCenterFacade() {
        super(FmsCostCenter.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<FmsCostCenter> findCostCenterName(FmsLuSystem fmsLuSystem) {
        Query query = em.createNamedQuery("FmsCostCenter.findBySystemId");
        query.setParameter("systemId", fmsLuSystem);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @return system id
     */
    public List<FmsCostCenter> findCapitalBudgetList(FmsLuSystem fmsLuSystem) {
        Query query = em.createNamedQuery("FmsCostCenter.findCapitalApprove");
        query.setParameter("systemId", fmsLuSystem);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @return system id
     */
    public List<FmsCostCenter> findCostCenterNameForRequest(FmsLuSystem fmsLuSystem) {
        Query query = em.createNamedQuery("FmsCostCenter.findforBudgetRequest");
        query.setParameter("systemId", fmsLuSystem);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @return system Id
     */
    public List<FmsCostCenter> getCCbySystem(FmsLuSystem fmsLuSystem) {
        Query query = em.createNamedQuery("FmsCostCenter.findBySysId");
        query.setParameter("systemId", fmsLuSystem);
        try {

            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @return systemId
     */
    public List<FmsCostCenter> findApprovedCostCenterName(FmsLuSystem fmsLuSystem) {
        Query query = em.createNamedQuery("FmsCostCenter.searchCostCenterApprovedBudget");
        query.setParameter("systemId", fmsLuSystem);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @param budgetYear
     * @return
     */
    public List<FmsCostCenter> findCostCenterNameForDisb(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsCostCenter.searchCostCenterDisbursment");
        query.setParameter("systemId", fmsLuSystem);
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("status", 1);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @param budgetYear
     * @return
     */
    public List<FmsCostCenter> findBudgetRequest(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsCostCenter.searchBgudetRequest");
        query.setParameter("systemId", fmsLuSystem);
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @param budgetYear
     * @return
     */
    public List<FmsCostCenter> findBudgetControl(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsCostCenter.searchBgudetCont");
        query.setParameter("systemId", fmsLuSystem);
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @param budgetYear
     * @return
     */
    public List<FmsCostCenter> findCapitalControl(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsCostCenter.searchCapitalBgtControl");
        query.setParameter("systemId", fmsLuSystem);
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @param budgetYear
     * @return
     */
    public List<FmsCostCenter> findCapitalDisbursment(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsCostCenter.searchCapitalBgtControl");
        query.setParameter("systemId", fmsLuSystem);
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @param budgetYear
     * @return
     */
    public List<FmsCostCenter> findCapitalRequest(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsCostCenter.searchCapitalBgtApprove");
        query.setParameter("systemId", fmsLuSystem);
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsCostCenter
     * @return system Name
     */
    public List<FmsCostCenter> findCostCodeLike(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsCostCenter.findBySystemNameLikeOnly");
        query.setParameter("systemName", fmsCostCenter.getSystemName() + '%');
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsCostCenter
     * @return system Name
     */
    public FmsCostCenter getCostCenterId(FmsCostCenter costCenter) {
        Query query = em.createNamedQuery("FmsCostCenter.findBySystemName");
        query.setParameter("systemName", costCenter.getSystemName());
        try {
            if (query.getResultList().size() > 0) {
                return (FmsCostCenter) query.getResultList().get(0);
            } else {

                return new FmsCostCenter();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsCostCenter
     * @return systemName
     */
    public FmsCostCenter getCostCenter(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsCostCenter.findBySystemName");
        query.setParameter("systemName", fmsCostCenter.getSystemName());
        try {
            return (FmsCostCenter) query.getResultList().get(0);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @return system Code
     */
    public FmsLuSystem findSystem(FmsLuSystem fmsLuSystem) {
        Query query = em.createNamedQuery("FmsLuSystem.findBySystemCode");
        query.setParameter("systemCode", fmsLuSystem.getSystemCode());
        try {
            return (FmsLuSystem) query.getResultList().get(0);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *
     * @param departmentId
     * @return department Id
     */
    public FmsCostCenter findbydepidOfcostcent(HrDepartments departmentId) {
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

    /**
     *
     * @param departmentId
     * @return cost Center
     */
    public FmsCostCenter findbyDepId(HrDepartments departmentId) {
        Query query = em.createNamedQuery("FmsCostCenter.findByDepIde", FmsCostCenter.class);
        query.setParameter("depId", departmentId);
        if (query.getResultList().size() > 0) {
            FmsCostCenter costCenter = (FmsCostCenter) query.getResultList().get(0);
            return costCenter;
        } else {
            FmsCostCenter costCenter = new FmsCostCenter();
            return costCenter;
        }
    }

    /**
     *
     * @param systemId
     * @return cost Center list
     */
    public List<FmsCostCenter> findUnmappedCostCenterName(FmsLuSystem systemId) {
        Query query = em.createNativeQuery("SELECT * FROM FMS_COST_CENTER scj "
                + "WHERE scj.COST_CENTER_ID not in"
                + "(SELECT f.COST_CENTER_ID FROM FMS_COSTC_SYSTEM_JUNCTION f "
                + "WHERE f.SYSTEM_ID = " + systemId.getSystemId() + ")", FmsCostCenter.class);
        List<FmsCostCenter> ccList = (List<FmsCostCenter>) query.getResultList();
        return ccList;
    }

    /**
     *
     * @param ccList
     * @return cost Center list
     */
    public List<FmsCostCenter> findMappedCostCenterName(FmsLuSystem systemId) {
        Query query = em.createNativeQuery("SELECT * FROM FMS_COST_CENTER scj "
                + "WHERE scj.COST_CENTER_ID in"
                + "(SELECT f.COST_CENTER_ID FROM FMS_COSTC_SYSTEM_JUNCTION f "
                + "WHERE f.SYSTEM_ID = " + systemId.getSystemId() + ")", FmsCostCenter.class);
        List<FmsCostCenter> ccList = (List<FmsCostCenter>) query.getResultList();
        return ccList;
    }

    public List<FmsCostCenter> getFmsCostCenterSearchingParameterList() {
//        System.out.println("inside the query");
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('FMS_COST_CENTER')\n"
                + "and column_name NOT IN('DEP_ID' ,'SYSTEM_ID') ORDER BY column_name ASC");
        try {
            List<FmsCostCenter> searchParamLists = new ArrayList<>();
            searchParamLists = query.getResultList();
            System.out.println("inside the query == searchParamLists " + searchParamLists);
            return searchParamLists;
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
    }

public List<FmsCostCenter> getVoch1No(FmsCostCenter fmsCostCenter) {
        System.out.println("fmsVoucher.getColumnValue()===" + fmsCostCenter.getColumnValue());
        System.out.println("====fmsVoucher.getColumnName()===" + fmsCostCenter.getColumnName());
        if (fmsCostCenter.getColumnValue() != null && fmsCostCenter.getColumnName() != null
                && !fmsCostCenter.getColumnValue().equals("") && !fmsCostCenter.getColumnName().equals("")) {
            System.out.println("passd!!!");
            Query query = em.createNativeQuery("SELECT * FROM FMS_COST_CENTER\n"
                    + "where " + fmsCostCenter.getColumnName().toLowerCase() + " = '" + fmsCostCenter.getColumnValue() + "' ", FmsCostCenter.class);

            try {
                List<FmsCostCenter> fmsCostCenterSearchLists = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    fmsCostCenterSearchLists = query.getResultList();
                    System.out.println("==fmsCostCenterSearchLists==" + fmsCostCenterSearchLists);
                }
                return fmsCostCenterSearchLists;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            // Query query = em.createNamedQuery("FmsVoucher.findByPreparedBy");
            //  query.setParameter("preparedby", fmsVoucher.getPreparedBy());
            //  return query.getResultList();
            Query query = em.createNamedQuery("FmsCostCenter.findAll");
//            query.setParameter("type", fmsLuSystem.getType());
            ArrayList<FmsCostCenter> CostCenterList = new ArrayList(query.getResultList());
            System.out.println("VoucherList===" + CostCenterList);
            return CostCenterList;
        }
    }
}
