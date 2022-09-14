
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsDisposalItemsDtl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsDisposalItemDtlBeanLocal {

    public List<MmsDisposalItemsDtl> findAllTagNo();
    
}
