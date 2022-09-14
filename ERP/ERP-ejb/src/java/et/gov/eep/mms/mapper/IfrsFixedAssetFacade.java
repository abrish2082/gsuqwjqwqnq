
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.mms.entity.IfrsFixedAsset;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class IfrsFixedAssetFacade extends AbstractFacade<IfrsFixedAsset> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IfrsFixedAssetFacade() {
        super(IfrsFixedAsset.class);
    }

    public IfrsFixedAsset getLastFixAId() {
        Query query = em.createQuery("SELECT p FROM IfrsFixedAsset p WHERE p.id = (SELECT MAX(m.id) FROM IfrsFixedAsset m)");
        try {
            IfrsFixedAsset lastNo = (IfrsFixedAsset) query.getResultList().get(0);
            return lastNo;
        } catch (Exception ex) {

            return null;
        }
    }

    public List<IfrsFixedAsset> findAllRevaluedFA() {
        List<IfrsFixedAsset> glCodeList;
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT ifa.ID,ifa.* FROM IFRS_FIXED_ASSET ifa "
                    + "INNER JOIN "
                    + "IFRS_FA_REVALUATION_HISTORY ifaRH "
                    + "ON ifa.ID=ifaRH.IFRS_FA_ID ", IfrsFixedAsset.class);
            glCodeList = (List<IfrsFixedAsset>) query.getResultList();
            return glCodeList;
        } catch (Exception e) {
            return null;
        }
    }

    public List<IfrsFixedAsset> searchFixedAssetByNo(IfrsFixedAsset fixedasset) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public IfrsFixedAsset getSelectedRequest(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public List<IfrsFixedAsset> findByDepreciationType(IfrsDepreciationSetup depreciationSetup) {

        try {
            Query query = em.createNamedQuery("IfrsFixedAsset.findByDepreciationType");
            query.setParameter("depreciationSetup", depreciationSetup);
            ArrayList<IfrsFixedAsset> fixedList = new ArrayList(query.getResultList());
            return fixedList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public IfrsFixedAsset findByFaCode(IfrsFixedAsset fixedassetIfrs) {
        Query query = em.createNamedQuery("IfrsFixedAsset.findByfaCode");
        query.setParameter("faCode", fixedassetIfrs.getFaCode());
        IfrsFixedAsset result;
        try {
            result = (IfrsFixedAsset) query.getResultList().get(0);
            System.out.println("=======================@Facade=====" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
