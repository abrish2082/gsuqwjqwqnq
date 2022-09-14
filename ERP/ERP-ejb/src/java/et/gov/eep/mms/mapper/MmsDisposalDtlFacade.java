
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsDisposalDtl;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Me
 */
@Stateless
public class MmsDisposalDtlFacade extends AbstractFacade<MmsDisposalDtl> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsDisposalDtlFacade() {
        super(MmsDisposalDtl.class);
    }

    public List<MmsDisposalDtl> fetchNewDisposedItems() {
        Query query = em.createNativeQuery("select * from MMS_DISPOSAL_DTL mdd where \n"
                + "mdd.DISP_DTL_ID not in (select fdi.MMS_DISPOSED_FK from FMS_DISPOSED_ITEMS fdi)", MmsDisposalDtl.class);
        try {
            List<MmsDisposalDtl> newlydisposedItems = new ArrayList(query.getResultList());
            return newlydisposedItems;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public MmsDisposalDtl getDisposedItem(MmsDisposalDtl mmsDisposalDtl) {
        Query query = em.createNamedQuery("MmsDisposalDtl.findByTagNo");
        query.setParameter("tagNo", mmsDisposalDtl.getTagNo());
        try {
            MmsDisposalDtl disposedItems = (MmsDisposalDtl) (query.getResultList().get(0));
            return disposedItems;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
