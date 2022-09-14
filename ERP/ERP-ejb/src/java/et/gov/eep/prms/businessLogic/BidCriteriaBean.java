/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidCriteria;
import et.gov.eep.prms.mapper.PrmsBidCriteriaFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class BidCriteriaBean implements BidCriteriaBeanLocal {

    @EJB
    PrmsBidCriteriaFacade bidCriteriaFacade;

    @Override
    public void save(PrmsBidCriteria prmsBidCriteria) {
        bidCriteriaFacade.create(prmsBidCriteria);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public ArrayList<PrmsBid> getBidNo() {
        return bidCriteriaFacade.getBidNO();
    }

    @Override
    public List<PrmsBidCriteria> searchCriterialNo(PrmsBidCriteria prmsBidCriteria) {
        return bidCriteriaFacade.getCriteriaNo(prmsBidCriteria);
    }

    @Override
    public void update(PrmsBidCriteria prmsBidCriteria) {
       bidCriteriaFacade.edit(prmsBidCriteria);
    }

    @Override
    public PrmsBidCriteria getBidCriteriaId(String id) {
      return bidCriteriaFacade.getBidCriteriaId(id);
    }

    @Override
    public PrmsBidCriteria getBidCriteria() {
        return bidCriteriaFacade.getCiteriaNo();
    }

    @Override
    public List<PrmsBidCriteria> getParamNameList() {
        return bidCriteriaFacade.getParamNameList();
    }

}
