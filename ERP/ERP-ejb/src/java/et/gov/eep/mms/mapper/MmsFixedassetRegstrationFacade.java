
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStorereq;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsFixedassetRegstrationFacade extends AbstractFacade<MmsFixedassetRegstration> {

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
    public MmsFixedassetRegstrationFacade() {
        super(MmsFixedassetRegstration.class);
    }

    /**
     *
     * @return
     */
    public MmsFixedassetRegstration getLastFARNo() {
        Query query = em.createNamedQuery("MmsFixedassetRegstration.findByFARIdMaximum");
        MmsFixedassetRegstration result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsFixedassetRegstration) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedassetRegstration> getFixedAssetInfoByDept(MmsFixedassetRegstration fixedassetRegstration) {
        Query query = em.createNamedQuery("MmsFixedassetRegstration.findByDept");
        query.setParameter("department", fixedassetRegstration.getDepartment());
        List<MmsFixedassetRegstration> result;
        try {

            result = new ArrayList<>(query.getResultList());
            System.out.println("=======================@Facade=====" + result);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedassetRegstration> getFixedAssetInfoByRecivedBy(MmsFixedassetRegstration fixedassetRegstration) {
        Query query = em.createNamedQuery("MmsFixedassetRegstration.findByRecivedBy");
        query.setParameter("recivedBy", fixedassetRegstration.getRecivedBy());
        System.out.println("----------@Facade fxr ---------- " + fixedassetRegstration.getRecivedBy());
        List<MmsFixedassetRegstration> result;
        try {

            result = new ArrayList<>(query.getResultList());
            System.out.println("=======================@Facade=====" + result);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedassetRegstration> searchFARByParameterPrefix(MmsFixedassetRegstration fixedAssetRegEntity) {
        System.out.println(".....@Facade......" + fixedAssetRegEntity.getFarNo());
        Query query = em.createNamedQuery("MmsFixedassetRegstration.findByAllParameters");
        query.setParameter("farNo", '%' + fixedAssetRegEntity.getFarNo() + '%');
        try {
            ArrayList<MmsFixedassetRegstration> FARNoList = new ArrayList(query.getResultList());
            System.out.println("------------ Item Name @ Facade -----------" + FARNoList);
            return FARNoList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFixedassetRegstration getSelectedRequest(Integer id) {
        Query query = em.createNamedQuery("MmsFixedassetRegstration.findById");
        query.setParameter("id", id);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFixedassetRegstration selectrequest = (MmsFixedassetRegstration) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFixedassetRegstration findByMasterId(MmsFixedassetRegstration fixedAsserRegEntity) {
        Query query = em.createNamedQuery("MmsFixedassetRegstration.findById");
        query.setParameter("id", fixedAsserRegEntity.getId());
        MmsFixedassetRegstration result;
        try {

            result = (MmsFixedassetRegstration) query.getResultList().get(0);
            System.out.println("=======================@Facade=====" + result);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFixedassetRegstration findByNameAndTagNo(MmsFixedassetRegstration fixedassetRegstration, MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        try {
            System.out.println("----------------Inside Facade --------------------");
            System.out.println("-----Tag no ------" + fixedAssetRegDtlEntity.getTagNo());
            System.out.println("--------Name ---------" + fixedassetRegstration.getRecivedBy());
            Query query = em.createNativeQuery("select * FROM mms_fixedasset_regst_detail fadl "
                    + "INNER JOIN mms_fixedasset_regstration fam "
                    + "on fadl.far_id= fam.id "
                    + "WHERE fam.recived_by='" + fixedassetRegstration.getRecivedBy() + "' "
                    + "And fadl.tag_no='" + fixedAssetRegDtlEntity.getTagNo() + "'", MmsFixedassetRegstration.class);
            MmsFixedassetRegstration Result = (MmsFixedassetRegstration) query.getSingleResult();
            System.out.println("------- Result -----------" + Result);

            return Result;
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsFixedassetRegstration> findFarListByWfStatus(int PREPARE_VALUE) {
        Query query = em.createNamedQuery("MmsFixedassetRegstration.findFarListByWfStatus", MmsFixedassetRegstration.class);
        query.setParameter("faStatus", PREPARE_VALUE);
        try {
            ArrayList<MmsFixedassetRegstration> listoffar = new ArrayList(query.getResultList());
            return listoffar;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsFixedassetRegstration> searchAllFarInfoByPreparerId(Integer approvedBy) {
        Query query = em.createNamedQuery("MmsFixedassetRegstration.findAllByPreparerId", MmsFixedassetRegstration.class);

        query.setParameter("approvedBy", approvedBy);
        System.out.println("======@facade====" + approvedBy);
        try {
            ArrayList<MmsFixedassetRegstration> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<FmsDprOfficeAsset> findBookvalue(String tagNo) {
        System.out.println("facade here");
        Query query = em.createNativeQuery("SELECT FMS_DPR_OFFICE_ASSET.TAG_NO,\n"
                + "               FMS_DPR_OFFICE_ASSET.NET_BOOK_VALUE,\n"
                + "                 FMS_DPR_OFFICE_ASSET.STATUS,\n"
                + "                  FMS_DPR_OFFICE_ASSET.DPR_OFFICE_ID\n"
                + "              FROM FMS_DPR_OFFICE_ASSET\n"
                + "              WHERE FMS_DPR_OFFICE_ASSET.TAG_NO = '" + tagNo + "'", FmsDprOfficeAsset.class);
        try {
            ArrayList<FmsDprOfficeAsset> bookList = null;
            if (query.getResultList().size() > 0) {
                bookList = new ArrayList(query.getResultList());
            }
            return bookList;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public MmsSiv getFixedSrNo(MmsSiv sivs) {
        System.out.println("thus facade");
        Query q = em.createNamedQuery("MmsSiv.findBySivIdForSrNo");
        q.setParameter("sivId", sivs.getSivId());
        System.out.println("siv id " + sivs.getSivId());
        try {
            System.out.println("1");
            MmsSiv srNo = new MmsSiv();
            if (q.getResultList().size() > 0) {
                System.out.println("2");
                srNo = (MmsSiv) (q.getResultList().get(0));
                System.out.println("sr no====" + srNo.getSrNo());
            }
            return srNo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MmsItemRegistration getcode(MmsItemRegistration itemRegistrationEntity) {
        System.out.println("thus facade");
        Query q = em.createNamedQuery("MmsItemRegistration.findBycode");
        q.setParameter("materialId", itemRegistrationEntity.getMaterialId());
        System.out.println("materialId " + itemRegistrationEntity.getMaterialId());
        try {
            System.out.println("1");
            MmsItemRegistration MaterialId = new MmsItemRegistration();
            if (q.getResultList().size() > 0) {
                System.out.println("2");
                MaterialId = (MmsItemRegistration) (q.getResultList().get(0));
                System.out.println("sr no====" + MaterialId.getMatCode());
            }
            return MaterialId;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MmsSiv> getSrNoLists(MmsSiv sivs) {
        System.out.println("face");
        Query q = em.createNamedQuery("MmsSiv.findBySivIdForSrNo");
        q.setParameter("sivId", sivs.getSivId());
        try {
            List<MmsSiv> srNos = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                srNos = (q.getResultList());
                System.out.println("size====" + q.getResultList().size());
                System.out.println("size====" + q.getResultList());
            }
            return srNos;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsSivDetail> getItemBySivId(MmsSiv sivs) {
        Query q = em.createNamedQuery("MmsSivDetail.findbysivIds", MmsSivDetail.class);
        q.setParameter("sivId", sivs.getSivId());
        try {
            List<MmsSivDetail> itemList = new ArrayList<>();
            if (q.getResultList().size() > 0) {             
                itemList = (q.getResultList());               
                System.out.println("lists===" + itemList);
            }
            return itemList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MmsStorereq> getsrNoListsByTag(HrDepartments hrDepartmentsEntity) {
        Query q = em.createNamedQuery("MmsStorereq.findByDepIdAndSrType");
        q.setParameter("depId", hrDepartmentsEntity.getDepId());
        try {
            List<MmsStorereq> srLists = new ArrayList();
            if (q.getResultList().size() > 0) {
                srLists = q.getResultList();
            }
            return srLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
     public List<MmsFixedassetRegstration> getMmsFixedAssetRColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_FIXEDASSET_REGSTRATION')\n"
                + "   and COLUMN_NAME NOT IN ('SELECT_OPT','ID','DELIVERED_BY','APPROVAL_DATE','DELIVERY_DATE','RECIVED_BY')\n"
                + "   ORDER BY column_name ASC"); 
        try {
            List<MmsFixedassetRegstration> colNameLists = new ArrayList<>();
            colNameLists = query.getResultList();
            return colNameLists;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public List<MmsFixedassetRegstration> getFAListsByParameter(MmsFixedassetRegstration fixedAssetRegEntity) {
       System.out.println("it's facade");
        System.out.println("here I facade colName " + fixedAssetRegEntity.getColumnName() + " colVal " + fixedAssetRegEntity.getColumnValue());
        List<MmsFixedassetRegstration> colValueLists = new ArrayList<>();
        if (fixedAssetRegEntity.getColumnName() != null && !fixedAssetRegEntity.getColumnName().equals("")
                && fixedAssetRegEntity.getColumnValue() != null && !fixedAssetRegEntity.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_FIXEDASSET_REGSTRATION\n"
                    + "   WHERE " + fixedAssetRegEntity.getColumnName().toLowerCase() + "='" + fixedAssetRegEntity.getColumnValue() + "'"
                    + "and " + fixedAssetRegEntity.getApprovedBy() + "='" + fixedAssetRegEntity.getApprovedBy() + "'", MmsFixedassetRegstration.class);
            try {

                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                    System.out.println("list of FA size " + colValueLists.size());
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            System.out.println("In else " + colValueLists.size());
            Query query = em.createNamedQuery("MmsFixedassetRegstration.findByApprovedBy");
            query.setParameter("approvedBy", fixedAssetRegEntity.getApprovedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }
    public List<MmsFixedassetRegstration> getAssetregstrationListsByParameter(MmsFixedassetRegstration fixedAssetRegEntity) {
        List<MmsFixedassetRegstration> colValueLists = new ArrayList<>();
        if (fixedAssetRegEntity.getColumnName() != null  && fixedAssetRegEntity.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_FIXEDASSET_REGSTRATION\n"
                  + "   WHERE " + fixedAssetRegEntity.getColumnName().toLowerCase() + "='" + fixedAssetRegEntity.getColumnValue() + "'"
                    + " ", MmsFixedassetRegstration.class);
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
            Query query = em.createNamedQuery("MmsFixedassetRegstration.findByApprovedBy");
            query.setParameter("ApprovedBy", fixedAssetRegEntity.getApprovedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_FIXEDASSET_REGSTRATION')\n"
                + "   and COLUMN_NAME NOT IN ('REMARK','DELIVERER_REMARK','SYSTEM_NO','SELECT_OPT','APP_DATE','DELIV_DATE','FA_STATUS')\n"
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


