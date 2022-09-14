
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsLostFixedAssetDetail;
import et.gov.eep.mms.mapper.MmsLostFixedAssetDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class LostFxAssetDtlBean implements LostFxAssetDtlBeanLocal {

    @EJB
    MmsLostFixedAssetDetailFacade lostDtlFacade;

    @Override
    public List<MmsLostFixedAssetDetail> findAllInfo() {
        return lostDtlFacade.findAll();
    }

 
}
