/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Local
public interface MmsGRNBEanLocal {

    /**
     *
     * @param mmsgrn
     */
    void create(MmsGrn mmsgrn);

    /**
     *
     * @param mmsgrn
     */
    void edit(MmsGrn mmsgrn);

    /**
     *
     * @param grn
     * @return
     */
    public ArrayList<MmsGrn> searchByGrn(MmsGrn grn);

    /**
     *
     * @param grnNo
     * @return
     */
    public MmsGrn getbyGrnNo(MmsGrn grnNo);

    /**
     *
     * @param selectedGrnNo
     * @return
     */
    public MmsGrn getGrnInfoByGrnNo(String selectedGrnNo);

    /**
     *
     * @param selectedGrnNo
     * @return
     */
    public String getAllDatawithinspectionResult(String selectedGrnNo);

    /**
     *
     * @return
     */
    public MmsGrn getLastinspectionNo();

    /**
     *
     * @param storeInformation
     * @return
     */
    public ArrayList<MmsGrn> approvedGrnList(MmsStoreInformation storeInformation);

    public MmsGrn getbyGrnID(MmsGrn grnId);

    List<MmsGrn> findAll();

    public List<MmsGrn> searchByParameterStore(MmsStoreInformation storeInfoEntity);

    public List<MmsGrn> searchByParameterStoreAndGrnNo(MmsStoreInformation storeInfoEntity, MmsGrn mmsgrn);

    public List<MmsGrn> searchByParameterStoreAndProcessedBy(MmsStoreInformation storeInfoEntity);

    public List<MmsGrn> searchByParameterStoreAndGrnNoAndProcessedBy(MmsStoreInformation storeInfoEntity, MmsGrn mmsgrn);

    public List<MmsGrn> findGRNListByWfStatus(int status);

    public ArrayList<MmsGrn> approvedGrnListByStoreAndStatus(MmsGrn grn, int status);
 
    public List<String> getMmsGrnColumnNameList();

    public List<MmsGrn> getGrnListsByParameter(ColumnNameResolver columnNameResolver, MmsGrn mmsgrn, String columnValue);
}
