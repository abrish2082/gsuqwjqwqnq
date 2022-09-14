package et.gov.eep.fcms.businessLogic.Bond;

import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalExtend;

/**
 *
 * @author mora
 */
@Local
public interface BondLocalExtendBeanLocal {

    public void Create(FmsBondLocalExtend fmsBondLocalExtend);

}
