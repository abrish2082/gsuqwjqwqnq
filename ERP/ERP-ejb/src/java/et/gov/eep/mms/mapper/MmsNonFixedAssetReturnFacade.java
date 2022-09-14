
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturn;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.ListModel;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsNonFixedAssetReturnFacade extends AbstractFacade<MmsNonFixedAssetReturn> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsNonFixedAssetReturnFacade() {
        super(MmsNonFixedAssetReturn.class);
    }

    public MmsNonFixedAssetReturn getLastReturnId() {

        Query query1 = em.createNamedQuery("MmsNonFixedAssetReturn.findByNfaIdMaximum");

        MmsNonFixedAssetReturn result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsNonFixedAssetReturn) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsNonFixedAssetReturn> searchByReturnNo(MmsNonFixedAssetReturn returnEntity) {
        Query query = em.createNamedQuery("MmsNonFixedAssetReturn.findByNfaNoLike");
        query.setParameter("nfaNo", returnEntity.getNfaNo() + '%');
        try {
            ArrayList<MmsNonFixedAssetReturn> listofNFANo = new ArrayList(query.getResultList());
            return listofNFANo;
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsNonFixedAssetReturn> searchNFReturnByParameterPrefix(MmsNonFixedAssetReturn nonFxdRetEntity) {
        Query query = em.createNamedQuery("MmsNonFixedAssetReturn.findByAllParameters");
        query.setParameter("nfaNo", '%' + nonFxdRetEntity.getNfaNo() + '%');
        try {
            ArrayList<MmsNonFixedAssetReturn> NFRList = new ArrayList(query.getResultList());
            return NFRList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsNonFixedAssetReturn getSelectedRequest(BigDecimal nfaId) {
        Query query = em.createNamedQuery("MmsNonFixedAssetReturn.findByNfaId");
        query.setParameter("nfaId", nfaId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsNonFixedAssetReturn selectrequest = (MmsNonFixedAssetReturn) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsNonFixedAssetReturn> findNFAListByWfStatus(int CHECK_APPROVE_VALUE) {
        Query query = em.createNamedQuery("MmsNonFixedAssetReturn.findNfaListByWfStatus", MmsNonFixedAssetReturn.class);
        query.setParameter("retStatus", CHECK_APPROVE_VALUE);
        try {
            ArrayList<MmsNonFixedAssetReturn> listofNfa = new ArrayList(query.getResultList());
            return listofNfa;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsNonFixedAssetReturn> findNFAListForCheckerByWfStatus(int PREPARE_VALUE, int APPROVE_REJECT_VALUE) {
        Query query = em.createNamedQuery("MmsNonFixedAssetReturn.findNfaListForCheckerByWfStatus", MmsNonFixedAssetReturn.class);
        query.setParameter("prepared", PREPARE_VALUE);
        query.setParameter("approverReject", APPROVE_REJECT_VALUE);

        try {
            ArrayList<MmsNonFixedAssetReturn> listofNfa = new ArrayList(query.getResultList());
            return listofNfa;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsSivDetail> getItemCodeLists(MmsStoreInformation storeInfoEntity) {
        System.out.println("facade here");
        Query q = em.createNativeQuery("select svd.* from mms_siv_detail svd\n"
                + "inner join mms_siv sv\n"
                + "inner join mms_store_information st\n"
                + "on st.store_id=sv.store_id\n"
                + "on svd.siv_id=sv.siv_id\n"
                + "where sv.store_id = '" + storeInfoEntity.getStoreId() + "'",MmsSivDetail.class);
        System.out.println("id------"+ storeInfoEntity.getStoreId());
        q.setParameter("sivId", storeInfoEntity.getStoreId());
        try {
            List<MmsSivDetail> itemCodes = null;
            if (q.getResultList().size() > 0) {
                itemCodes = new ArrayList<>(q.getResultList());
            }
            return itemCodes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
   public List<MmsNonFixedAssetReturn> getNonreturnListsByParameter(MmsNonFixedAssetReturn nonFxdRetEntity) {
       List<MmsNonFixedAssetReturn> colValueLists = new ArrayList<>();
       System.out.println("nonFxdRetEntity.getColumnName()==="+nonFxdRetEntity.getColumnName());
        System.out.println("nonFxdRetEntity.getColumnValue()==="+nonFxdRetEntity.getColumnValue());
       
        if (nonFxdRetEntity.getColumnName() != null  && nonFxdRetEntity.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_NON_FIXED_ASSET_RETURN\n"
                    + "   WHERE " + nonFxdRetEntity.getColumnName().toLowerCase() + "='" + nonFxdRetEntity.getColumnValue() + "'"
                    + " ", MmsNonFixedAssetReturn.class);
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
            Query query = em.createNamedQuery("MmsNonFixedAssetReturn.findByReturnedBy");
            query.setParameter("returnedBy", nonFxdRetEntity.getReturnedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_NON_FIXED_ASSET_RETURN')\n"
                + "   and  column_name NOT IN ('REMARK','RETURN_STATUS','STORE_ID','SELECT_OPT','RETURN2_DATE','RECIEVED2_DATE','RET_STATUS')\n"
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


