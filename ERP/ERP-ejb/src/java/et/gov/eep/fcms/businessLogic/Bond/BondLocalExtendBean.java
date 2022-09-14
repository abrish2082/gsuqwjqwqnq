package et.gov.eep.fcms.businessLogic.Bond;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalExtend;
import et.gov.eep.fcms.mapper.Bond.FmsBondLocalExtendFacade;

/**
 *
 * @author mora
 */
@Stateless
public class BondLocalExtendBean implements BondLocalExtendBeanLocal {

    @EJB
    FmsBondLocalExtendFacade BondLocalExtendFacade;

    @Override
    public void Create(FmsBondLocalExtend fmsBondLocalExtend) {
        BondLocalExtendFacade.create(fmsBondLocalExtend);
    }

}
