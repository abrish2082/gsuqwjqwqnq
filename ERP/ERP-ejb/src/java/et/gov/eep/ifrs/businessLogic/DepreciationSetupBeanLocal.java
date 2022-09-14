/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.businessLogic;

import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface DepreciationSetupBeanLocal {

    public void saveOrUpdate(IfrsDepreciationSetup depreciationSetup);
    /**
     *
     * @param depreciationSetup
     */
    void create(IfrsDepreciationSetup depreciationSetup); 
    /**
     *
     * @param depreciationSetup
     */

    /**
     *
     * @return
     */
    public List<IfrsDepreciationSetup> depreciationList();

    public List<IfrsDepreciationSetup> findAllFixedAssetGroup();

    public IfrsDepreciationSetup getListOfAttributesByCategory(int key);

    public IfrsDepreciationSetup getFixedassetgroup(Integer fixedassetgroupId);

    public List<IfrsDepreciationSetup> depreciationListForStock();

    public IfrsDepreciationSetup findById(Integer id);
    
}
