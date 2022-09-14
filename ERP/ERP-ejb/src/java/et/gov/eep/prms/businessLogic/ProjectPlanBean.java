/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.prms.entity.PrmsProjectPlan;
import et.gov.eep.prms.mapper.PrmsProjectPlanFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
// </editor-fold>

/**
 *
 * @author user
 */
@Stateless
public class ProjectPlanBean implements ProjectPlanBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsProjectPlanFacade prmsProjectPlanFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
// </editor-fold>

    @Override
    public void save(PrmsProjectPlan prmsProjectPlan) {
        prmsProjectPlanFacade.create(prmsProjectPlan);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void upDate(PrmsProjectPlan prmsProjectPlan) {
        prmsProjectPlanFacade.edit(prmsProjectPlan);
    }

    @Override
    public List<PrmsProjectPlan> searchByPlanNo(PrmsProjectPlan prmsProjectPlan) {
        return prmsProjectPlanFacade.getProjectPlanNo(prmsProjectPlan);
    }

    @Override
    public List<PrmsProjectPlan> searchByPlanNo(int status, int UserId) {
        return prmsProjectPlanFacade.getProjectPlanNo(status, UserId);
    }

    @Override
    public PrmsProjectPlan getProjectPlanId(String id) {
        return prmsProjectPlanFacade.getProjectPlanId(id);
    }

    @Override
    public PrmsProjectPlan getProjectPlan() {
        return prmsProjectPlanFacade.getProjectPlanNo();
    }

    @Override
    public List<PrmsProjectPlan> searchByPlanNo_() {
        return prmsProjectPlanFacade.getProjectPlanNo_();
    }

    @Override
    public List<PmsCreateProjects> getProjectId() {
        return prmsProjectPlanFacade.getProject();
    }

    @Override
    public String getGoodsOrServiceOrWorkProjectSeqNo(String purchaseType) {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        System.out.println("Current Ethiopian Year " + eYear);
        String goods_Service_Work_ProjectNo = null;
        String prefix = null;
        int maxNo = 0;
        if (purchaseType.equals("good")) {
            prefix = "PP-Goods-No";
        } else if (purchaseType.equals("Service")) {
            prefix = "PP-Service-No";
        } else if (purchaseType.equals("Work")) {
            prefix = "PP-Work-No";
        }
        List<PrmsProjectPlan> prmsProjectPlanLists = prmsProjectPlanFacade.getGoodsOrServiceOrWorkProjectSeqNo(prefix, eYear);
        for (int j = 0; j < prmsProjectPlanLists.size(); j++) {
            goods_Service_Work_ProjectNo = prmsProjectPlanLists.get(j).getProjectPlanNo();
            String[] lastInspNos = goods_Service_Work_ProjectNo.split("-");
            String lastDatesPatern = lastInspNos[3];
            System.out.println("1 " + lastDatesPatern);
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increment = Integer.parseInt(lastDatesPaterns[0]);
            System.out.println("increment " + increment);
            if (maxNo < increment) {
                maxNo = increment;
            }
        }
        maxNo += 1;
        goods_Service_Work_ProjectNo = prefix + "-" + maxNo + "/" + eYear;
        return goods_Service_Work_ProjectNo;
    }

    @Override
    public List<PrmsProjectPlan> getParamNameList() {
        return prmsProjectPlanFacade.getParamNameList();
    }
}
