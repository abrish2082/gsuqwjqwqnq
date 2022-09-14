
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsInsurance;
import et.gov.eep.mms.entity.MmsInsuranceDetail;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kukusha
 */
@Stateless
public class MmsInsuranceDetailFacade extends AbstractFacade<MmsInsuranceDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    public MmsInsuranceDetailFacade(Class<MmsInsuranceDetail> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsInsuranceDetailFacade() {
        super(MmsInsuranceDetail.class);
    }

    /**
     *
     * @param insId
     * @return
     */
    public List<MmsInsuranceDetail> searchByinsuranceId(MmsInsurance insId) {
        Query query = em.createNamedQuery("MmsInsuranceDetail.frindByInsId", MmsInsuranceDetail.class);
        query.setParameter("insId", insId.getInsId());

        try {
            ArrayList<MmsInsuranceDetail> inspectionList = new ArrayList(query.getResultList());
            return inspectionList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
