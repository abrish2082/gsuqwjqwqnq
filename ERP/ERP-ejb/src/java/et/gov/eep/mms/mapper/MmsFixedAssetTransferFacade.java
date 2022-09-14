
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedAssetTransfer;
import java.math.BigDecimal;
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
public class MmsFixedAssetTransferFacade extends AbstractFacade<MmsFixedAssetTransfer> {

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
    public MmsFixedAssetTransferFacade() {
        super(MmsFixedAssetTransfer.class);
    }

    /**
     *
     * @param TransferEntity
     * @return
     */
    public ArrayList<MmsFixedAssetTransfer> searchByTransferNo(MmsFixedAssetTransfer TransferEntity) {
        Query query = em.createNamedQuery("MmsFixedAssetTransfer.findByTransferNoLike");
        query.setParameter("transferNo", TransferEntity.getTransferNo() + '%');
        try {
            ArrayList<MmsFixedAssetTransfer> listoftransferNo = new ArrayList(query.getResultList());
            return listoftransferNo;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param TransferEntity
     * @return
     */
    public List<MmsFixedAssetTransfer> searchTransferByParameterPrefix(MmsFixedAssetTransfer TransferEntity) {
        Query query = em.createNamedQuery("MmsFixedAssetTransfer.findByAllParameters");
        query.setParameter("transferNo", '%' + TransferEntity.getTransferNo() + '%');
        try {
            ArrayList<MmsFixedAssetTransfer> transferList = new ArrayList(query.getResultList());
            return transferList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public MmsFixedAssetTransfer getLastTransferId() {
        Query query = em.createNamedQuery("MmsFixedAssetTransfer.findByTransferIdMaximum");
        MmsFixedAssetTransfer result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsFixedAssetTransfer) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFixedAssetTransfer getSelectedRequest(BigDecimal transferId) {
        Query query = em.createNamedQuery("MmsFixedAssetTransfer.findByTransferId");
        query.setParameter("transferId", transferId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFixedAssetTransfer selectrequest = (MmsFixedAssetTransfer) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedAssetTransfer> searchTransferByParameterPrefixAndTrPrep(MmsFixedAssetTransfer TransferEntity) {

        Query query = em.createNamedQuery("MmsFixedAssetTransfer.findByAllParametersAndTrPrep");
        query.setParameter("transferNo", TransferEntity.getTransferNo());
        query.setParameter("preparedBy", TransferEntity.getPreparedBy());
        try {
            ArrayList<MmsFixedAssetTransfer> transferList = new ArrayList(query.getResultList());
            return transferList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedAssetTransfer> findTrListByWfStatus(int CHECK_REJECT_VALUE) {
        Query query = em.createNamedQuery("MmsFixedAssetTransfer.findTrListByWfStatus", MmsFixedAssetTransfer.class);
        query.setParameter("trStatus", CHECK_REJECT_VALUE);
        try {
            ArrayList<MmsFixedAssetTransfer> listoftr = new ArrayList(query.getResultList());
            return listoftr;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsFixedAssetTransfer> findTrListForCheckerByWfStatus(int PREPARE_VALUE, int APPROVE_REJECT_VALUE) {
        Query query = em.createNamedQuery("MmsFixedAssetTransfer.findTrListForCheckerByWfStatus", MmsFixedAssetTransfer.class);
        query.setParameter("prepared", PREPARE_VALUE);
        query.setParameter("approverReject", APPROVE_REJECT_VALUE);

        try {
            ArrayList<MmsFixedAssetTransfer> listoftr = new ArrayList(query.getResultList());
            return listoftr;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsFixedAssetTransfer> searchAlltraInfoByPreparerId(Integer preparedBy) {
        Query query = em.createNamedQuery("MmsFixedAssetTransfer.findAllByPreparerId", MmsFixedAssetTransfer.class);

        query.setParameter("preparedBy", preparedBy);
        System.out.println("======@facade====" + preparedBy);
        try {
            ArrayList<MmsFixedAssetTransfer> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
 
    
    
    public List<MmsFixedAssetTransfer> getTransferListsByParameter(MmsFixedAssetTransfer TransferEntity) {
        List<MmsFixedAssetTransfer> colValueLists = new ArrayList<>();
        if (TransferEntity.getColumnName() != null  && TransferEntity.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_FIXED_ASSET_TRANSFER\n"
                   + "   WHERE " + TransferEntity.getColumnName().toLowerCase() + "='" + TransferEntity.getColumnValue() + "'"
                    + " ", MmsFixedAssetTransfer.class);
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
            Query query = em.createNamedQuery("MmsFixedAssetTransfer.findByPreparedBy");
            query.setParameter("PreparedBy", TransferEntity.getPreparedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
       Query query = em.createNativeQuery("SELECT COLUMN_NAME FROM USER_TAB_COLUMNS\n"
                + "WHERE TABLE_NAME= UPPER('MMS_FIXED_ASSET_TRANSFER')\n"
                + "and COLUMN_NAME NOT in ('TR_STATUS','SELECT_OPT','CHCK_DATE','CHECKED_BY','APP_DATE','RCV_DATE','RECEIVED2_DATE','TDEPARTMENT','RECEIV_DATE','TDEPARTMENT')\n"
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


