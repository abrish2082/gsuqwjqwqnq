package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturnDtl;
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
public class MmsFixedassetRegstDetailFacade extends AbstractFacade<MmsFixedassetRegstDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsFixedassetRegstDetailFacade() {
        super(MmsFixedassetRegstDetail.class);
    }

    public MmsFixedassetRegstDetail getFixedAssetInfoByItemCode(MmsFixedassetRegstDetail fxR) {
        Query query = em.createNamedQuery("MmsFixedassetRegstDetail.findByItemID");
        query.setParameter("itemId", fxR.getItemId());
        MmsFixedassetRegstDetail result;
        try {

            result = (MmsFixedassetRegstDetail) query.getResultList().get(0);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFixedassetRegstDetail findByTag(MmsFixedassetRegstDetail fxR) {
        Query query = em.createNamedQuery("MmsFixedassetRegstDetail.findByTagNo");
        query.setParameter("tagNo", fxR.getTagNo());

        MmsFixedassetRegstDetail result = new MmsFixedassetRegstDetail();
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsFixedassetRegstDetail) query.getResultList().get(0);
                System.out.println("=======================@Facade=====" + result);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedassetRegstDetail> findByTagNo2(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        Query query = em.createNamedQuery("MmsFixedassetRegstDetail.findByTagNo");
        query.setParameter("tagNo", fixedAssetRegDtlEntity.getTagNo());
        System.out.println("======item name" + fixedAssetRegDtlEntity.getItemName());
        System.out.println("========item status===" + fixedAssetRegDtlEntity.getItemStatus());
        System.out.println("====Tag No ====" + fixedAssetRegDtlEntity.getTagNo());
        try {
            ArrayList<MmsFixedassetRegstDetail> tagList = new ArrayList(query.getResultList());
            System.out.println("Trans Number" + tagList);
            return tagList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedassetRegstDetail> findAllTagNoForOld() {
        Query query = em.createNamedQuery("MmsFixedassetRegstDetail.findTAGNosList");
        query.setParameter("selectOptRadio", 3);
        System.out.println("================tagselectOptRadio==" + 3);
        try {
            ArrayList<MmsFixedassetRegstDetail> TagNOList = new ArrayList(query.getResultList());
            System.out.println("tag no" + TagNOList);
            return TagNOList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFixedassetRegstDetail findByDetailId(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        Query query = em.createNamedQuery("MmsFixedassetRegstDetail.findByFarDetId");
        query.setParameter("farDetId", fixedAssetRegDtlEntity.getFarDetId());
        System.out.println("======fardetId==" + fixedAssetRegDtlEntity.getFarDetId());
        MmsFixedassetRegstDetail result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsFixedassetRegstDetail) query.getResultList().get(0);
                System.out.println("=======================@Facade=====" + result);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedassetRegstDetail> findNewItems() {
        Query query = em.createNativeQuery("SELECT * FROM MMS_FIXEDASSET_REGST_DETAIL WHERE MMS_FIXEDASSET_REGST_DETAIL.FAR_DET_ID NOT IN (SELECT FMS_DPR_OFFICE_ASSET.OF_ASSET_ID FROM FMS_DPR_OFFICE_ASSET)", MmsFixedassetRegstDetail.class);
        ArrayList<MmsFixedassetRegstDetail> newItems = new ArrayList<>(query.getResultList());
        return newItems;
    }

    public List<MmsFixedassetRegstDetail> getTagNo() {
        Query qu = em.createNamedQuery("MmsFixedassetRegstDetail.findAll");

        try {
            List<MmsFixedassetRegstDetail> tagNos = null;
            if (qu.getResultList().size() > 0) {
                tagNos = new ArrayList<>(qu.getResultList());
            }
            return tagNos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MmsFixedassetRegstDetail getTagInfoBtyId(MmsFixedassetRegstDetail mmsFixedassetRegstDetail) {
        System.out.println("faces");
        Query q = em.createNamedQuery("MmsFixedassetRegstDetail.findByFarDetId");
        q.setParameter("farDetId", mmsFixedassetRegstDetail.getFarDetId());
        System.out.println("farDetId====" + mmsFixedassetRegstDetail.getFarDetId());
        try {
            MmsFixedassetRegstDetail findByFarDetId = new MmsFixedassetRegstDetail();
            if (q.getResultList().size() > 0) {
                findByFarDetId = (MmsFixedassetRegstDetail) (q.getResultList().get(0));
            }
            return findByFarDetId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedassetRegstDetail> getFixedAssetInfoByRecivedBy(HrDepartments hrDepartmentsEntity) {
        Query query = em.createNativeQuery("select emp.first_name from mms_fixedasset_regst_detail fard\n"
                + "inner join mms_fixedasset_regstration far\n"
                + "inner join hr_departments hrd\n"
                + "on  far.department=hrd.dep_id\n"
                + "on far.id=fard.far_id\n"
                + "inner join hr_employees emp\n"
                + "on emp.id = fard.requeisted_by\n"
                + "where far.department='" + hrDepartmentsEntity.getDepId() + "'");
        System.out.println("----------@Facade Dtl far id ---------- " + hrDepartmentsEntity.getDepId());
        List<String> name = query.getResultList();
        System.out.println("name    ======" + name);
        try {
            List<MmsFixedassetRegstDetail> result = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                result = (query.getResultList());
                System.out.println("=======================@FacadeDtl=====" + result);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFixedassetRegstDetail getFixedAssetInfoByRecivedBy(MmsFixedassetRegstDetail selectedReturndeBy) {
        MmsFixedassetRegstDetail sivInfo = null;
        Query query = em.createNamedQuery("MmsFixedassetRegstDetail.findBySivNo");
        query.setParameter("requeistedBy", selectedReturndeBy.getRequeistedBy());
        try {

            sivInfo = (MmsFixedassetRegstDetail) query.getSingleResult();
            return sivInfo;
        } catch (Exception ex) {
            System.err.println("===============expetion===============");
            return null;
        }
    }

    public List<MmsFixedassetRegstDetail> getTagNoByRequester(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        System.out.println("facade  " + fixedAssetRegDtlEntity.getRequeistedBy());
        Query q = em.createNativeQuery("select * from mms_fixedasset_regst_detail frd\n"
                + "where mms_fixedasset_regst_detail.requeisted_by='" + fixedAssetRegDtlEntity.getRequeistedBy() + "'");
        try {
            System.out.println("try  where " + fixedAssetRegDtlEntity.getRequeistedBy());
            List<MmsFixedassetRegstDetail> tagsList = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                System.out.println("size exist");
                tagsList = q.getResultList();
            }
            return tagsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MmsFixedassetRegstDetail> getTagNoByRequester1(List<Integer> ids) {
        System.out.println("we here" + ids);
        String listString = ids.toString();
        System.out.println("to string " + listString);
        listString = listString.substring(1, listString.length() - 1);
        System.out.println("No Array Sign " + listString);
        Query q = em.createNativeQuery("select fard.tag_no from mms_fixedasset_regst_detail fard\n"
                + "where fard.requeisted_by='" + listString + "'");
        System.out.println(" ==== Strings========  " + listString);
        try {
            List<MmsFixedassetRegstDetail> tags = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                tags = q.getResultList();
                System.out.println("tag Lists ===== " + tags);
            }
            return tags;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> findAllItemStatuses() {
        Query query = em.createNativeQuery("select DISTINCT ITEM_STATUS from MMS_FIXEDASSET_REGST_DETAIL where ITEM_STATUS is not null");
        return query.getResultList();
    }

    public int ConutBYItemType(String get) {
        System.out.println("");
        Query query = em.createNativeQuery("select * from MMS_FIXEDASSET_REGST_DETAIL where ITEM_STATUS='" + get + "'");
        return query.getResultList().size();
    }
}
