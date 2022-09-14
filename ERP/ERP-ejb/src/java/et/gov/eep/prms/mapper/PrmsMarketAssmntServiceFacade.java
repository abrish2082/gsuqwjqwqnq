/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.prms.entity.PrmsMarketAssmntService;
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
public class PrmsMarketAssmntServiceFacade extends AbstractFacade<PrmsMarketAssmntService> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsMarketAssmntServiceFacade() {
        super(PrmsMarketAssmntService.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    //get Lists of Consultancy Service Name
    public List<PrmsMarketAssmntService> getConsultancyServiceNameLists() {
        Query query = em.createNativeQuery("select ms.*\n"
                + "from prms_market_assmnt_service ms\n"
                + "inner join\n"
                + "       (select service_id,  max(id) as maxs\n"
                + "       from prms_market_assmnt_service\n"
                + "       WHERE service_id is not null\n"
                + "      AND service_type = 'Consultancy'\n"
                + "      group by service_id)ma2\n"
                + "      on ms.id=ma2.maxs \n"
                + "", PrmsMarketAssmntService.class);
        try {
            List<PrmsMarketAssmntService> serviceName = new ArrayList(query.getResultList());
            return serviceName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //get Lists of Non Consultancy Service Name
    public List<PrmsMarketAssmntService> getNonConsultancyServiceNameLists() {
        Query query = em.createNativeQuery("select ms.*\n"
                + "from prms_market_assmnt_service ms\n"
                + "inner join\n"
                + "       (select service_id,  max(id) as maxs\n"
                + "       from prms_market_assmnt_service\n"
                + "       WHERE service_id is not null\n"
                + "      AND service_type = 'Non Consultancy'\n"
                + "      group by service_id)ma2\n"
                + "      on ms.id=ma2.maxs \n"
                + "                ", PrmsMarketAssmntService.class);
        try {
            List<PrmsMarketAssmntService> serviceName = new ArrayList(query.getResultList());
            return serviceName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //get Lists of Work Names
    public List<PrmsMarketAssmntService> getWorkNameLists() {
        Query que = em.createNativeQuery("select ms2.*\n"
                + "from prms_market_assmnt_service ms2\n"
                + "\n"
                + "inner join\n"
                + "(select service_id, max(id) as maxs_SerIdWork\n"
                + "from prms_market_assmnt_service\n"
                + "WHERE service_id is not NULL\n"
                + "and service_type is null\n"
                + "group by service_id)ma2\n"
                + "on ms2.id=ma2.maxs_SerIdWork\n"
                + "\n"
                + "inner join prms_service_and_work_reg m0\n"
                + "\n"
                + "inner join\n"
                + "(select registeration_type as reg_type\n"
                + "from prms_service_and_work_reg\n"
                + "where registeration_type='work' \n"
                + "group by registeration_type)m02\n"
                + "on m0.registeration_type=m02.reg_type\n"
                + "\n"
                + "on m0.serv_and_work_id=ms2.service_id ", PrmsMarketAssmntService.class);
        try {
            List<PrmsMarketAssmntService> workNames = new ArrayList<>(que.getResultList());
            return workNames;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //get Service Info By General Ledger Id
    public PrmsMarketAssmntService getServiceInfoByGL(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        try {
            Query que = em.createNativeQuery("select ms2.*  from prms_market_assmnt_service ms2\n"
                    + "inner join\n"
                    + "(select service_id, max(id) as maxs_SerIdWork\n"
                    + "from prms_market_assmnt_service\n"
                    + "WHERE service_id is not Null\n"
                    + "group by service_id)ma2\n"
                    + "on ms2.id=ma2.maxs_SerIdWork\n"
                    + "inner join prms_service_and_work_reg sw\n"
                    + "on sw.serv_and_work_id=ms2.service_id\n"
                    + "where sw.general_ledger_id in(SELECT obd.general_ledger_id FROM fms_operating_budget_detail obd where obd.general_ledger_id='" + fmsOperatingBudgetDetail.getGeneralLedger().getGeneralLedgerId() + "')", PrmsMarketAssmntService.class);

            PrmsMarketAssmntService serviceInfo = new PrmsMarketAssmntService();
            if (que.getResultList().size() > 0) {
                serviceInfo = (PrmsMarketAssmntService) (que.getResultList().get(0));

            }
            return serviceInfo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //get Work Info By General Ledger Id
    public PrmsMarketAssmntService getWorkInfoByGL(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        Query que = em.createNativeQuery("SELECT * FROM prms_market_assmnt_service mas\n"
                + "inner join (select service_id, max(id) as maxId FROM prms_market_assmnt_service\n"
                + "where service_id is not Null\n"
                + "and service_type is null\n"
                + "group by service_id) mas2\n"
                + "on mas.id=mas2.maxId\n"
                + "inner join prms_service_and_work_reg sw\n"
                + "on mas.service_id=sw.serv_and_work_id\n"
                + "where sw.general_ledger_id in(SELECT obd.general_ledger_id FROM fms_operating_budget_detail obd where obd.general_ledger_id='" + fmsOperatingBudgetDetail.getGeneralLedger().getGeneralLedgerId() + "')", PrmsMarketAssmntService.class);

        try {
            PrmsMarketAssmntService workInfo = new PrmsMarketAssmntService();
            if (que.getResultList().size() > 0) {
                workInfo = (PrmsMarketAssmntService) (que.getResultList().get(0));
            }
            return workInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

}
