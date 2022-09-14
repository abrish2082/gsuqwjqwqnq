
package et.gov.eep.mms.businessLogic;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import static java.util.Collections.list;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsFixedAssetRegistrationDtlBeanLocal {

    public List<MmsFixedassetRegstDetail> findAllTagNo();

    public List<MmsFixedassetRegstDetail> findAll();

    public MmsFixedassetRegstDetail findByTag(MmsFixedassetRegstDetail fixedassetRegstDetail);

    public List<MmsFixedassetRegstDetail> findAllTagNoForOld();

    public MmsFixedassetRegstDetail findByDetailId(MmsFixedassetRegstDetail fixedAssetRegDtlEntity);

    public void edit(MmsFixedassetRegstDetail fixedAssetRegDtlEntity);

    public List<MmsFixedassetRegstDetail> findNewItems();

    public List<MmsFixedassetRegstDetail> findByTagNo2(MmsFixedassetRegstDetail fixedAssetRegDtlEntity);

    public List<MmsFixedassetRegstDetail> getTagNo();

    public MmsFixedassetRegstDetail getTagInfoBtyId(MmsFixedassetRegstDetail mmsFixedassetRegstDetail);

    public List<MmsFixedassetRegstDetail> getFixedAssetInfoByRecivedBy(HrDepartments hrDepartmentsEntity);

    public MmsFixedassetRegstDetail getFixedAssetInfoByRecivedBytagno(MmsFixedassetRegstDetail selectedReturndeBy);

    public List<MmsFixedassetRegstDetail> getTagNoByRequester(MmsFixedassetRegstDetail fixedAssetRegDtlEntity);

    public List<MmsFixedassetRegstDetail> getTagNoByRequester1(List<Integer> ids);

    public List<String> findAllItemStatuses();

    public int ConutBYItemType(String get);

}
