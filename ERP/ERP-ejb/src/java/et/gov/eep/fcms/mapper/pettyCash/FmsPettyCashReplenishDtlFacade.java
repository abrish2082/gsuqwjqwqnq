package et.gov.eep.fcms.mapper.pettyCash;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishDtl;

/**
 *
 * @author semira
 */
@Stateless
public class FmsPettyCashReplenishDtlFacade extends AbstractFacade<FmsPettyCashReplenishDtl> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsPettyCashReplenishDtlFacade() {
        super(FmsPettyCashReplenishDtl.class);
    }

}
