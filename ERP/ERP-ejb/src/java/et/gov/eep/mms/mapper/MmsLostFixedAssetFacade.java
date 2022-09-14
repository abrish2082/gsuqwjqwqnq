package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsLostFixedAssetFacade extends AbstractFacade<MmsLostFixedAsset> {

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
    public MmsLostFixedAssetFacade() {
        super(MmsLostFixedAsset.class);
    }

    /**
     *
     * @return
     */
    public MmsLostFixedAsset getLastLostItemId() {
        Query query = em.createNamedQuery("MmsLostFixedAsset.findBylostItemIdMaximum");
        MmsLostFixedAsset result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsLostFixedAsset) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param lostitemno
     * @return
     */
    public ArrayList<MmsLostFixedAsset> searchByLostItemNo(MmsLostFixedAsset lostitemno) {
        Query query = em.createNamedQuery("MmsLostFixedAsset.findByLostItemNoLike");
        query.setParameter("lostItemNo", lostitemno.getLostItemNo() + '%');
        try {
            ArrayList<MmsLostFixedAsset> listoflostItemNo = new ArrayList(query.getResultList());
            return listoflostItemNo;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param lostFixedAsset
     * @return
     */
    public MmsLostFixedAsset getLostItemInfoByItemNo(MmsLostFixedAsset lostFixedAsset) {
        Query query = em.createNamedQuery("MmsLostFixedAsset.findByLostItemNo");
        query.setParameter("lostItemNo", lostFixedAsset.getLostItemNo());
        MmsLostFixedAsset result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsLostFixedAsset) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param lostFxAssetInfo
     * @return
     */
    public List<MmsLostFixedAsset> searchLostItemByParameterPrefix(MmsLostFixedAsset lostFxAssetInfo) {
        Query query = em.createNamedQuery("MmsLostFixedAsset.findByAllParameters");
        query.setParameter("lostItemNo", '%' + lostFxAssetInfo.getLostItemNo() + '%');

        try {
            ArrayList<MmsLostFixedAsset> lostItemList = new ArrayList(query.getResultList());
            return lostItemList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsLostFixedAsset getSelectedRequest(Integer lostItemId) {
        Query query = em.createNamedQuery("MmsLostFixedAsset.findByLostItemId");
        query.setParameter("lostItemId", lostItemId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsLostFixedAsset selectrequest = (MmsLostFixedAsset) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsLostFixedAsset searchById(MmsLostFixedAsset LostObj) {

        try {
            System.out.println("inside facade");
            Query query = em.createNamedQuery("MmsLostFixedAsset.findByLostItemId");
            query.setParameter("lostItemId", LostObj.getLostItemId());

            return (MmsLostFixedAsset) query.getSingleResult();
        } catch (Exception ex) {

            return null;
        }

    }

    public List<MmsLostFixedAsset> searchLostItemByParameterPrefixAndLostPrep(MmsLostFixedAsset lostFxAssetEntity) {

        Query query = em.createNamedQuery("MmsLostFixedAsset.findByAllParametersAndEmpName");
        query.setParameter("lostItemNo", lostFxAssetEntity.getLostItemNo());
        query.setParameter("employeeName", lostFxAssetEntity.getEmployeeName());

        try {
            ArrayList<MmsLostFixedAsset> lostItemList = new ArrayList(query.getResultList());
            return lostItemList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsLostFixedAsset> findLostListByWfStatus(int CHECK_APPROVE_VALUE) {
        Query query = em.createNamedQuery("MmsLostFixedAsset.findLostListByWfStatus", MmsLostFixedAsset.class);
        query.setParameter("lostStatus", CHECK_APPROVE_VALUE);
        try {
            ArrayList<MmsLostFixedAsset> listoflost = new ArrayList(query.getResultList());
            return listoflost;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsLostFixedAsset> findLostListForCheckerByWfStatus(int PREPARE_VALUE, int APPROVE_REJECT_VALUE) {
        Query query = em.createNamedQuery("MmsLostFixedAsset.findLostListForCheckerByWfStatus", MmsLostFixedAsset.class);
        query.setParameter("prepared", PREPARE_VALUE);
        query.setParameter("approverReject", APPROVE_REJECT_VALUE);

        try {
            ArrayList<MmsLostFixedAsset> listofLost = new ArrayList(query.getResultList());
            return listofLost;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsLostFixedAsset> searchAllLostInfoByPreparerId(Integer employeeName) {
        Query query = em.createNamedQuery("MmsLostFixedAsset.findAllByPreparerId", MmsLostFixedAsset.class);
        query.setParameter("employeeName", employeeName);
        System.out.println("======@facade====" + employeeName);
        try {
            ArrayList<MmsLostFixedAsset> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsLostFixedAsset> getLostFixedAssetColumnNameLists() {
        Query query = em.createNativeQuery("SELECT COLUMN_NAME FROM USER_TAB_COLUMNS\n"
                + "WHERE TABLE_NAME= UPPER('MMS_LOST_FIXED_ASSET')\n"
                + "and COLUMN_NAME NOT in ('PHOTO','LOST_STATUS','STATUS','REMARK','LOST_ITEM_ID','STORE_ID')");
        try {
            List<MmsLostFixedAsset> lostFAcolNameList = new ArrayList<>();
            lostFAcolNameList = query.getResultList();
            return lostFAcolNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }

    public List<MmsLostFixedAsset> searchLostItemInformation(MmsLostFixedAsset lostFxAssetEntity) {
        System.out.println("colName " + lostFxAssetEntity.getColumnName() + " colValue " + lostFxAssetEntity.getColumnValue());
        Query query = em.createNativeQuery("SELECT * FROM MMS_LOST_FIXED_ASSET\n"
                + "WHERE " + lostFxAssetEntity.getColumnName().toLowerCase() + "='" + lostFxAssetEntity.getColumnValue() + "'", MmsLostFixedAsset.class);
        try {
            List<MmsLostFixedAsset> searchLostItemObjects = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                searchLostItemObjects = query.getResultList();

            }
            return searchLostItemObjects;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }
     public List<MmsLostFixedAsset> getLostListsByParameter(MmsLostFixedAsset lostFxAssetEntity) {
        List<MmsLostFixedAsset> colValueLists = new ArrayList<>();
        if (lostFxAssetEntity.getColumnName() != null && !lostFxAssetEntity.getColumnName().equals("")
                && lostFxAssetEntity.getColumnValue() != null && !lostFxAssetEntity.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * from MMS_LOST_FIXED_ASSET\n"
                    + "   WHERE " + lostFxAssetEntity.getColumnName().toLowerCase() + "='" + lostFxAssetEntity.getColumnValue() + "'"
                    + " ", MmsLostFixedAsset.class);
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
            Query query = em.createNamedQuery("MmsLostFixedAsset.findByAcceptedBy");
            query.setParameter("AcceptedBy", lostFxAssetEntity.getAcceptedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_LOST_FIXED_ASSET')\n"
                + "   and COLUMN_NAME NOT IN ('PHOTO','LOST_STATUS','REMARK','STATUS','STORE_ID','ACCEPTED2_DATE','APPROVED2_DATE','REG_DATE','REFERENCE_NUMBER','ACCEPTED2_DATE','REASON_OF_WRITTEN_OFF','EVIDENCE_ATTACHED_ID','COST_CENTER')\n"
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



