/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFaRoad;
import et.gov.eep.mms.mapper.MmsFaRoadFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsRoadBean implements MmsRoadBeanLocal {

    @EJB
    MmsFaRoadFacade roadFacade;
    
    @Override
    public void create(MmsFaRoad roadEntity) {
        roadFacade.create(roadEntity);
    }

    @Override
    public void edit(MmsFaRoad roadEntity) {
        roadFacade.edit(roadEntity);
    }

    @Override
    public MmsFaRoad getLastRoadId() {
        return roadFacade.getLastRoadId();
    }

    @Override
    public List<MmsFaRoad> searchRoadByParameterPrefix(MmsFaRoad roadEntity) {
        return roadFacade.searchRoadByParameterPrefix(roadEntity);
    }

    @Override
    public MmsFaRoad getSelectedRequest(BigDecimal roadId) {
     return roadFacade.getSelectedRequest(roadId);   
    }

    
}
