
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsDisposalItemsDtl;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsDisposalItemsDtlFacade extends AbstractFacade<MmsDisposalItemsDtl> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsDisposalItemsDtlFacade() {
        super(MmsDisposalItemsDtl.class);
    }
    
}
