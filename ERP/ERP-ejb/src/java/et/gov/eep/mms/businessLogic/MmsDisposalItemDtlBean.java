
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsDisposalItemsDtl;
import et.gov.eep.mms.mapper.MmsDisposalItemsDtlFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsDisposalItemDtlBean implements MmsDisposalItemDtlBeanLocal {

   @EJB
   MmsDisposalItemsDtlFacade dispFacade;
   
   @Override
    public List<MmsDisposalItemsDtl> findAllTagNo() {
       return dispFacade.findAll();
    }

    
}
