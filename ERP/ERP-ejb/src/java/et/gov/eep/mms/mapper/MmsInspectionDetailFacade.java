/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsInspection;

import et.gov.eep.mms.entity.MmsInspectionDetail;
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
public class MmsInspectionDetailFacade extends AbstractFacade<MmsInspectionDetail> {

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
    public MmsInspectionDetailFacade() {
        super(MmsInspectionDetail.class);
    }

    /**
     *
     * @param selectedInspNo
     * @return
     */
    public MmsInspectionDetail getByInspectionNumber(String selectedInspNo) {
        MmsInspectionDetail result = null;
        Query query = em.createNamedQuery("MmsInspectionDetail.findByInspectyionNo");
        query.setParameter("inspectionNo", selectedInspNo);
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsInspectionDetail) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param insp
     * @return
     */
    public List<MmsInspectionDetail> searchByinspectionId(MmsInspection insp) {
        Query query = em.createNamedQuery("MmsInspectionDetail.frindByInspId", MmsInspectionDetail.class);
        query.setParameter("inspectionId", insp);

        try {
            ArrayList<MmsInspectionDetail> inspectionList = new ArrayList(query.getResultList());
            return inspectionList;
        } catch (Exception ex) {
            return null;
        }

    }
}
