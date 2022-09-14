package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsInventoryCountDetail;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturn;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kimmyo
 */
@Stateless
public class MmsInventoryCountingFacade extends AbstractFacade<MmsInventoryCounting> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public MmsInventoryCountingFacade() {
        super(MmsInventoryCounting.class);
    }

    /**
     *
     * @param information
     * @return
     */
    public ArrayList<MmsInventoryCounting> searchInventoryIdInformation(MmsInventoryCounting information) {
        Query query = em.createNamedQuery("MmsInventoryCounting.findByInventoryCountId", MmsInventoryCounting.class);
        query.setParameter("inventoryCountId", information.getInventoryCountId());
        try {
            ArrayList<MmsInventoryCounting> InventioryInformations = new ArrayList(query.getResultList());
            return InventioryInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param invInformation
     * @return
     */
    public MmsInventoryCounting getMmsInventoryInformation(MmsInventoryCounting invInformation) {
        Query query = em.createNamedQuery("MmsInventoryCounting.findByInventoryCountId", MmsInventoryCounting.class);
        query.setParameter("inventoryCountId", invInformation.getInventoryCountId());
        try {
            MmsInventoryCounting importationInfo = (MmsInventoryCounting) query.getResultList().get(0);
            return importationInfo;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param InvInformation
     * @return
     */
    public ArrayList<MmsInventoryCounting> getInventoryCountByYear(MmsInventoryCounting InvInformation) {
        Query query = em.createNamedQuery("MmsInventoryCounting.findByBudgetYear", MmsInventoryCounting.class);
        query.setParameter("budgetYear", InvInformation.getBudgetYear());
        try {
            ArrayList<MmsInventoryCounting> InvCounttList = new ArrayList(query.getResultList());
            return InvCounttList;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param inventoryInformation
     * @return
     */
    public List<MmsInventoryCounting> searchByStoreAndBudgetYear(MmsInventoryCounting inventoryInformation) {

        Query query = em.createNamedQuery("MmsInventoryCounting.findByBudgetYearAndStoreId", MmsInventoryCounting.class);
        query.setParameter("workUnit", inventoryInformation.getWorkUnit());
        query.setParameter("budgetYear", inventoryInformation.getBudgetYear() + '%');
        try {
            List<MmsInventoryCounting> inventoryInformations = new ArrayList(query.getResultList());

            return inventoryInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryCounting> searchByStoreAndBudgetYearAndProcessedBy(MmsInventoryCounting inventoryInformation) {

        Query query = em.createNamedQuery("MmsInventoryCounting.findByBudgetYearAndStoreIdAndProcessedBy", MmsInventoryCounting.class);
        query.setParameter("workUnit", inventoryInformation.getWorkUnit());
        query.setParameter("processedBy", inventoryInformation.getProcessedBy());
        query.setParameter("budgetYear", inventoryInformation.getBudgetYear() + '%');
        try {
            List<MmsInventoryCounting> inventoryInformations = new ArrayList(query.getResultList());

            return inventoryInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param Year
     * @return
     */
    public List<MmsInventoryCountDetail> SearchMatcodeByYear(String Year) {
        Query query = em.createNamedQuery("MmsInventoryCountDetail.findByyear", MmsInventoryCountDetail.class);
        query.setParameter("budgetYear", Year);
        try {
            List<MmsInventoryCountDetail> inventoryInformations = new ArrayList(query.getResultList());
            return inventoryInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public List<MmsInventoryCountDetail> getInventoryListByyear(String name) {
        Query query = em.createNamedQuery("MmsInventoryCountDetail.findListByyear", MmsInventoryCountDetail.class);
        query.setParameter("budgetYear", name);
        try {
            List<MmsInventoryCountDetail> inventoryInformations = new ArrayList(query.getResultList());
            return inventoryInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsInventoryCounting getLastInvNO() {
        Query query = em.createNamedQuery("MmsInventoryCounting.findByInventoryIdMaximum");
        MmsInventoryCounting result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsInventoryCounting) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryCounting> searchByParameterInventoryNoAndStoreId(MmsInventoryCounting inventoryCountEntity) {
        Query query = em.createNamedQuery("MmsInventoryCounting.findByInventoryNoAndStoreId", MmsInventoryCounting.class);
        query.setParameter("workUnit", inventoryCountEntity.getWorkUnit());
        query.setParameter("inventoryCountNo", inventoryCountEntity.getInventoryCountNo() + '%');
        try {
            ArrayList<MmsInventoryCounting> InventoryList = new ArrayList(query.getResultList());
            return InventoryList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryCounting> searchByParameterInventoryNoAndStoreIdAndProcessedBy(MmsInventoryCounting inventoryCountEntity) {
        Query query = em.createNamedQuery("MmsInventoryCounting.findByInventoryNoAndStoreIdAndProcessedBy", MmsInventoryCounting.class);
        query.setParameter("workUnit", inventoryCountEntity.getWorkUnit());
        query.setParameter("processedBy", inventoryCountEntity.getProcessedBy());
        query.setParameter("inventoryCountNo", inventoryCountEntity.getInventoryCountNo() + '%');
        try {
            ArrayList<MmsInventoryCounting> InventoryList = new ArrayList(query.getResultList());
            return InventoryList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryCounting> searchByStoreId(MmsInventoryCounting inventoryCountEntity) {
        Query query = em.createNamedQuery("MmsInventoryCounting.findByStoreId", MmsInventoryCounting.class);
        query.setParameter("workUnit", inventoryCountEntity.getWorkUnit());

        try {
            ArrayList<MmsInventoryCounting> InventoryList = new ArrayList(query.getResultList());
            return InventoryList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryCounting> searchByStoreIdAndProcessedBy(MmsInventoryCounting inventoryCountEntity) {
        Query query = em.createNamedQuery("MmsInventoryCounting.findByStoreIdAndProcessedBy", MmsInventoryCounting.class);
        query.setParameter("workUnit", inventoryCountEntity.getWorkUnit());
        query.setParameter("processedBy", inventoryCountEntity.getProcessedBy());

        try {
            ArrayList<MmsInventoryCounting> InventoryList = new ArrayList(query.getResultList());
            return InventoryList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryCounting> searchByAllParameters(MmsInventoryCounting inventoryCountEntity) {
        Query query = em.createNamedQuery("MmsInventoryCounting.findByInventoryNoAndStoreId", MmsInventoryCounting.class);
        query.setParameter("workUnit", inventoryCountEntity.getWorkUnit());
        query.setParameter("inventoryCountNo", inventoryCountEntity.getInventoryCountNo() + '%');
        query.setParameter("processedBy", inventoryCountEntity.getProcessedBy() + '%');
        query.setParameter("budgetYear", inventoryCountEntity.getBudgetYear() + '%');
        try {
            ArrayList<MmsInventoryCounting> InventoryList = new ArrayList(query.getResultList());
            return InventoryList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryCounting> findInventoryNumberListByWfStatus(int status) {
        Query query = em.createNamedQuery("MmsInventoryCounting.findByWfStatus", MmsInventoryCounting.class);
        query.setParameter("status", status);
        try {
            ArrayList<MmsInventoryCounting> listofinventory = new ArrayList(query.getResultList());
            return listofinventory;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsInventoryCounting> getCountingListsByParameter(MmsInventoryCounting inventoryCountEntity) {
        List<MmsInventoryCounting> colValueLists = new ArrayList<>();
        if (inventoryCountEntity.getColumnName() != null && inventoryCountEntity.getColumnValue() != null) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_INVENTORY_COUNTING\n"
                    + "   WHERE " + inventoryCountEntity.getColumnName().toLowerCase() + "='" + inventoryCountEntity.getColumnValue() + "'"
                    + " ", MmsInventoryCounting.class);
            try {

                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("MmsInventoryCounting.findByApprovedBy");
            query.setParameter("approvedBy", inventoryCountEntity.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<MmsInventoryCounting> getCountingListsByParameterChekOrApprove(MmsInventoryCounting inventoryCountEntity, int status) {
        List<MmsInventoryCounting> colValueLists = new ArrayList<>();
        if (inventoryCountEntity.getColumnName() != null && inventoryCountEntity.getColumnValue() != null) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_INVENTORY_COUNTING\n"
                    + "   WHERE " + inventoryCountEntity.getColumnName().toLowerCase() + "='" + inventoryCountEntity.getColumnValue() + "' and STATUS='" + status + "'"
                    + " ", MmsInventoryCounting.class);
            try {

                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("MmsInventoryCounting.findByApprovedBy");
            query.setParameter("approvedBy", inventoryCountEntity.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_INVENTORY_COUNTING')\n"
                + "   and COLUMN_NAME NOT IN ('BUDGET_YEAR','CHECKED_DATE','WAREHOUSE_ID','COUNTED_BY_2','COUNTED_BY_3','WORK_UNIT','APPROVED_STATUS','REMARK','COMMITTEE_MEMBERS','COMMITTEE_ID','ACTIVE_ACCOUNTING_PERIOD','COMMENT_GIVEN','STATUS','COUNT_TYPE')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<String> RetrivedColumns = new ArrayList<>();
            RetrivedColumns = query.getResultList();
            List<ColumnNameResolver> ResolvedCol_list = new ArrayList<>();
            if (RetrivedColumns.size() > 0) {
                for (int i = 0; i < RetrivedColumns.size(); i++) {
                    ColumnNameResolver obj = new ColumnNameResolver();
                    System.out.println("RetrivedColumns.get(i)===" + RetrivedColumns.get(i));
                    obj.setCol_Name_FromTable(RetrivedColumns.get(i));
                    obj.setParsed_Col_Name(ColumnParser(RetrivedColumns.get(i)).toLowerCase());
                    ResolvedCol_list.add(obj);
                }
            }
            return ResolvedCol_list;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }
}
