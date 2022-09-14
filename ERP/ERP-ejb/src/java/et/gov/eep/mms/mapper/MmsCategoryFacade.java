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
public class MmsCategoryFacade extends AbstractFacade<MmsCategory> {

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
    public MmsCategoryFacade() {
        super(MmsCategory.class);
    }

    /**
     *
     * @param categories
     * @return
     */
    public MmsCategory findCategory(MmsCategory categories) {
        Query query = em.createNamedQuery("MmsCategory.findByCatName");
        query.setParameter("catName", categories.getCatName());
        try {
            MmsCategory catname = (MmsCategory) query.getResultList().get(0);
            return catname;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param strings
     * @return
     */
    public String findMaterialCode(String strings) {

        Query query = em.createNamedQuery("MmsCategory.findByCatCodes", MmsCategory.class);
        query.setParameter("catName", strings);
        try {
            String catname = (String) query.getSingleResult();

            return catname;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param categoryInformation
     * @return
     */
    public boolean isCategoryDuplicated(MmsCategory categoryInformation) {
        boolean duplicaton;
        Query query = em.createNamedQuery("MmsCategory.findByCatName", MmsCategory.class);
        query.setParameter("catName", categoryInformation.getCatName());

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
     * @param categoryInformation
     * @return
     */
    public ArrayList<MmsSubCat> getSubcatlist(MmsCategory categoryInformation) {
        Query query = em.createNamedQuery("MmsSubCat.findBySubCatName", MmsSubCat.class);
        query.setParameter("subCatName", categoryInformation.getCatName());
        try {
            ArrayList<MmsSubCat> listofSiv = new ArrayList(query.getResultList());
            return listofSiv;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     *
     * @param categoryInformation
     * @return
     */
    public String getMaterialCode(MmsCategory categoryInformation) {
        Query query = em.createNamedQuery("MmsSubCat.findBySubCatName", MmsSubCat.class);
        query.setParameter("catCode", categoryInformation.getCatCode()
        );
        try {
            String listofSiv = query.getSingleResult().toString();
            return listofSiv;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     *
     * @param item
     * @return
     */
    public ArrayList<MmsCategory> searchByCategoryName(MmsCategory item) {
        Query query = em.createNamedQuery("MmsCategory.findByCatNameLike");
        query.setParameter("catName", item.getCatName() + '%');
        try {
            ArrayList<MmsCategory> ItemList = new ArrayList(query.getResultList());
            return ItemList;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param item
     * @return
     */
    public ArrayList<MmsCategory> searchByCategoryCode(MmsCategory item) {
        Query query = em.createNamedQuery("MmsCategory.findByCatCode");
        query.setParameter("catCode", item.getCatCode());

        try {

            ArrayList<MmsCategory> ItemList = new ArrayList(query.getResultList());
            return ItemList;
        } catch (Exception ex) {
            return null;
        }
    }
//Sadik

    public MmsCategory searchByCategoryId(MmsCategory category) {
        Query query = em.createNamedQuery("MmsCategory.findByCatId");
        query.setParameter("catId", category.getCatId());

        try {

            MmsCategory catagorie = (MmsCategory) query.getResultList().get(0);
            return catagorie;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsCategory searchByCatCode(MmsCategory category) {
        Query query = em.createNamedQuery("MmsCategory.findByCatCode");
        query.setParameter("cCode", category.getCatCode());

        try {

            MmsCategory catagorie = (MmsCategory) query.getResultList().get(0);
            return catagorie;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsCategory> getCatNameLists() {
        List<MmsCategory> mmsCategorys = new ArrayList<>();
        Query query = em.createNativeQuery("SELECT distinct MMS_CATEGORY.CAT_ID,\n"
                + "  MMS_CATEGORY.CAT_NAME\n"
                + "FROM MMS_SUB_CAT\n"
                + "INNER JOIN MMS_CATEGORY\n"
                + "ON MMS_CATEGORY.CAT_ID = MMS_SUB_CAT.CAT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_SUB_CAT.SUB_CAT_ID = MMS_ITEM_REGISTRATION.MAT_SUBCATEGORY\n"
                + "AND MMS_CATEGORY.CAT_ID   = MMS_ITEM_REGISTRATION.MAT_CATEGORY", MmsCategory.class);
        try {
            mmsCategorys = new ArrayList<>(query.getResultList());
            return mmsCategorys;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

//get a Category object by ItemId
    public MmsCategory getCategoryByItemId(MmsItemRegistration mmsItemRegistration) {
        Query query = em.createNativeQuery("SELECT MMS_CATEGORY.CAT_ID,\n"
                + "  MMS_CATEGORY.CAT_NAME\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID\n"
                + "INNER JOIN MMS_CATEGORY\n"
                + "ON MMS_CATEGORY.CAT_ID = MMS_ITEM_REGISTRATION.MAT_CATEGORY\n"
                + "WHERE MMS_ITEM_REGISTRATION.MATERIAL_ID = '" + mmsItemRegistration.getMaterialId() + "'", MmsCategory.class);
        try {
            MmsCategory mmsCategory = (MmsCategory) (query.getResultList().get(0));
            return mmsCategory;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
