
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFixedAssetReturnDtl;
import et.gov.eep.mms.mapper.MmsFixedAssetReturnDtlFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */



@Stateless
public class MmsFixedAssetReturnDtlBean implements MmsFixedAssetReturnDtlBeanLocal {

    @EJB MmsFixedAssetReturnDtlFacade faRetFacade;
    @Override
    public List<MmsFixedAssetReturnDtl> findAllTagNo() {
      return faRetFacade.findAll();
    }

    @Override
    public List<MmsFixedAssetReturnDtl> searchBytag1(MmsFixedAssetReturnDtl returnDtlEntity) {
         return faRetFacade.searchBytag1(returnDtlEntity);
    }

    @Override
    public List<MmsFixedAssetReturnDtl> findAllInfo() {
        return faRetFacade.findAll();
    }

   
}
