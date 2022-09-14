
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsDisposalItems;
import et.gov.eep.mms.entity.MmsDisposalItemsDtl;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedAssetTransfer;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturn;
import et.gov.eep.mms.entity.MmsStockDisposal;
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
public class MmsDisposalItemsFacade extends AbstractFacade<MmsDisposalItems> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsDisposalItemsFacade() {
        super(MmsDisposalItems.class);
    }

    public List<MmsDisposalItems> searchDisposalByParameterPrefix(MmsDisposalItems disposalEntity) {
        Query query = em.createNamedQuery("MmsDisposalItems.findByAllParameters");
        query.setParameter("disposalNo", '%' + disposalEntity.getDisposalNo() + '%');
        try {
            ArrayList<MmsDisposalItems> DisposalList = new ArrayList(query.getResultList());
            return DisposalList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsDisposalItems> searchByDispNo(MmsDisposalItems disposalEntity) {
        Query query = em.createNamedQuery("MmsDisposalItems.findByDisposalNoLike");
        query.setParameter("disposalNo", disposalEntity.getDisposalNo() + '%');
        try {
            ArrayList<MmsDisposalItems> listofFADNo = new ArrayList(query.getResultList());
            return listofFADNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsDisposalItems getLastDisposalId() {

        Query query1 = em.createNamedQuery("MmsDisposalItems.findByDisposalIdMaximum");

        MmsDisposalItems result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsDisposalItems) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsDisposalItems> getDispInfoByDispNo1(MmsDisposalItems dispNo) {

        Query query = em.createNamedQuery("MmsDisposalItems.findByDisposalNo");
        query.setParameter("disposalNo", dispNo.getDisposalNo());
        List<MmsDisposalItems> result;
        try {

            result = new ArrayList<>(query.getResultList());

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsDisposalItems getSelectedRequest(BigDecimal disposalId) {
        Query query = em.createNamedQuery("MmsDisposalItems.findByDisposalId");
        query.setParameter("disposalId", disposalId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsDisposalItems selectrequest = (MmsDisposalItems) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsDisposalItems> searchDisposalByParameterPrefixAndItemDispPrep(MmsDisposalItems disposalEntity) {
        Query query = em.createNamedQuery("MmsDisposalItems.findByAllParametersAndDipPrep");
        query.setParameter("disposalNo", disposalEntity.getDisposalNo());
        query.setParameter("requestedBy", disposalEntity.getRequestedBy());
        try {
            ArrayList<MmsDisposalItems> DisposalList = new ArrayList(query.getResultList());
            return DisposalList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsDisposalItems> findDispListByWfStatus(int dStatus) {
        Query query = em.createNamedQuery("MmsDisposalItems.findDispListByWfStatus", MmsDisposalItems.class);
        query.setParameter("dStatus", dStatus);
        try {
            ArrayList<MmsDisposalItems> listofdisp = new ArrayList(query.getResultList());
            return listofdisp;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsDisposalItems> searchAllTransmissionsInfoByPreparerId(Integer requestedBy) {
        Query query = em.createNamedQuery("MmsDisposalItems.findAllByPreparerId", MmsDisposalItems.class);

        query.setParameter("requestedBy", requestedBy);
        System.out.println("======@facade====" + requestedBy);
        try {
            ArrayList<MmsDisposalItems> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsDisposalItemsDtl> getTagNoLists(MmsDisposalItems dispColectionEntity) {
        System.out.println("to get a lists");
        Query q = em.createNamedQuery("MmsDisposalItemsDtl.findByDisposalId");
        System.out.println("selcted disposal id===" + dispColectionEntity.getDisposalId());
        q.setParameter("disposalId", dispColectionEntity.getDisposalId());
        try {
            List<MmsDisposalItemsDtl> tagNos = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                tagNos = q.getResultList();
            }
            System.out.println("sizes====" + tagNos.size());
            return tagNos;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsDisposalItems> findAllbyApproveStatus(int Status) {
       System.out.println("to get a lists");
        Query q = em.createNamedQuery("MmsDisposalItems.findAllbyApproveStatus");
        System.out.println("Status ===" + Status);
        q.setParameter("dStatus", Status);
        try {
            List<MmsDisposalItems> disposalStatus = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                disposalStatus = q.getResultList();
            }
            System.out.println("sizes====" + disposalStatus.size());
            return disposalStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
        }

    public MmsDisposalItemsDtl findByTag(MmsDisposalItemsDtl dispCollectionDtlEntity) {
       Query query = em.createNamedQuery("MmsDisposalItemsDtl.findByTagNo");
        query.setParameter("tagNo", dispCollectionDtlEntity.getTagNo());
        System.out.println("============ tag no ==="+  dispCollectionDtlEntity.getTagNo());
        MmsDisposalItemsDtl result = new MmsDisposalItemsDtl();
          try {
            if (query.getResultList().size() > 0) {
                result = (MmsDisposalItemsDtl) query.getResultList().get(0);
                System.out.println("=======================@Facade=====" + result);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
   public List<MmsDisposalItems> getItemdisposalListsByParameter(MmsDisposalItems disposalEntity) {
        List<MmsDisposalItems> colValueLists = new ArrayList<>();
        if (disposalEntity.getColumnName() != null && disposalEntity.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_DISPOSAL_ITEMS\n"
                 + "   WHERE " + disposalEntity.getColumnName().toLowerCase() + "='" + disposalEntity.getColumnValue() + "'"
                    + "", MmsDisposalItems.class);
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
            Query query = em.createNamedQuery("MmsDisposalItems.findByRequestedBy");
            query.setParameter("RequestedBy", disposalEntity.getRequestedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_DISPOSAL_ITEMS')\n"
                + "   and COLUMN_NAME NOT IN ('DISPOSAL_STATUS','REMARK','REQUISITION_DATE','STORE_ID','SELECT_OPT','D_STATUS','REQUISITION_DATE','APP_DATE','REQ_DATE')\n"
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



