/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSubCat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class MmsSubCatFacade extends AbstractFacade<MmsSubCat> {

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
    public MmsSubCatFacade() {
        super(MmsSubCat.class);
    }

    /**
     *
     * @param subcatinfo
     * @return
     */
    public boolean isSubCategoryDuplicated(MmsSubCat subcatinfo) {
        boolean duplicaton;
        Query query = em.createNamedQuery("MmsSubCat.findBySubCatNameDup", MmsSubCat.class);
        query.setParameter("subCatName", subcatinfo.getSubCatName());
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = true;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     *
     * @param subcatinfo
     * @return
     */
    public String getMaterialCode(String subcatinfo) {
        Query query = em.createNamedQuery("MmsSubCat.findBySubCatCodes", MmsSubCat.class);
        query.setParameter("subCatName", subcatinfo);
        try {

            String subcatName = (String) query.getSingleResult();
            return subcatName;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param mmsLuSubCategory
     * @return
     */
    public List<MmsSubCat> searchMaterialInfoByName(MmsSubCat mmsLuSubCategory) {

        List<MmsSubCat> luMaterialSubCategorys = null;
        try {
            Query query = em.createNamedQuery("MmsSubCat.findByCatagoryName", MmsSubCat.class);
            query.setParameter("Name", mmsLuSubCategory.getSubCatName() + "%");
            luMaterialSubCategorys = (List<MmsSubCat>) query.getResultList();

            return luMaterialSubCategorys;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *
     * @param subCategoryEntity
     * @return
     */
    public MmsSubCat getMmsMaterialSubCatInformation(MmsSubCat subCategoryEntity) {

        Query query = em.createNamedQuery("MmsSubCat.findBySubCatId", MmsSubCat.class);
        query.setParameter("subCatId", subCategoryEntity.getSubCatId());
        try {
            MmsSubCat luMaterialSubCatInfo = (MmsSubCat) query.getResultList().get(0);
            return luMaterialSubCatInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param subcatinfo
     * @return
     */
    public MmsSubCat getCatagoryId(String subcatinfo) {

        Query query = em.createNamedQuery("MmsSubCat.findBySubCatNames", MmsSubCat.class);
        query.setParameter("subCatName", subcatinfo);
        try {

            MmsSubCat subcatName = (MmsSubCat) query.getSingleResult();
            return subcatName;

        } catch (Exception ex) {
            return null;
        }
    }

    //get Sub CategoryList By Category Id
    public List<MmsSubCat> getSubCatListByCategoryId(MmsCategory subCatName) {
        Query query = em.createNativeQuery("SELECT DISTINCT MMS_CATEGORY.CAT_ID,\n"
                + "  MMS_CATEGORY.CAT_NAME,\n"
                + "  MMS_SUB_CAT.SUB_CAT_ID,\n"
                + "  MMS_SUB_CAT.SUB_CAT_NAME\n"
                + "FROM MMS_SUB_CAT\n"
                + "INNER JOIN MMS_CATEGORY\n"
                + "ON MMS_CATEGORY.CAT_ID = MMS_SUB_CAT.CAT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_SUB_CAT.SUB_CAT_ID = MMS_ITEM_REGISTRATION.MAT_SUBCATEGORY\n"
                + "AND MMS_CATEGORY.CAT_ID   = MMS_ITEM_REGISTRATION.MAT_CATEGORY\n"
                + "WHERE MMS_CATEGORY.CAT_ID='" + subCatName.getCatId() + "'", MmsSubCat.class);
        try {
            List<MmsSubCat> mmsSubCatLst = new ArrayList<>(query.getResultList());
            return mmsSubCatLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //get Sub CategoryList By Category Id
    public List<MmsSubCat> getSubCatListById(Integer catId) {
        Query query = em.createNativeQuery("SELECT DISTINCT MMS_SUB_CAT.SUB_CAT_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  MMS_CATEGORY.CAT_ID,\n"
                + "  MMS_SUB_CAT.SUB_CAT_NAME\n"
                + "FROM MMS_SUB_CAT\n"
                + "INNER JOIN MMS_CATEGORY\n"
                + "ON MMS_CATEGORY.CAT_ID = MMS_SUB_CAT.CAT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_SUB_CAT.SUB_CAT_ID = MMS_ITEM_REGISTRATION.MAT_SUBCATEGORY\n"
                + "AND MMS_CATEGORY.CAT_ID   = MMS_ITEM_REGISTRATION.MAT_CATEGORY\n"
                + "WHERE MMS_CATEGORY.CAT_ID='" + catId + "'", MmsSubCat.class);
        try {
            List<MmsSubCat> mmsSubCats = new ArrayList<>(query.getResultList());
            return mmsSubCats;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    //get Sub Category objects By ItemId
    public MmsSubCat getSubCategoryByItemId(MmsItemRegistration mmsItemRegistration) {
        Query query = em.createNativeQuery("SELECT MMS_SUB_CAT.SUB_CAT_ID,\n"
                + "  MMS_SUB_CAT.SUB_CAT_NAME\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID\n"
                + "INNER JOIN MMS_SUB_CAT\n"
                + "ON MMS_SUB_CAT.SUB_CAT_ID = MMS_ITEM_REGISTRATION.MAT_SUBCATEGORY\n"
                + "WHERE MMS_ITEM_REGISTRATION.MATERIAL_ID = '" + mmsItemRegistration.getMaterialId() + "'", MmsSubCat.class);
        try {
            MmsSubCat mmsSubCat = (MmsSubCat) (query.getResultList().get(0));
            return mmsSubCat;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
