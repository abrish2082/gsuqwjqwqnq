/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.admin;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;

/**
 *
 * @author userPCAdmin
 */
@Stateless
public class FmsVouchersNoRangeFacade extends AbstractFacade<FmsVouchersNoRange> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public FmsVouchersNoRangeFacade() {
        super(FmsVouchersNoRange.class);
    }

    /**
     *
     * @param vouchersNoRangeSearch
     * @return vouchersNoRangesList
     */
    public List<FmsVouchersNoRange> searchByVoucherType(FmsVouchersNoRange vouchersNoRangeSearch) {
        try {
            List< FmsVouchersNoRange> vouchersNoRangesList = null;
            if (vouchersNoRangeSearch.getTypeId().getId() == -1) {
                Query query = em.createNamedQuery("FmsVouchersNoRange.findByActiveStatus", FmsVouchersNoRange.class);
                vouchersNoRangesList = (List< FmsVouchersNoRange>) query.getResultList();
            } else {
                Query query = em.createNamedQuery("FmsVouchersNoRange.findByVoucherType", FmsVouchersNoRange.class);
                query.setParameter("typeId", vouchersNoRangeSearch.getTypeId().getId());
                vouchersNoRangesList = (List< FmsVouchersNoRange>) query.getResultList();
            }

            return vouchersNoRangesList;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * update status
     *
     * @param vouchersNoRange
     */
    public void updateStatus(FmsVouchersNoRange vouchersNoRange) {
        Query query = em.createNamedQuery("FmsVouchersNoRange.findByStatusUPDATE", FmsVouchersNoRange.class);
        query.setParameter("typeId", vouchersNoRange.getTypeId().getId());
        query.executeUpdate();
    }

    /**
     *
     * @param vouchersNoRange
     * @return fmsVouchersNoRange
     */
    public FmsVouchersNoRange getCurrentVoucherNumber(FmsVouchersNoRange vouchersNoRange) {
        {
            Query query = em.createNamedQuery("FmsVouchersNoRange.findByStatus", FmsVouchersNoRange.class);
            query.setParameter("status", vouchersNoRange.getStatus());
            query.setParameter("typeId", vouchersNoRange.getTypeId().getId());
            try {
                FmsVouchersNoRange fmsVouchersNoRange = (FmsVouchersNoRange) query.getResultList().get(0);
                return fmsVouchersNoRange;
            } catch (Exception ex) {
                return null;
            }

        }
    }

    /**
     *
     * @param vouchersNoRange
     * @return fmsVouchersNoRange by id
     */
    public FmsVouchersNoRange getCurrentVoucherById(FmsVouchersNoRange vouchersNoRange) {
        {
            Query query = em.createNamedQuery("FmsVouchersNoRange.findById", FmsVouchersNoRange.class);
            query.setParameter("id", vouchersNoRange.getId());
            try {
                FmsVouchersNoRange fmsVouchersNoRange = (FmsVouchersNoRange) query.getResultList().get(0);
                return fmsVouchersNoRange;
            } catch (Exception ex) {
                return null;
            }

        }
    }

}
