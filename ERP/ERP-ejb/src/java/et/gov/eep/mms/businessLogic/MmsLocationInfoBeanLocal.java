/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.mms.entity.MmsLocationInfo;
import et.gov.eep.mms.entity.MmsShelfInfo;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Local
public interface MmsLocationInfoBeanLocal {

    /**
     *
     * @param mmsLocationInfo
     */
    public void create(MmsLocationInfo mmsLocationInfo);

    public void saveOrUpdate(MmsLocationInfo mmsLocationInfo);

    /**
     *
     * @param mmsLocationInfo
     */
    public void edit(MmsLocationInfo mmsLocationInfo);

    /**
     *
     * @param mmsNeedAssessment
     * @return
     */
    public MmsLocationInfo getMmsCellInformation(MmsLocationInfo mmsNeedAssessment);

    /**
     *
     * @param mmsNeedAssessment
     * @return
     */
    public MmsLocationInfo getMmsCellInformationForWarehouse(MmsLocationInfo mmsNeedAssessment);

    public List<MmsLocationInfo> searchCellByShelfId(MmsShelfInfo mmsShelfInfoEntity);

    public List<MmsLocationInfo> searchByParameterStoreAndShelf(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity);

    public List<MmsLocationInfo> searchByAllParameters(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity, MmsLocationInfo mmsCellInfoEntity);

    public List<MmsLocationInfo> searchCellByRackAndShelfId(MmsShelfInfo mmsShelfInfoEntity, MmsLocationInfo cellInfo);

    public List<MmsLocationInfo> findAllItemInfo();

    public List<String> getMmsShelfColumnNameList();

    public List<MmsLocationInfo> getShelfListsByParameter(ColumnNameResolver columnNameResolver, MmsLocationInfo mmsCellInfoEntity, String columnValue);
}
