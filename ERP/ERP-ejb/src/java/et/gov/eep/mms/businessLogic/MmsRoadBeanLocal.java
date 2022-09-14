/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;



import et.gov.eep.mms.entity.MmsFaRoad;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;



/**
 *
 * @author w_station
 */
@Local
public interface MmsRoadBeanLocal {

    public void create(MmsFaRoad roadEntity);

    public void edit(MmsFaRoad roadEntity);

    public MmsFaRoad getLastRoadId();

    public List<MmsFaRoad> searchRoadByParameterPrefix(MmsFaRoad roadEntity);

    public MmsFaRoad getSelectedRequest(BigDecimal roadId);
    
}
