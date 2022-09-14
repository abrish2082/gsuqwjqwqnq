/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsSubCat;
import et.gov.eep.mms.mapper.MmsCategoryFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class MmsCategoryBean implements MmsCategoryBeanLocal {

    @EJB
    MmsCategoryFacade catFacade;

    /**
     *
     * @param mmsCategory
     */
    @Override
    public void create(MmsCategory mmsCategory) {
        catFacade.create(mmsCategory);
    }

    /**
     *
     * @param mmscxat
     */
    @Override
    public void edit(MmsCategory mmscxat) {
        catFacade.edit(mmscxat);
    }

    /**
     *
     * @return
     */
    @Override
    public List<MmsCategory> findAll() {
        return catFacade.findAll();
    }

    /**
     *
     * @param categories
     * @return
     */
    @Override
    public MmsCategory findCategory(MmsCategory categories) {
        return catFacade.findCategory(categories);
    }

    /**
     *
     * @param categoryinformation
     * @return
     */
    @Override
    public boolean isCategoryduplicated(MmsCategory categoryinformation) {
        return catFacade.isCategoryDuplicated(categoryinformation);
    }

    /**
     *
     * @param mmscat
     * @return
     */
    @Override
    public ArrayList<MmsSubCat> getSubcategoryList(MmsCategory mmscat) {
        return catFacade.getSubcatlist(mmscat);
    }

    /**
     *
     * @param category
     * @return
     */
    @Override
    public ArrayList<MmsCategory> searchByCategoryName(MmsCategory category) {
        return catFacade.searchByCategoryName(category);
    }

    /**
     *
     * @param string
     * @return
     */
    @Override
    public String getMaterialCode(String string) {
        return catFacade.findMaterialCode(string);
    }

    @Override
    public MmsCategory searchByCategoryId(MmsCategory category) {
       return catFacade.searchByCategoryId(category);
    }

    @Override
    public MmsCategory getCatByCategoryCode(MmsCategory category) {
       return catFacade.searchByCatCode(category);
    }
}
