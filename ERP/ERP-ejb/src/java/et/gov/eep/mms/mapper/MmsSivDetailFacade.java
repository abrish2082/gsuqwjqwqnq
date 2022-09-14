/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class MmsSivDetailFacade extends AbstractFacade<MmsSivDetail> {

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
    public MmsSivDetailFacade() {
        super(MmsSivDetail.class);
    }

    /**
     *
     * @param selectedSivNo
     * @return
     */
    public ArrayList<MmsSivDetail> getSIVinfoBySivNo(String selectedSivNo) {
        Query query = em.createNamedQuery("MmsSivDetail.findBySivNo");
        query.setParameter("sivNo", selectedSivNo);
        try {
            if (query.getResultList().size() > 0) {
                ArrayList<MmsSivDetail> sivInfo = new ArrayList(query.getResultList());

                return sivInfo;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     *
     * @param mmsSivDtlEntity
     * @return
     */
    public MmsSivDetail getSivDetailInfosByMatcode(MmsSivDetail mmsSivDtlEntity) {
        Query query = em.createNamedQuery("MmsSivDetail.findByItemCode", MmsSivDetail.class);
        //search using item id
//        query.setParameter("itemCode", mmsSivDtlEntity.getItemCode());
        try {
            MmsSivDetail mmsSivdetails = (MmsSivDetail) query.getResultList().get(0);

            return mmsSivdetails;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param sivid
     * @return
     */
    public MmsSivDetail getDetailbySivId(MmsSiv sivid) {
        MmsSivDetail result = null;
        Query query = em.createNamedQuery("MmsSivDetail.findbysivIds", MmsSivDetail.class);
        query.setParameter("sivId", sivid.getSivId());
        try {
            MmsSivDetail importationInfo = (MmsSivDetail) query.getResultList().get(0);
            return importationInfo;

        } catch (Exception ex) {
            return null;
        }
    }

    public MmsSivDetail getLastSivDtlId() {
        Query query = em.createNamedQuery("MmsSivDetail.findBySivDtlIdMaximum");
        MmsSivDetail result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsSivDetail) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsSivDetail findByDetailId(MmsSivDetail sivDtlEntity) {
        MmsSivDetail result = null;
        Query query = em.createNamedQuery("MmsSivDetail.findBySivDetId", MmsSivDetail.class);
        query.setParameter("sivDetId", sivDtlEntity.getSivDetId());
        try {
            MmsSivDetail importationInfo = (MmsSivDetail) query.getResultList().get(0);
            return importationInfo;

        } catch (Exception ex) {
            return null;
        }
    }
   public MmsSivDetail findItemId() {
        Query query = em.createNamedQuery("MmsSivDetail.findAll", MmsSivDetail.class);
        try {
            MmsSivDetail importationInfo = (MmsSivDetail) query.getResultList().get(0);
            return importationInfo;

        } catch (Exception ex) {
            return null;
        }
    }
    public MmsSivDetail getItemInfoByItemId(MmsItemRegistration itemRegEntity) {
        System.out.println("i here");
        Query q =em.createNamedQuery("MmsSivDetail.findByItemId");
        q.setParameter("itemId", itemRegEntity.getMaterialId());
        System.out.println("selected id====="+itemRegEntity.getMaterialId());
        try {
            MmsSivDetail itemInfo=new MmsSivDetail();
            if(q.getResultList().size()>0){
                itemInfo=(MmsSivDetail)(q.getResultList().get(0));
                System.out.println("item name=="+itemInfo.getItemId().getMatName());
                System.out.println("item name=="+itemInfo.getItemId().getUnitPrice());
                System.out.println("item name=="+itemInfo.getItemId().getUnitMeasure1());
                System.out.println("quantity from sv detail==="+itemInfo.getQuantity());
            }
            return itemInfo;
        } catch (Exception e) {
            return null;
        }
    }

}
