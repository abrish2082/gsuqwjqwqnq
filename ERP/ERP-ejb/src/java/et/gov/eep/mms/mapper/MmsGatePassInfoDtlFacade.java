/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsGatePassInfoDtl;
import et.gov.eep.mms.entity.MmsGatePassInformation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsGatePassInfoDtlFacade extends AbstractFacade<MmsGatePassInfoDtl> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public MmsGatePassInfoDtlFacade() {
        super(MmsGatePassInfoDtl.class);
    }

    /**
     *
     * @param gatePassEntity
     * @return
     */
    public ArrayList<MmsGatePassInfoDtl> searchGatePassByParameterPrefix(MmsGatePassInformation gatePassEntity) {
        Query query = em.createNamedQuery("MmsGatePassInfoDtl.findByAllParameters");
        query.setParameter("gatePassNo", gatePassEntity.getGatePassNo() + '%');
        try {
            ArrayList<MmsGatePassInfoDtl> gatePassList = new ArrayList(query.getResultList());
            return gatePassList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsGatePassInfoDtl> searchGatePassByParameterPrefixAndProcessedBy(MmsGatePassInformation gatePassEntity) {
        Query query = em.createNamedQuery("MmsGatePassInfoDtl.findByAllParameters");
        query.setParameter("gatePassNo", gatePassEntity.getGatePassNo() + '%');
        try {
            ArrayList<MmsGatePassInfoDtl> gatePassList = new ArrayList(query.getResultList());
            return gatePassList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param gatePassEntity
     * @return
     */
    public ArrayList<MmsGatePassInfoDtl> searchGatePassByParameterContains(MmsGatePassInformation gatePassEntity) {
        Query query = em.createNamedQuery("MmsGatePassInfoDtl.findByAllParameters");
        query.setParameter("gatePassNo", '%' + gatePassEntity.getGatePassNo() + '%');
        try {
            ArrayList<MmsGatePassInfoDtl> gatePassList = new ArrayList(query.getResultList());
            return gatePassList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
