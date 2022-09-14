
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsStockDisposalDtl;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsStockDisposalDtlFacade extends AbstractFacade<MmsStockDisposalDtl> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsStockDisposalDtlFacade() {
        super(MmsStockDisposalDtl.class);
    }

    public MmsStockDisposalDtl findbyitemcode(MmsStockDisposalDtl stockDispDetEntity) {
         Query query = em.createNamedQuery("MmsStockDisposalDtl.findByItemCode");
        query.setParameter("itemCode", stockDispDetEntity.getItemCode());

        MmsStockDisposalDtl result = new MmsStockDisposalDtl();
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsStockDisposalDtl) query.getResultList().get(0);
                System.out.println("=======================@Facade itemCode=====" + result);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
