/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsSubCat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface MmsCategoryBeanLocal {

    /**
     *
     * @param mmsCategory
     */
    void create(MmsCategory mmsCategory);

    /**
     *
     * @param mmscat
     */
    void edit(MmsCategory mmscat);

    /**
     *
     * @return
     */
    public List<MmsCategory> findAll();

    /**
     *
     * @param categories
     * @return
     */
    public MmsCategory findCategory(MmsCategory categories);

    /**
     *
     * @param categoryinformation
     * @return
     */
    boolean isCategoryduplicated(MmsCategory categoryinformation);

    /**
     *
     * @param mmscat
     * @return
     */
    public ArrayList<MmsSubCat> getSubcategoryList(MmsCategory mmscat);

    /**
     *
     * @param category
     * @return
     */
    public ArrayList<MmsCategory> searchByCategoryName(MmsCategory category);

    /**
     *
     * @param string
     * @return
     */
    public String getMaterialCode(String string);

    public MmsCategory searchByCategoryId(MmsCategory category);

    public MmsCategory getCatByCategoryCode(MmsCategory category);


}
