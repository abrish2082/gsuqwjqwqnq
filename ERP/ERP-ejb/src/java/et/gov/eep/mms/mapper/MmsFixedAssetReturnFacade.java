
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.internal.queries.ArrayListContainerPolicy;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsFixedAssetReturnFacade extends AbstractFacade<MmsFixedAssetReturn> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsFixedAssetReturnFacade() {
        super(MmsFixedAssetReturn.class);
    }

    public ArrayList<MmsFixedAssetReturn> searchByReturnNo(MmsFixedAssetReturn returnEntity) {
        Query query = em.createNamedQuery("MmsFixedAssetReturn.findByFarNoLike");
        query.setParameter("farNo", returnEntity.getFarNo() + '%');
        System.out.println("======facade=====" + returnEntity.getFixedAssetList());
        try {
            ArrayList<MmsFixedAssetReturn> listofFARNo = new ArrayList(query.getResultList());
            return listofFARNo;
        } catch (Exception ex) {
            return null;
        }

    }

    public MmsFixedAssetReturn getLastReturnId() {

        Query query1 = em.createNamedQuery("MmsFixedAssetReturn.findByFarIdMaximum");

        MmsFixedAssetReturn result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsFixedAssetReturn) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedAssetReturn> searchReturnByParameterPrefix(MmsFixedAssetReturn returnEntity) {
        Query query = em.createNamedQuery("MmsFixedAssetReturn.findByAllParameters");
        query.setParameter("farNo", '%' + returnEntity.getFarNo() + '%');
        try {
            ArrayList<MmsFixedAssetReturn> insuranceList = new ArrayList(query.getResultList());
            return insuranceList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedAssetReturn> searchReturnByParameterPrefixAndProcessedBy(MmsFixedAssetReturn returnEntity) {
        Query query = em.createNamedQuery("MmsFixedAssetReturn.findByAllParametersAndProcessedBy");
        query.setParameter("farNo", '%' + returnEntity.getFarNo() + '%');
        query.setParameter("processedBy", returnEntity.getProcessedBy());
        try {
            ArrayList<MmsFixedAssetReturn> insuranceList = new ArrayList(query.getResultList());
            return insuranceList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedAssetReturn> searchByDept(MmsFixedAssetReturn returnEntity) {
        Query query = em.createNamedQuery("MmsFixedAssetReturn.findByAllParameters2");
        query.setParameter("department", '%' + returnEntity.getFarNo() + '%');
        try {
            ArrayList<MmsFixedAssetReturn> insuranceList = new ArrayList(query.getResultList());
            return insuranceList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFixedAssetReturn getSelectedRequest(Integer farId) {
        Query query = em.createNamedQuery("MmsFixedAssetReturn.findByFarId");
        query.setParameter("farId", farId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFixedAssetReturn selectrequest = (MmsFixedAssetReturn) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedAssetReturn> findFarListByWfStatus(int status) {
        Query query = em.createNamedQuery("MmsFixedAssetReturn.findFarListByWfStatus", MmsFixedAssetReturn.class);
        query.setParameter("status", status);
        try {
            ArrayList<MmsFixedAssetReturn> listofsr = new ArrayList(query.getResultList());
            return listofsr;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsFixedAssetReturn> findFarListForCheckerByWfStatus(int preparerStatus, int approverRejectStatus) {
        Query query = em.createNamedQuery("MmsFixedAssetReturn.findFarListForCheckerByWfStatus", MmsFixedAssetReturn.class);
        query.setParameter("prepared", preparerStatus);
        query.setParameter("approverReject", approverRejectStatus);

        try {
            ArrayList<MmsFixedAssetReturn> listofsr = new ArrayList(query.getResultList());
            return listofsr;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsFixedAssetReturn> searchAllFarInfoByPreparerId(Integer processedBy) {
        Query query = em.createNamedQuery("MmsFixedAssetReturn.findAllByPreparerId", MmsFixedAssetReturn.class);
        query.setParameter("processedBy", processedBy);
        System.out.println("======@facade====" + processedBy);
        try {
            ArrayList<MmsFixedAssetReturn> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedassetRegstDetail> getReturnByDepId(MmsFixedassetRegstration fixedassetRegstration) {
        System.out.println("here");
        Query q = em.createNamedQuery("select dt.* from mms_fixedasset_regst_detail dt\n"
                + "inner join mms_fixedasset_regstration ma\n"
                + "on dt.far_id=ma.id\n"
                + "where ma.id='" + fixedassetRegstration.getId() + "'");
        try {
            System.out.println("===");
            List<MmsFixedassetRegstDetail> returnBys = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                System.out.println("==========" + returnBys.size());
                returnBys = q.getResultList();
            }
            return returnBys;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Integer> getEmpIdByName(String returnby) {
        System.out.println("facade " + returnby);
        Query q = em.createNativeQuery("select hr.id from hr_employees hr\n"
                + "where hr.first_name='" + returnby + "'");
        try {
            List<Integer> EmpId = new ArrayList();
            if (q.getResultList().size() > 0) {
                EmpId = q.getResultList();
                System.out.println("size     " + EmpId.size());
            }
            return EmpId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MmsFixedassetRegstDetail gettingTagInfo(String tingOthersByTagNo) {
        System.out.println("get here " + tingOthersByTagNo);
        Query query = em.createNativeQuery("SELECT * from mms_fixedasset_regst_detail \n"
                + "where tag_no='" + tingOthersByTagNo + "'", MmsFixedassetRegstDetail.class);
        try {
            MmsFixedassetRegstDetail tagInfo = new MmsFixedassetRegstDetail();
            if (query.getResultList().size() > 0) {
                System.out.println(" existed");
                tagInfo = (MmsFixedassetRegstDetail) query.getResultList().get(0);
                System.out.println(" " + tagInfo.getItemName());
                System.out.println(" " + tagInfo.getItemId().getMatCode());
            }
            return tagInfo;
        } catch (Exception e) {
            return null;
        }
    }
  public List<MmsFixedAssetReturn> getReturnListsByParameter(MmsFixedAssetReturn returnEntity) {
        List<MmsFixedAssetReturn> colValueLists = new ArrayList<>();
        if (returnEntity.getColumnName() != null  && returnEntity.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_FIXED_ASSET_RETURN\n"
                   + "   WHERE " + returnEntity.getColumnName().toLowerCase() + "='" + returnEntity.getColumnValue() + "'"
                    + " ", MmsFixedAssetReturn.class);
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
            Query query = em.createNamedQuery("MmsFixedAssetReturn.findByProposedBy");
            query.setParameter("proposedBy", returnEntity.getProposedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_FIXED_ASSET_RETURN')\n"
                + "   and COLUMN_NAME NOT IN ('REMARK','RETURN_STATUS','STORE_ID','STATUS','FIXEDAR_DT_ID','RCV_DATE','RET_DATE')\n"
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
