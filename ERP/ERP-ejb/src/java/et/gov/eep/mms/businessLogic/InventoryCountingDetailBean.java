
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsInventoryCountDetail;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import et.gov.eep.mms.mapper.MmsInventoryCountDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author kimmyo
 */
@Stateless
public class InventoryCountingDetailBean implements InventoryCountingDetailBeanLocal {
    @EJB
    InventoryCountingDetailBeanLocal invCountDetailInterface;
     @EJB
    MmsInventoryCountDetailFacade invCountDetailFacade;

    /**
     *
     * @return
     */
    @Override
    public List<MmsInventoryCountDetail> findAll() {
        return invCountDetailInterface.findAll();
    }


    /**
     *
     * @param countInformation
     * @return
     */
    
    @Override
    public MmsInventoryCountDetail getMmsinvCountInformation(MmsInventoryCountDetail countInformation) {
        return invCountDetailFacade.getMmsInventoryInformation(countInformation);
    }

    @Override
    public List<MmsInventoryCountDetail> getInventoryInfoJoinedWithBinCard(MmsInventoryCounting inventoryCountEntity) {
       return invCountDetailFacade.getInventoryInfoJoinedWithBinCard(inventoryCountEntity);
    }
}
