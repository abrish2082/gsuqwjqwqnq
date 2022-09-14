/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package et.gov.eep.mms.businessLogic;
import et.gov.eep.mms.entity.MmsFaLand;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsLandBeanLocal {

    public void create(MmsFaLand landEntity);

    public void edit(MmsFaLand landEntity);

    public MmsFaLand getLastLandId();

    public List<MmsFaLand> searchLandByParameterPrefix(MmsFaLand landEntity);

    public MmsFaLand getSelectedRequest(BigDecimal landId);

    public List<MmsFaLand> searchLandByName(MmsFaLand landEntity);

    public List<MmsFaLand> searchLandByParameterPrefixAndLandPrep(MmsFaLand landEntity);

    public List<MmsFaLand> searchLandByNameAndLandPrep(MmsFaLand landEntity);
    public List<MmsFaLand> searchAllLandInfoByPreparerId(Integer preparedBy);
    
}
