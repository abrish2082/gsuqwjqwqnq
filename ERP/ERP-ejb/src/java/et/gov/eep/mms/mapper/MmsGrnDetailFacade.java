/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
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
public class MmsGrnDetailFacade extends AbstractFacade<MmsGrnDetail> {

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
    public MmsGrnDetailFacade() {
        super(MmsGrnDetail.class);
    }

    /**
     *
     * @param grnid
     * @return
     */
    public MmsGrnDetail getDetailbyId(MmsGrn grnid) {
        MmsGrnDetail result = null;
        Query query = em.createNamedQuery("MmsGrnDetail.findByUnitPrice", MmsGrnDetail.class);
        query.setParameter("grnId", grnid.getGrnId());
        try {
            MmsGrnDetail importationInfo = (MmsGrnDetail) query.getResultList().get(0);
            return importationInfo;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param mmsGrnDetail
     * @return
     */
    public MmsGrnDetail getGrnDetailInfosByMatcode(MmsGrnDetail mmsGrnDetail) {
        Query query = em.createNamedQuery("MmsGrnDetail.findByMaterialCode", MmsGrnDetail.class);
        //query.setParameter("materialCode", mmsGrnDetail.getMaterialCode());
        try {
            MmsGrnDetail mmsGrndetails = (MmsGrnDetail) query.getResultList().get(0);
            return mmsGrndetails;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsGrnDetail getGrnDetailInfosByDtlId(MmsGrnDetail mmsGrnDetail) {
        Query query = em.createNamedQuery("MmsGrnDetail.findByGrnDetailId", MmsGrnDetail.class);
        query.setParameter("grnDetailId", mmsGrnDetail.getGrnDetailId());
        try {
            MmsGrnDetail mmsGrndetails = (MmsGrnDetail) query.getResultList().get(0);
            return mmsGrndetails;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsGrnDetail getLastGrnDtlId() {
        MmsGrnDetail result = null;
        Query query = em.createNamedQuery("MmsGrnDetail.findByGRNDtlIdMaximum");

        try {
            if (query.getResultList().size() > 0) {
                result = (MmsGrnDetail) query.getResultList().get(0);
                System.out.println("=========result @ facade======" + result);
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsGrnDetail> searchMmsGrnDetailByItemId(int id) {

        try {
            Query query1 = em.createNativeQuery("SELECT *  "
                    + "FROM Mms_GRN_DETAIL gd          "
                    + "INNER JOIN MMS_GRN grn "
                    + "ON gd.GRN_ID =grn.GRN_ID "
                    + "WHERE gd.ITEM_ID ='" + id + "' "
                    + "AND gd.REMAINING_QUANTITY > 0 "
                    + "ORDER BY grn.GRN_NO  ASC ",
                    MmsGrnDetail.class);
            return (List<MmsGrnDetail>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsGrnDetail> listOfGrnDetailsByItemId(int itemId, MmsGrn mmsGrn) {
        System.out.println("when fac itemId " + itemId + " and GRN " + mmsGrn);
        Query query = em.createNativeQuery("SELECT * "
                + "FROM Mms_GRN_DETAIL gd  "
                + "INNER JOIN MMS_GRN grn "
                + "ON gd.GRN_ID =grn.GRN_ID "
                + "WHERE gd.ITEM_ID ='" + itemId + "' "
                + "AND gd.RECEIVED_QUANTITY > 0 "
                + "AND gd.GRN_ID ='" + mmsGrn.getGrnId() + "' "
                + "ORDER BY grn.GRN_NO  ASC ", MmsGrnDetail.class);
        try {
            List<MmsGrnDetail> mmsGrnDetailsLists = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                mmsGrnDetailsLists = query.getResultList();
            }
            return mmsGrnDetailsLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
