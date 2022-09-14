/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.math.BigDecimal;
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
public class PrmsContractDetailFacade extends AbstractFacade<PrmsContractDetail> {

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
    public PrmsContractDetailFacade() {
        super(PrmsContractDetail.class);
    }

    public PrmsContractDetail findInfoByMaterialId(MmsItemRegistration itemRegistrationEntity) {

        Query query = em.createNamedQuery("PrmsContractDetail.findByMaterialId", PrmsContractDetail.class);
        query.setParameter("itemId", itemRegistrationEntity);

        try {
            PrmsContractDetail importationInfo = (PrmsContractDetail) query.getResultList().get(0);
            return importationInfo;

        } catch (Exception ex) {
            return null;
        }

    }

//    public PrmsAward FindBySupplyIid(PrmsContract prmsContract) {
//        System.out.println("______****" + prmsContract);
//        Query query = em.createNativeQuery("SELECT PRMS_AWARD.SUPP_ID,\n"
//                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
//                + "  PRMS_AWARD.AWARD_ID\n"
//                + "FROM PRMS_CONTRACT\n"
//                + "INNER JOIN PRMS_AWARD\n"
//                + "ON PRMS_AWARD.AWARD_ID = PRMS_CONTRACT.AWARD_ID\n"
//                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
//                + "ON PRMS_SUPPLY_PROFILE.ID       = PRMS_AWARD.SUPP_ID\n"
//                + "WHERE prms_contract.contract_id = '" + prmsContract.getContractId() + "'", PrmsAward.class);
//        try {
//            PrmsAward vendorname = new PrmsAward();
//            if (query.getResultList().size() > 0) {
//                vendorname = (PrmsAward) query.getResultList().get(0);
//                System.out.println("============**********" + vendorname.getSuppId());
//
//            }
//            return vendorname;
//        } catch (Exception ex) {
//            return null;
//        }
//    }
    public PrmsContract FindBySupplyIid(PrmsContract prmsContract) {
        System.out.println("______****" + prmsContract);
        Query query = em.createNativeQuery("SELECT * FROM PRMS_CONTRACT "
                + "INNER JOIN PRMS_SUPPLY_PROFILE ON PRMS_SUPPLY_PROFILE.ID= "
                + "PRMS_CONTRACT.SUPP_ID WHERE prms_contract.contract_id = '" 
                + prmsContract.getContractId() + "'", PrmsContract.class);
        try {
            PrmsContract vendorname = new PrmsContract();
            if (query.getResultList().size() > 0) {
                vendorname = (PrmsContract) query.getResultList().get(0);
                System.out.println("============**********" + vendorname.getSuppId().getVendorName());

            }
            return vendorname;
        } catch (Exception ex) {
            return null;
        }
    }
}
