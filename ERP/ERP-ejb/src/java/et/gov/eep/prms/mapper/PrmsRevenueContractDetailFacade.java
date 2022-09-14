/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsRevenueContractDetail;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mora1
 */
@Stateless
public class PrmsRevenueContractDetailFacade extends AbstractFacade<PrmsRevenueContractDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsRevenueContractDetailFacade() {
        super(PrmsRevenueContractDetail.class);
    }

    public List<PrmsRevenueContractDetail> howMPaidByProdOrService(String prodOrServName) {
        System.out.println("check By Pro/Serv Name " + prodOrServName);
        Query query = em.createNamedQuery("PrmsRevenueContractDetail.findByPerformanceObligation");
        query.setParameter("performanceObligation", prodOrServName);
        try {
            List<PrmsRevenueContractDetail> detRevContList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                detRevContList = query.getResultList();
                System.out.println("hhh " + detRevContList.size());
            }
            return detRevContList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
