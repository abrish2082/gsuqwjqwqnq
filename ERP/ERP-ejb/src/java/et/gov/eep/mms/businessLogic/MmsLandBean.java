/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFaLand;
import et.gov.eep.mms.mapper.MmsFaLandFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsLandBean implements MmsLandBeanLocal {

   @EJB
   MmsFaLandFacade landFacade;
   
    
    @Override
    public void create(MmsFaLand landEntity) {
       landFacade.create(landEntity);
    }

    @Override
    public void edit(MmsFaLand landEntity) {
        landFacade.edit(landEntity);
    }

    @Override
    public MmsFaLand getLastLandId() {
       return landFacade.getLastLandId();
    }

    @Override
    public List<MmsFaLand> searchLandByParameterPrefix(MmsFaLand landEntity) {
         return landFacade.searchLandByParameterPrefix(landEntity);
    }

    @Override
    public MmsFaLand getSelectedRequest(BigDecimal landId) {
       return landFacade.getSelectedRequest(landId);
    }

    @Override
    public List<MmsFaLand> searchLandByName(MmsFaLand landEntity) {
        return landFacade.searchLandByName(landEntity);
    }

    @Override
    public List<MmsFaLand> searchLandByParameterPrefixAndLandPrep(MmsFaLand landEntity) {
        return landFacade.searchLandByParameterPrefixAndLandPrep(landEntity);
    }

    @Override
    public List<MmsFaLand> searchLandByNameAndLandPrep(MmsFaLand landEntity) {
        return landFacade.searchLandByNameAndLandPrep(landEntity);
    }

    @Override
    public List<MmsFaLand> searchAllLandInfoByPreparerId(Integer preparedBy) {
         return landFacade.searchAllLandInfoByPreparerId(preparedBy);
    }

   
}
