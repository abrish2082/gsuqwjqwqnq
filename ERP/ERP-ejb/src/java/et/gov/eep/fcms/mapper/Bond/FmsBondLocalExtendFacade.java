package et.gov.eep.fcms.mapper.Bond;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalExtend;

/**
 *
 * @author mora
 */
@Stateless
public class FmsBondLocalExtendFacade extends AbstractFacade<FmsBondLocalExtend> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondLocalExtendFacade() {
        super(FmsBondLocalExtend.class);
    }

}
