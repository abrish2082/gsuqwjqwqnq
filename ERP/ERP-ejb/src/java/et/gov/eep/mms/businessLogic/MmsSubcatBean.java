/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsSubCat;
import et.gov.eep.mms.mapper.MmsSubCatFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class MmsSubcatBean implements MmsSubcatBeanLocal {

    @EJB
    MmsSubCatFacade subcatFacade;

    /**
     *
     * @param mmsSubcat
     */
    @Override
    public void create(MmsSubCat mmsSubcat) {
        subcatFacade.create(mmsSubcat);
    }

    /**
     *
     * @param mmssubcat
     * @return
     */
    @Override
    public boolean isSubcategoryDuplicated(MmsSubCat mmssubcat) {

        return subcatFacade.isSubCategoryDuplicated(mmssubcat);
    }

    /**
     *
     * @return
     */
    @Override
    public List<MmsSubCat> findAll() {
        return subcatFacade.findAll();
    }

    /**
     *
     * @param subcat
     */
    @Override
    public void edit(MmsSubCat subcat) {
        subcatFacade.edit(subcat);
    }

    /**
     *
     * @param a
     * @return
     */
    @Override
    public String getMateriaCode(String a) {
        return subcatFacade.getMaterialCode(a);
    }

    /**
     *
     * @param mmsLuSubCategory
     * @return
     */
    @Override
    public List<MmsSubCat> searchMaterialInfoByName(MmsSubCat mmsLuSubCategory) {
        return subcatFacade.searchMaterialInfoByName(mmsLuSubCategory);
    }

    /**
     *
     * @param subCategoryEntity
     * @return
     */
    @Override
    public MmsSubCat getMmsMaterialSubCatInformation(MmsSubCat subCategoryEntity) {
        return subcatFacade.getMmsMaterialSubCatInformation(subCategoryEntity);
    }

    /**
     *
     * @param matname
     * @return
     */
    @Override
    public MmsSubCat getMatCategory(String matname) {
        return subcatFacade.getCatagoryId(matname);
    }
}
