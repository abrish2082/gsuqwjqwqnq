
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuTitles;
import et.gov.eep.hrms.mapper.lookup.HrLuTitlesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrLuTitlesBean implements HrLuTitlesBeanLocal {

   @EJB
   HrLuTitlesFacade hrLuTitlesFacede;
   
    /**
     *
     * @return
     */
    @Override
    public List<HrLuTitles> findAll() {
        return hrLuTitlesFacede.findAll();
    }
}
