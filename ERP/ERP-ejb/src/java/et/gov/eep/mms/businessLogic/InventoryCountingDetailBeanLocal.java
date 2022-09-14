
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsInventoryCountDetail;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kimmyo
 */
@Local
public interface InventoryCountingDetailBeanLocal {

    /**
     *
     * @return
     */
    List<MmsInventoryCountDetail> findAll();

    /**
     *
     * @param countInformation
     * @return
     */
    MmsInventoryCountDetail getMmsinvCountInformation(MmsInventoryCountDetail countInformation);

    public List<MmsInventoryCountDetail> getInventoryInfoJoinedWithBinCard(MmsInventoryCounting inventoryCountEntity);
}
