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
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author user
 */
@Local
public interface MmsInspectionBeanLocal {

    /**
     *
     * @param Inspection
     */
    void create(MmsInspection Inspection);

    /**
     *
     * @param Inspection
     */
    void edit(MmsInspection Inspection);

    /**
     *
     * @return
     */
    public List<MmsInspection> findAll();

    /**
     *
     * @param qa
     * @return
     */
    public List<MmsInspection> findAllByStatus(int qa);

    /**
     *
     * @param storeinfo
     * @return
     */
    ArrayList<MmsStoreInformation> searchStoreName(MmsStoreInformation storeinfo);

    /**
     *
     * @param inspection
     * @return
     */
    public ArrayList<MmsInspection> searchByinspectionNumber(MmsInspection inspection);

    /**
     *
     * @return
     */
    public MmsInspection getLastinspectionNo();

    /**
     *
     * @param a
     * @return
     */
    public String getinspectionIdbyInspectionNo(String a);

    /**
     *
     * @param selectedSivNo
     * @return
     */
    public MmsInspection getInspectionInfoByInspNo(String selectedSivNo);

    /**
     *
     * @param inspection
     * @return
     */
    public ArrayList<MmsInspection> searchByinspectionNumber(String inspection);

    public List<MmsInspection> searchInspectionByParameterInspectionNo(MmsInspection inspection);

    public MmsInspection getInspectionInfoByInspId(MmsInspection inspection);

    public ArrayList<MmsInspection> searchByinspectionNoAndProcessedBy(MmsInspection inspection);

//    public List<MmsInspection> findAll(MmsInspection inspection);
    public List<MmsInspection> findinspactionNo(MmsInspection inspaction);

    public List<MmsInspection> searchAllByPreparerId(MmsInspection inspection);

    public List<MmsInspection> findInspectionListByWfStatus(int PREPARE_VALUE);

    public List<MmsInspection> findAllinsNo();

    public List<String> getMmsInspectionColumnNameList();

    public List<MmsInspection> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, MmsInspection inspection, String columnValue);
}
