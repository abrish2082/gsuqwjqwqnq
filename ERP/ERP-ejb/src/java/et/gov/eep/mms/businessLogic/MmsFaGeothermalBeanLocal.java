/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;





import et.gov.eep.mms.entity.MmsFaGeothermal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface MmsFaGeothermalBeanLocal {

    /**
     *
     * @param mmsFaTransmission
     * @return
     */
    public ArrayList<MmsFaGeothermal> searchGeothermalFA(MmsFaGeothermal mmsFaTransmission);

    /**
     *
     * @param mmsFaTransmission
     * @return
     */
    
    
    
    
   
    public MmsFaGeothermal getByAssetName(MmsFaGeothermal mmsFaTransmission);

    public void create(MmsFaGeothermal geothermalEntity);

    public void edit(MmsFaGeothermal geothermalEntity);

    public List<MmsFaGeothermal> searchGeoByParameterPrefix(MmsFaGeothermal geothermalEntity);

    public MmsFaGeothermal getLastGeoId();

    public ArrayList<MmsFaGeothermal> searchByGeoNo(MmsFaGeothermal geothermalEntity);

    public List<MmsFaGeothermal> findAll1();

    public List<MmsFaGeothermal> searchByGeo(MmsFaGeothermal geothermalEntity);

    public MmsFaGeothermal getSelectedRequest(Integer geothermalId);
    
    public List<MmsFaGeothermal> findNewItems();

    public List<MmsFaGeothermal> searchByGeoNoAndGtPrep(MmsFaGeothermal geothermalEntity);
//   public List<MmsFaGeothermal> searchAllTransmissionsInfoByPreparerId(Integer tpPrepared);
}
