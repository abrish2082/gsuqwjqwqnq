/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsSubCat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Man
 */
@Local
public interface MmsSubcatBeanLocal {

    /**
     *
     * @param mmsSubcat
     */
    void create(MmsSubCat mmsSubcat);

    /**
     *
     * @param subcat
     */
    void edit(MmsSubCat subcat);

    /**
     *
     * @param mmssubcat
     * @return
     */
    boolean isSubcategoryDuplicated(MmsSubCat mmssubcat);

    /**
     *
     * @return
     */
    public List<MmsSubCat> findAll();

    /**
     *
     * @param matname
     * @return
     */
    public String getMateriaCode(String matname);

    /**
     *
     * @param mmsLuSubCategory
     * @return
     */
    public List<MmsSubCat> searchMaterialInfoByName(MmsSubCat mmsLuSubCategory);

    /**
     *
     * @param subCategoryEntity
     * @return
     */
    public MmsSubCat getMmsMaterialSubCatInformation(MmsSubCat subCategoryEntity);

    /**
     *
     * @param matname
     * @return
     */
    public MmsSubCat getMatCategory(String matname);

}
